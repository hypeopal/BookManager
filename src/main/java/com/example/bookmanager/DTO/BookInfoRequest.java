package com.example.bookmanager.DTO;

import com.example.bookmanager.Annotation.BookCategoryValid;
import com.example.bookmanager.Annotation.IsbnValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @BookCategoryValid
    private String category;
}
