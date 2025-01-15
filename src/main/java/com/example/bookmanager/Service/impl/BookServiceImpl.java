package com.example.bookmanager.Service.impl;

import com.example.bookmanager.DTO.BookDTO;
import com.example.bookmanager.Entity.BookInformation;
import com.example.bookmanager.Mapper.BookMapper;
import com.example.bookmanager.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public BookInformation findByTitle(String title) {
        return bookMapper.findByTitle(title);
    }

    @Override
    public List<BookDTO> findAllBooks() {
        return bookMapper.findAllBooks();
    }
}
