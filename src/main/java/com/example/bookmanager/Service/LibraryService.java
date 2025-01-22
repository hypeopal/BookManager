package com.example.bookmanager.Service;

import com.example.bookmanager.DTO.LibraryBooks;
import com.example.bookmanager.Entity.Library;

import java.util.List;

public interface LibraryService {
    List<Library> findAllLibrary();
    List<LibraryBooks> findAllLibraryBooks();
}
