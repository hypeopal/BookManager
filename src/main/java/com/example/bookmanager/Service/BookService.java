package com.example.bookmanager.Service;

import com.example.bookmanager.DTO.BookDTO;
import com.example.bookmanager.DTO.AddBookRequest;
import com.example.bookmanager.DTO.BookInfoRequest;
import com.example.bookmanager.Entity.BookInformation;

import java.util.List;

public interface BookService {
    BookInformation findByTitle(String title);
    BookDTO findById(Long id);
    List<BookDTO> findAllBooks();
    void addBooks(AddBookRequest addBookRequest);
    void addBookInfo(BookInfoRequest bookInfoRequest);
}
