package com.example.bookmanager.Entity;

import com.example.bookmanager.Type.BookCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookInformation {
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private BookCategory category;
}
