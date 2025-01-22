package com.example.bookmanager.Controller;

import com.example.bookmanager.DTO.LibraryBooks;
import com.example.bookmanager.Entity.Library;
import com.example.bookmanager.Service.LibraryService;
import com.example.bookmanager.Utils.Result;
import com.example.bookmanager.Utils.ResultData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/library")
public class LibraryController {
    private final LibraryService libraryService;
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public Result getAllLib() {
        try {
            List<Library> list = libraryService.findAllLibrary();
            if (list != null) return ResultData.success("Get list of libraries successfully", list);
            else return Result.error(1, "Failed to get list of libraries");
        } catch (Exception e) {
            return Result.error(1, "Failed to get list of libraries: " + e.getMessage());
        }
    }

    @GetMapping("/books")
    public Result getAllBooksByLib() {
        try {
            List<LibraryBooks> list = libraryService.findAllLibraryBooks();
            if (list != null) return ResultData.success("Get list of books successfully", list);
            else return Result.error(1, "Failed to get list of books");
        } catch (Exception e) {
            return Result.error(1, "Failed to get list of books: " + e.getMessage());
        }
    }
}
