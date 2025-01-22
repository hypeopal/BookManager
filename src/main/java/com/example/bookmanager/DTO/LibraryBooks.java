package com.example.bookmanager.DTO;

import com.example.bookmanager.Entity.Library;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LibraryBooks extends Library {
    List<BookDTO> books;
    public LibraryBooks(int id, String name, String address, String phone, List<BookDTO> books) {
        super(id, name, address, phone);
        this.books = books;
    }
}
