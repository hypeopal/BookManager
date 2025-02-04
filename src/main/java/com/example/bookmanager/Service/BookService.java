package com.example.bookmanager.Service;

import com.example.bookmanager.DTO.BookDTO;
import com.example.bookmanager.DTO.AddBookRequest;
import com.example.bookmanager.DTO.BookInfoRequest;
import com.example.bookmanager.DTO.PageContent;
import com.example.bookmanager.Entity.BookInformation;

import java.util.List;


public interface BookService {
    BookInformation findByTitle(String title);
    BookDTO findById(Long id);
    PageContent<BookDTO> findAllBooks(String status, Integer page, Integer count, String category);
    void addBooks(AddBookRequest addBookRequest);
    void addBookInfo(BookInfoRequest bookInfoRequest);
    void deleteBook(Long id);
    void borrowBook(Long id);
    List<BookDTO> getBorrowedBooks();
    void returnBook(Long id);
}
