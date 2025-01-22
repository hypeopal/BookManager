package com.example.bookmanager.Entity;

import com.example.bookmanager.Type.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Long id;
    private String isbn;
    private BookStatus status = BookStatus.AVAILABLE;
    private Integer library = null;
}
