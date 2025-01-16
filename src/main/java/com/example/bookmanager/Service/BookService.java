package com.example.bookmanager.Service;

import com.example.bookmanager.DTO.BookDTO;
import com.example.bookmanager.DTO.BookRequestDTO;
import com.example.bookmanager.Entity.BookInformation;

import java.util.List;

public interface BookService {
    BookInformation findByTitle(String title);
    List<BookDTO> findAllBooks();
    void addBooks(BookRequestDTO bookRequestDTO);
}
