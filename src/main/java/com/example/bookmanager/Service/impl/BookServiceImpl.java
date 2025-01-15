package com.example.bookmanager.Service.impl;

import com.example.bookmanager.Entity.Book;
import com.example.bookmanager.Mapper.BookMapper;
import com.example.bookmanager.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public Book findByTitle(String title) {
        return bookMapper.findByTitle(title);
    }
}
