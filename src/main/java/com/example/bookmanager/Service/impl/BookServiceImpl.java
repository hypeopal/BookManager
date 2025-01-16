package com.example.bookmanager.Service.impl;

import com.example.bookmanager.DTO.BookDTO;
import com.example.bookmanager.DTO.BookRequestDTO;
import com.example.bookmanager.Entity.BookInformation;
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
    public void addBooks(BookRequestDTO bookRequestDTO) {
        if (bookInformationMapper.existsByIsbn(bookRequestDTO.getIsbn()) == 0) {
            BookInformation bookInformation = new BookInformation();
            bookInformation.setIsbn(bookRequestDTO.getIsbn());
            bookInformation.setTitle(bookRequestDTO.getTitle());
            bookInformation.setAuthor(bookRequestDTO.getAuthor());
            bookInformation.setPublisher(bookRequestDTO.getPublisher());
            bookInformationMapper.insertBookInformation(bookInformation);
        }
        for (int i = 0; i < bookRequestDTO.getCount(); i++) {
            booksMapper.insertBook(bookRequestDTO.getIsbn());
        }
    }
}
