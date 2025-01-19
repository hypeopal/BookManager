package com.example.bookmanager.Controller;

import com.example.bookmanager.DTO.BookDTO;
import com.example.bookmanager.DTO.BookRequestDTO;
import com.example.bookmanager.Entity.BookInformation;
import com.example.bookmanager.Service.BookService;
import com.example.bookmanager.Utils.Result;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
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
    * */
    @GetMapping
    public Result<List<BookDTO>> getAllBooks() {
        try {
            List<BookDTO> list = bookService.findAllBooks();
            return Result.success(list, "Get list of books successfully");
        } catch (Exception e) {
            return Result.error(2, "Failed to get list of books: " + e.getMessage());
        }
    }

    /*
    * Search book by title
    * /api/books/search
    * */
    @GetMapping("/search")
    public Result<BookInformation> findByTitle(String title) {
        try {
            BookInformation bookInformation = bookService.findByTitle(title);
            return Result.success(bookInformation, "Get book by title successfully");
        } catch (Exception e) {
            return Result.error(3, "Failed to get book by title: " + e.getMessage());
        }

    }

    /*
    * add books
    * /api/books
    * method:post
    * */
    @PostMapping
    public Result<String> addBooks(@Valid @RequestBody BookRequestDTO bookRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error(7, bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        try {
            bookService.addBooks(bookRequestDTO);
            return Result.success(null, "Books added successfully");
        } catch (Exception e) {
            return Result.error(1, "Failed to add books: " + e.getMessage());
        }
    }
}
