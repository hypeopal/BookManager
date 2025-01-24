package com.example.bookmanager.Service.impl;

import com.example.bookmanager.DTO.BookDTO;
import com.example.bookmanager.DTO.AddBookRequest;
import com.example.bookmanager.DTO.BookInfoRequest;
import com.example.bookmanager.DTO.PageContent;
import com.example.bookmanager.Entity.BookInformation;
import com.example.bookmanager.Exception.BusinessException;
import com.example.bookmanager.Mapper.BookInformationMapper;
import com.example.bookmanager.Mapper.BooksMapper;
import com.example.bookmanager.Service.BookService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BookServiceImpl implements BookService {

    private final BookInformationMapper bookInformationMapper;
    private final BooksMapper booksMapper;

    public BookServiceImpl(BookInformationMapper bookInformationMapper, BooksMapper booksMapper) {
        this.bookInformationMapper = bookInformationMapper;
        this.booksMapper = booksMapper;
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
    public PageContent<BookDTO> findAllBooks(String status, Integer page, Integer count) {
        PageContent<BookDTO> pc = new PageContent<>();
        if (page == null) PageHelper.startPage(1, 10000);
        else PageHelper.startPage(page, count);
        Page<BookDTO> p = (Page<BookDTO>) bookInformationMapper.findAllBooks(status);
        pc.setCount((int) p.getTotal());
        pc.setPage(p.getPageNum());
        pc.setContent(p.getResult());
        return pc;
    }

    @Override
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
                throw new BusinessException(7, "Book info not match ISBN");
        }
        for (int i = 0; i < addBookRequest.getCount(); i++) {
            booksMapper.insertBook(addBookRequest.getIsbn(), addBookRequest.getLibrary());
        }
    }

    @Override
    public void addBookInfo(BookInfoRequest bookInfoRequest) {
        bookInformationMapper.insertBookInformation(new BookInformation(bookInfoRequest.getIsbn(), bookInfoRequest.getTitle(), bookInfoRequest.getAuthor(), bookInfoRequest.getPublisher()));
    }

    @Override
    public void deleteBook(Long id) {
        booksMapper.deleteBook(id);
    }
}
