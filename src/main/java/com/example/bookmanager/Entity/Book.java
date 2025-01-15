package com.example.bookmanager.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {
    private int id;
    private String isbn;
    private String author;
    private String title;
    private String publisher;

    public Book(int id, String isbn, String author, String title, String publisher) {
        this.id = id;
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.publisher = publisher;
    }
    public Book() {}
}
