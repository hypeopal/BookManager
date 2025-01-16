package com.example.bookmanager.Controller;

import com.example.bookmanager.DTO.BookDTO;
import com.example.bookmanager.DTO.BookRequestDTO;
import com.example.bookmanager.Entity.BookInformation;
import com.example.bookmanager.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /*
    * Get list of all books
    * /api/books
    * method:get
    * */
    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookService.findAllBooks();
    }

    /*
    * Search book by title
    * /api/books/search
    * method:get
    * */
    @GetMapping("/search")
    public BookInformation findByTitle(String title) {
        return bookService.findByTitle(title);
    }

    /*
    * add books
    * /api/books
    * method:post
    * */
    @PostMapping
    public ResponseEntity<String> addBooks(@RequestBody BookRequestDTO bookRequestDTO) {
        bookService.addBooks(bookRequestDTO);
        return ResponseEntity.ok("Book added successfully");
    }
}
