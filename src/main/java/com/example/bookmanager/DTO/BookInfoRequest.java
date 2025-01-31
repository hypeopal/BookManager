package com.example.bookmanager.DTO;

import com.example.bookmanager.Annotation.IsbnValid;
import com.example.bookmanager.Type.BookCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class BookInfoRequest {
    @Pattern(regexp = "^97[89]-[0-9]{1,5}-[0-9]{1,7}-[0-9]{1,6}-[0-9X]$", message = "invalid isbn format")
    @IsbnValid
    private String isbn;       // ISBN
    @NotBlank(message = "Title is required")
    private String title;      // 书名
    @NotBlank(message = "Author is required")
    private String author;     // 作者
    @NotBlank(message = "Publisher is required")
    private String publisher;  // 出版社
    @NotBlank(message = "Category is required")
    private String category;
}
