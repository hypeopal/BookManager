package com.example.bookmanager.Service.impl;

import com.example.bookmanager.DTO.BookDTO;
import com.example.bookmanager.DTO.AddBookRequest;
import com.example.bookmanager.DTO.BookInfoRequest;
import com.example.bookmanager.Entity.BookInformation;
import com.example.bookmanager.Exception.BusinessException;
import com.example.bookmanager.Mapper.BookInformationMapper;
import com.example.bookmanager.Mapper.BooksMapper;
import com.example.bookmanager.Service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<BookDTO> findAllBooks() {
        return bookInformationMapper.findAllBooks();
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
            booksMapper.insertBook(addBookRequest.getIsbn());
        }
    }

    @Override
    public void addBookInfo(BookInfoRequest bookInfoRequest) {
        bookInformationMapper.insertBookInformation(new BookInformation(bookInfoRequest.getIsbn(), bookInfoRequest.getTitle(), bookInfoRequest.getAuthor(), bookInfoRequest.getPublisher()));
    }
}
