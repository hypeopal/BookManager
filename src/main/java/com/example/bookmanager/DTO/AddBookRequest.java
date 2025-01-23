package com.example.bookmanager.DTO;

import com.example.bookmanager.Annotation.IsbnValid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class AddBookRequest {
    @Pattern(regexp = "^97[89]-[0-9]{1,5}-[0-9]{1,7}-[0-9]{1,6}-[0-9X]$", message = "invalid isbn format")
    @IsbnValid
    private String isbn;       // ISBN
    private String title;      // 书名
    private String author;     // 作者
    private String publisher;  // 出版社
    @PositiveOrZero(message = "count must be non-negative")
    private int count;         // 添加的书籍数量
    private int library;       // 所属图书馆
}
