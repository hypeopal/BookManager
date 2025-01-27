package com.example.bookmanager.Controller;

import com.example.bookmanager.Annotation.RequireAdmin;
import com.example.bookmanager.DTO.BookDTO;
import com.example.bookmanager.DTO.AddBookRequest;
import com.example.bookmanager.DTO.BookInfoRequest;
import com.example.bookmanager.DTO.PageContent;
import com.example.bookmanager.Entity.BookInformation;
import com.example.bookmanager.Exception.BusinessException;
import com.example.bookmanager.Service.BookService;
import com.example.bookmanager.Utils.Result;
import com.example.bookmanager.Utils.ResultData;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


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
    public Result getAllBooks(@RequestParam(required = false) String status,
                              @RequestParam(required = false) Integer page,
                              @RequestParam(required = false) Integer count) {
        if (status != null && status.trim().isEmpty()) status = null;
        if (page == null || count == null) {
            page = null; count = null;
        }
        try {
            PageContent<BookDTO> list = bookService.findAllBooks(status, page, count);
            return ResultData.success("Get list of books successfully", list);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            return Result.error(2, "Failed to get list of books: " + e.getMessage());
        }
    }

    /*
     * Search book by title
     * /api/books/search
     * */
    @GetMapping("/search")
    public Result findByTitle(String title) {
        try {
            BookInformation bookInformation = bookService.findByTitle(title);
            return ResultData.success("Get book by title successfully", bookInformation);
        } catch (Exception e) {
            return Result.error(3, "Failed to get book by title: " + e.getMessage());
        }

    }

    /*
     * add books
     * /api/books
     * method:post
     * */
    @RequireAdmin
    @PostMapping
    public Result addBooks(@Valid @RequestBody AddBookRequest addBookRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BusinessException(7, bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        try {
            bookService.addBooks(addBookRequest);
            return Result.success("Books added successfully");
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(7, "Library not exists");
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            return Result.error(1, "Failed to add books: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteBook(@PathVariable("id") Long id) {
        try {
            bookService.deleteBook(id);
            return Result.success("Delete book successfully");
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            return Result.error(1, "Failed to delete book: " + e.getMessage());
        }
    }

    /*
     * add book info
     * /api/books/info
     * method:post
     * */
    @PostMapping("/info")
    public Result addBookInfo(@Valid @RequestBody BookInfoRequest bookInfoRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BusinessException(7, bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        try {
            bookService.addBookInfo(bookInfoRequest);
            return Result.success("Book info added successfully");
        } catch (DuplicateKeyException e) {
            throw new BusinessException(1, "Book info already exists");
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            return Result.error(1, "Failed to add book info: " + e.getClass().getSimpleName());
        }
    }

    @GetMapping("/{id}")
    public Result getBookById(@PathVariable("id") Long id) {
        try {
            BookDTO book = bookService.findById(id);
            if (book == null) throw new BusinessException(7, "Book id not exists");
            return ResultData.success("Get book by id successfully", book);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            return Result.error(7, "Failed to get book by id: " + e.getMessage());
        }
    }
}
