package com.example.bookmanager.Controller;

import com.example.bookmanager.DTO.BookDTO;
import com.example.bookmanager.DTO.AddBookRequest;
import com.example.bookmanager.DTO.BookInfoRequest;
import com.example.bookmanager.Entity.BookInformation;
import com.example.bookmanager.Exception.BusinessException;
import com.example.bookmanager.Service.BookService;
import com.example.bookmanager.Utils.Result;
import jakarta.validation.Valid;
import org.springframework.dao.DuplicateKeyException;
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
            if (e.getClass().getSimpleName().equals("BusinessException")) throw e;
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
    public Result<String> addBooks(@Valid @RequestBody AddBookRequest addBookRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error(7, bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        try {
            bookService.addBooks(addBookRequest);
            return Result.success(null, "Books added successfully");
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equals("BusinessException")) throw e;
            return Result.error(1, "Failed to add books: " + e.getClass().getSimpleName());
        }
    }

    /*
    * add book info
    * /api/books/info
    * method:post
    * */
    @PostMapping("/info")
    public Result<String> addBookInfo(@Valid @RequestBody BookInfoRequest bookInfoRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error(7, bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        try {
            bookService.addBookInfo(bookInfoRequest);
            return Result.success(null, "Book info added successfully");
        } catch (DuplicateKeyException e) {
            throw new BusinessException(1, "Book info already exists");
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equals("BusinessException")) throw e;
            return Result.error(1, "Failed to add book info: " + e.getClass().getSimpleName());
        }
    }
}
