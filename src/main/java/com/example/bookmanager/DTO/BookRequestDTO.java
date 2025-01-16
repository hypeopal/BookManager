package com.example.bookmanager.DTO;

import lombok.Data;

@Data
public class BookRequestDTO {
    private String isbn;       // ISBN
    private String title;      // 书名
    private String author;     // 作者
    private String publisher;  // 出版社
    private int count;         // 添加的书籍数量
}
