package com.example.bookmanager.Controller;

import com.example.bookmanager.Entity.Book;
import com.example.bookmanager.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping("/book")
    public Book findByTitle(String title) {
        return bookService.findByTitle(title);
    }
}
