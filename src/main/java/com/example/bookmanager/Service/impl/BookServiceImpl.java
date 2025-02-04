package com.example.bookmanager.Service.impl;

import com.example.bookmanager.Annotation.LogRecord;
import com.example.bookmanager.Config.LibraryConfig;
import com.example.bookmanager.DTO.BookDTO;
import com.example.bookmanager.DTO.AddBookRequest;
import com.example.bookmanager.DTO.BookInfoRequest;
import com.example.bookmanager.DTO.PageContent;
import com.example.bookmanager.Entity.BookInformation;
import com.example.bookmanager.Exception.BusinessException;
import com.example.bookmanager.Mapper.BookInformationMapper;
import com.example.bookmanager.Mapper.BooksMapper;
import com.example.bookmanager.Mapper.BorrowRecordMapper;
import com.example.bookmanager.Service.BookService;
import com.example.bookmanager.Type.BookCategory;
import com.example.bookmanager.Utils.ThreadLocalUtil;
import com.example.bookmanager.Utils.UserClaims;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class BookServiceImpl implements BookService {

    private final BookInformationMapper bookInformationMapper;
    private final BooksMapper booksMapper;
    private final BorrowRecordMapper borrowRecordMapper;
    private final LibraryConfig libraryConfig;

    public BookServiceImpl(BookInformationMapper bookInformationMapper, BooksMapper booksMapper, BorrowRecordMapper borrowRecordMapper, LibraryConfig libraryConfig) {
        this.bookInformationMapper = bookInformationMapper;
        this.booksMapper = booksMapper;
        this.borrowRecordMapper = borrowRecordMapper;
        this.libraryConfig = libraryConfig;
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
    public PageContent<BookDTO> findAllBooks(String status, Integer page, Integer count, String category) {
        if (status != null && status.trim().isEmpty()) status = null;
        if (category != null && category.trim().isEmpty()) category = null;
        if (page == null || count == null) {
            page = null; count = null;
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
        List<BookDTO> list = bookInformationMapper.findAllBooks(status, category);
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
            BookInformation bookInformation = new BookInformation();
            bookInformation.setIsbn(addBookRequest.getIsbn());
            bookInformation.setTitle(addBookRequest.getTitle());
            bookInformation.setAuthor(addBookRequest.getAuthor());
            bookInformation.setPublisher(addBookRequest.getPublisher());
            bookInformationMapper.insertBookInformation(bookInformation);
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
        UserClaims claims = ThreadLocalUtil.get();
        if (!claims.isStatus()) throw new BusinessException(5, 200, "User is banned");
        if (borrowRecordMapper.countBorrow(claims.getId()) == libraryConfig.getLoanMaxCount())
            throw new BusinessException(5, 200, "User has borrowed too many books");
        String status = booksMapper.getStatusById(bookId);
        if (status == null) {
            throw new BusinessException(2, 400, "Book id not exists");
        } else if (status.equals("AVAILABLE")) {
            booksMapper.updateStatusById(bookId, "BORROWED");
            LocalDateTime returnDate = LocalDateTime.now().plusDays(libraryConfig.getLoanDurationDays());
            borrowRecordMapper.insertRecord(claims.getId(), bookId, returnDate);
        } else {
            throw new BusinessException(2, 200, "Book not available");
        }
    }

    @Override
    public List<BookDTO> getBorrowedBooks() {
        return borrowRecordMapper.getBorrowedBooks(ThreadLocalUtil.get().getId());
    }

    @Override
    @Transactional
    @LogRecord
    public void returnBook(Long id) {
        if (borrowRecordMapper.isBorrowedByUser(id, ThreadLocalUtil.get().getId()) == 1) {
            booksMapper.updateStatusById(id, "AVAILABLE");
            borrowRecordMapper.deleteRecord(id, ThreadLocalUtil.get().getId());
        } else throw new BusinessException(2, 400, "You didn't borrow this book");
    }
}
