package com.example.bookmanager.Controller;

import com.example.bookmanager.DTO.BookDTO;
import com.example.bookmanager.Entity.BookInformation;
import com.example.bookmanager.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    /*
    * Get list of all books
    * /api/books
    * */
    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookService.findAllBooks();
    }

    /*
    * Search book by title
    * /api/books/search
    * */
    @GetMapping("/search")
    public BookInformation findByTitle(String title) {
        return bookService.findByTitle(title);
    }
}
