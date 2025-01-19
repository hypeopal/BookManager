package com.example.bookmanager.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookRequestDTO {
    @NotBlank(message = "isbn is required")
    @Size(min = 17, max = 17, message = "isbn must be 17 characters")
    @Pattern(regexp = "^97[89]-[0-9]{1,5}-[0-9]{1,7}-[0-9]{1,6}-[0-9X]$", message = "invalid isbn format")
    private String isbn;       // ISBN
    private String title;      // 书名
    private String author;     // 作者
    private String publisher;  // 出版社
    private int count;         // 添加的书籍数量
}
