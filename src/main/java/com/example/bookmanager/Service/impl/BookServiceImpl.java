package com.example.bookmanager.Service.impl;

import com.example.bookmanager.Annotation.LogRecord;
import com.example.bookmanager.Config.LibraryConfig;
import com.example.bookmanager.DTO.*;
import com.example.bookmanager.Entity.BookInformation;
import com.example.bookmanager.Entity.BorrowRecord;
import com.example.bookmanager.Entity.ReserveRecord;
import com.example.bookmanager.Exception.BusinessException;
import com.example.bookmanager.Mapper.BookInformationMapper;
import com.example.bookmanager.Mapper.BooksMapper;
import com.example.bookmanager.Mapper.BorrowRecordMapper;
import com.example.bookmanager.Mapper.ReserveRecordMapper;
import com.example.bookmanager.Service.BookService;
import com.example.bookmanager.Service.RedisService;
import com.example.bookmanager.Type.BookCategory;
import com.example.bookmanager.Type.BookStatus;
import com.example.bookmanager.Utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
public class BookServiceImpl implements BookService {

    private final BookInformationMapper bookInformationMapper;
    private final BooksMapper booksMapper;
    private final BorrowRecordMapper borrowRecordMapper;
    private final ReserveRecordMapper reserveRecordMapper;
    private final LibraryConfig libraryConfig;
    private final RedisService redisService;

    public BookServiceImpl(BookInformationMapper bookInformationMapper, BooksMapper booksMapper, BorrowRecordMapper borrowRecordMapper, ReserveRecordMapper reserveRecordMapper, LibraryConfig libraryConfig, RedisService redisService) {
        this.bookInformationMapper = bookInformationMapper;
        this.booksMapper = booksMapper;
        this.borrowRecordMapper = borrowRecordMapper;
        this.reserveRecordMapper = reserveRecordMapper;
        this.libraryConfig = libraryConfig;
        this.redisService = redisService;
    }

    private void validateUserStatus(Long userId) {
        if (!redisService.getUserStatus(userId)) {
            throw new BusinessException(1, 400, "User is banned");
        }
    }

    private void validateBorrowLimit(Long userId) {
        if (borrowRecordMapper.countBorrow(userId) == libraryConfig.getLoanMaxCount()) {
            throw new BusinessException(5, 200, "User has borrowed too many books");
        }
    }

    private void validateReserveLimit(Long userId) {
        if (reserveRecordMapper.countReserve(userId) == libraryConfig.getMaxReserveCount()) {
            throw new BusinessException(5, 200, "User has reserved too many books");
        }
    }

    private void validateBookAvailability(String bookStatus) {
        if (bookStatus == null) {
            throw new BusinessException(2, 200, "Book id not exists");
        }
        if (!bookStatus.equals("AVAILABLE")) {
            throw new BusinessException(2, 200, "Book not available");
        }
    }

    @Override
    public BookInformation findByTitle(String title) {
        return bookInformationMapper.findByTitle(title);
    }

    @Override
    public BookDTO findById(Long id) {
        return bookInformationMapper.findById(id);
    }

    @Override
    public PageContent<BookDTO> findAllBooks(String status, Integer page, Integer count, String category, String library) {
        if (category != null && category.trim().isEmpty()) category = null;
        if (library != null && library.trim().isEmpty()) library = null;
        if (page == null || count == null) {
            page = null;
            count = null;
        }
        if (status != null && status.trim().isEmpty()) {
            try {
                BookStatus.valueOf(status);
            } catch (IllegalArgumentException e) {
                throw new BusinessException(2, 400, "Book status not exists");
            }
        }
        if (category != null) {
            try {
                BookCategory.valueOf(category);
            } catch (IllegalArgumentException e) {
                throw new BusinessException(2, 400, "Book category not exists");
            }
        }
        PageContent<BookDTO> pc = new PageContent<>();
        if (page != null) PageHelper.startPage(page, count);
        List<BookDTO> list = bookInformationMapper.findAllBooks(status, category, library);
        if (page != null) {
            Page<BookDTO> pageList = (Page<BookDTO>) list;
            pc.setCount((int) pageList.getTotal());
            pc.setPage(pageList.getPageNum());
            pc.setContent(pageList.getResult());
            pc.setSize(pageList.getPageSize());
        } else {
            pc.setCount(list.size());
            pc.setPage(1);
            pc.setContent(list);
            pc.setSize(list.size());
        }
        return pc;
    }

    @Override
    @LogRecord
    @Transactional
    public void addBooks(AddBookRequest addBookRequest) {
        if (bookInformationMapper.existsByIsbn(addBookRequest.getIsbn()) == 0) {
            addBookInfo(addBookRequest);
        } else {
            BookInformation information = bookInformationMapper.findByIsbn(addBookRequest.getIsbn());
            if (!information.getTitle().equals(addBookRequest.getTitle()) ||
                    !information.getAuthor().equals(addBookRequest.getAuthor()) ||
                    !information.getPublisher().equals(addBookRequest.getPublisher()))
                throw new BusinessException(2, 400, "Book info not match ISBN");
        }
        for (int i = 0; i < addBookRequest.getCount(); i++) {
            booksMapper.insertBook(addBookRequest.getIsbn(), addBookRequest.getLibrary());
        }
    }

    @LogRecord
    @Override
    public void addBookInfo(BookInfoRequest bookInfoRequest) {
        if (bookInformationMapper.existsByIsbn(bookInfoRequest.getIsbn()) != 0) {
            throw new BusinessException(2, 400, "Book info already exists");
        }
        bookInformationMapper.insertBookInformation(new BookInformation(bookInfoRequest.getIsbn(), bookInfoRequest.getTitle(), bookInfoRequest.getAuthor(), bookInfoRequest.getPublisher(), BookCategory.valueOf(bookInfoRequest.getCategory())));
    }

    @LogRecord
    @Override
    public void deleteBook(Long id) {
        booksMapper.deleteBook(id);
    }

    @LogRecord
    @Override
    @Transactional
    public void borrowBook(Long bookId) {
        Long userId = ThreadLocalUtil.get().getId();
        validateUserStatus(userId);
        validateBorrowLimit(userId);

        Long reserveId = reserveRecordMapper.getReserveIdByBookIdAndUserId(bookId, userId);
        if (reserveId == null) {
            String bookStatus = booksMapper.getStatusById(bookId);
            validateBookAvailability(bookStatus);
        }
        booksMapper.updateStatusById(bookId, "BORROWED");
        LocalDateTime returnDate = LocalDateTime.now().plusDays(libraryConfig.getLoanDurationDays())
                .withHour(23).withMinute(59).withSecond(59);
        if (reserveId != null) {
            borrowRecordMapper.insertRecordWithReserve(userId, bookId, returnDate, reserveId);
            reserveRecordMapper.setStatus(reserveId, "BORROWED");
            reserveRecordMapper.setBorrowDate(reserveId, LocalDateTime.now());
        } else borrowRecordMapper.insertRecord(userId, bookId, returnDate);
    }

    @Override
    public List<BorrowedBook> getBorrowedBooks() {
        return borrowRecordMapper.getBorrowedBooks(ThreadLocalUtil.get().getId());
    }

    @Override
    @Transactional
    @LogRecord
    public void returnBook(Long bookId) {
        Long userId = ThreadLocalUtil.get().getId();
        BorrowRecord record = borrowRecordMapper.getBorrowRecordByBookIdAndUserId(bookId, userId);
        if (record != null) {
            booksMapper.updateStatusById(bookId, "AVAILABLE");
            borrowRecordMapper.setReturnDate(bookId, userId, LocalDateTime.now());
            borrowRecordMapper.setReturnStatus(bookId, userId, true);
            if (record.getReserveId() != null) {
                reserveRecordMapper.setStatus(record.getReserveId(), "RETURNED");
            }
        } else throw new BusinessException(2, 200, "You didn't borrow this book");
    }

    @Override
    @Transactional
    @LogRecord
    public void renewBorrowedBook(Long bookId) {
        Long userId = ThreadLocalUtil.get().getId();
        BorrowRecord record = borrowRecordMapper.getBorrowRecordByBookIdAndUserId(bookId, userId);

        if (record == null) throw new BusinessException(2, 200, "You didn't borrow this book");
        LocalDateTime now = LocalDateTime.now();
        if (record.getReturnDate().isBefore(now))
            throw new BusinessException(2, 200, "The time to return the book has passed");
        if (record.getRenewTimes() == libraryConfig.getMaxRenewTimes())
            throw new BusinessException(2, 200, "You can't renew this book anymore");
        if (ChronoUnit.DAYS.between(now, record.getReturnDate()) > libraryConfig.getRenewPriorDays())
            throw new BusinessException(2, 200, "Renewals can only be made " + libraryConfig.getRenewPriorDays() + " five days prior to the return date");
        LocalDateTime returnDate = record.getReturnDate().plusDays(libraryConfig.getLoanDurationDays());
        borrowRecordMapper.setReturnDate(bookId, userId, returnDate);
        borrowRecordMapper.setRenewTimes(bookId, userId, record.getRenewTimes() + 1);
    }

    @Override
    @Transactional
    @LogRecord
    public void reserveBook(Long bookId) {
        Long userId = ThreadLocalUtil.get().getId();
        validateUserStatus(userId);
        validateBorrowLimit(userId);
        validateReserveLimit(userId);

        String bookStatus = booksMapper.getStatusById(bookId);
        validateBookAvailability(bookStatus);

        LocalDateTime borrowDate = LocalDateTime.now().plusDays(libraryConfig.getBorrowAfterRenew())
                .withHour(23).withMinute(59).withSecond(59);
        booksMapper.updateStatusById(bookId, "RESERVED");
        reserveRecordMapper.insertRecord(userId, bookId, borrowDate);
    }

    @Override
    @Transactional
    @LogRecord
    public void cancelReserve(Long bookId) {
        Long userId = ThreadLocalUtil.get().getId();
        ReserveRecord record = reserveRecordMapper.getReserveRecordByUserIdAndBookId(userId, bookId);

        if (record == null) throw new BusinessException(2, 200, "You didn't reserve this book");
        if (ChronoUnit.HOURS.between(record.getReserveDate(), LocalDateTime.now()) < libraryConfig.getReserveCancelHours())
            throw new BusinessException(6, 200, "Reservations cannot be cancelled up to " + libraryConfig.getReserveCancelHours() + " hours after the reservation is made");
        reserveRecordMapper.setStatus(record.getId(), "CANCELED");
        reserveRecordMapper.setBorrowDate(record.getId(), null);
        booksMapper.updateStatusById(bookId, "AVAILABLE");
    }

    @Override
    public List<ReservedBook> getReservedBooks() {
        return reserveRecordMapper.getReservedBookByUserId(ThreadLocalUtil.get().getId());
    }
}
