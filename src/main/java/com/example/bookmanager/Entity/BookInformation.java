package com.example.bookmanager.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookInformation {
    private String isbn;
    private String author;
    private String title;
    private String publisher;
}
