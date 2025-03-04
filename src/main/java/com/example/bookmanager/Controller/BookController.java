package com.example.bookmanager.Controller;

import com.example.bookmanager.Annotation.RequireAdmin;
import com.example.bookmanager.DTO.AddBookRequest;
import com.example.bookmanager.DTO.BookDTO;
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
                              @RequestParam(required = false) Integer count,
                              @RequestParam(required = false) String category,
                              @RequestParam(required = false) String library) {
        try {
            PageContent<BookDTO> list = bookService.findAllBooks(status, page, count, category, library);
            return ResultData.success("Get list of books successfully", list);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(4, 400, "Failed to get list of books: " + e.getMessage());
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
            throw new BusinessException(4, 400, "Failed to get book by title: " + e.getMessage());
        }

    }

    /*
     * add books
     * /api/books
     * method:post
     * */
    @RequireAdmin
    @PostMapping
    public Result addBooks(@Valid @RequestBody AddBookRequest addBookRequest) {
        try {
            bookService.addBooks(addBookRequest);
            return Result.success("Books added successfully");
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(2, 400, "Library not exists");
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(4, 400, "Failed to add books: " + e.getMessage());
        }
    }

    @RequireAdmin
    @DeleteMapping("/{id}")
    public Result deleteBook(@PathVariable("id") Long id) {
        try {
            bookService.deleteBook(id);
            return Result.success("Delete book successfully");
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(4, 400, "Failed to delete book: " + e.getMessage());
        }
    }

    /*
     * add book info
     * /api/books/info
     * method:post
     * */
    @RequireAdmin
    @PostMapping("/info")
    public Result addBookInfo(@Valid @RequestBody BookInfoRequest bookInfoRequest) {
        try {
            bookService.addBookInfo(bookInfoRequest);
            return Result.success("Book info added successfully");
        } catch (DuplicateKeyException e) {
            throw new BusinessException(2, 400, "Book info already exists");
        } catch (BusinessException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new BusinessException(2, 400, "Book category not exists");
        }catch (Exception e) {
            throw new BusinessException(4, 400, "Failed to add book info: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result getBookById(@PathVariable("id") Long id) {
        try {
            BookDTO book = bookService.findById(id);
            if (book == null) throw new BusinessException(2, 400, "Book id not exists");
            return ResultData.success("Get book by id successfully", book);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(4, 400, "Failed to get book by id: " + e.getMessage());
        }
    }

    @PatchMapping("/borrow/{id}")
    public Result borrowBook(@PathVariable("id") Long id) {
        try {
            bookService.borrowBook(id);
            return Result.success("Borrow book successfully");
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(4, 400, "Failed to borrow book: " + e.getMessage());
        }
    }

    @GetMapping("/borrowed")
    public Result getBorrowedBooks() {
        try {
            return ResultData.success("Get borrowed books successfully", bookService.getBorrowedBooks());
        } catch (Exception e) {
            throw new BusinessException(4, 400, "Failed to get borrowed books: " + e.getMessage());
        }
    }

    @PatchMapping("/return/{id}")
    public Result returnBook(@PathVariable("id") Long id) {
        try {
            bookService.returnBook(id);
            return Result.success("Return book successfully");
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(4, 400, "Failed to return book: " + e.getMessage());
        }
    }

    @PatchMapping("/renew/{id}")
    public Result renewBorrowedBook(@PathVariable("id") Long id) {
        try {
            bookService.renewBorrowedBook(id);
            return Result.success("Renew borrowed book successfully");
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(4, 400, "Failed to renew borrowed book: " + e.getMessage());
        }
    }

    @PostMapping("/reserve/{id}")
    public Result reserveBook(@PathVariable("id") Long id) {
        try {
            bookService.reserveBook(id);
            return Result.success("Reserve book successfully");
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(4, 400, "Failed to reserve book: " + e.getMessage());
        }
    }

    @PostMapping("/cancelReserve/{id}")
    public Result cancelReserve(@PathVariable("id") Long id) {
        try {
            bookService.cancelReserve(id);
            return Result.success("Cancel reserve successfully");
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(4, 400, "Failed to cancel reserve: " + e.getMessage());
        }
    }

    @GetMapping("/reserved")
    public Result getReservedBook() {
        try {
            return ResultData.success("Get reserved books successfully", bookService.getReservedBooks());
        } catch (Exception e) {
            throw new BusinessException(4, 400, "Failed to get reserved books: " + e.getMessage());
        }
    }
}
