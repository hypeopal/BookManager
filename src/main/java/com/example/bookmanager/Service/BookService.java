package com.example.bookmanager.Service;

import com.example.bookmanager.Entity.Book;

public interface BookService {
    Book findByTitle(String title);
}
