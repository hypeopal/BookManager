package com.example.bookmanager.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class AddBookRequest extends BookInfoRequest {
    @Positive(message = "count must be positive")
    private int count;         // 添加的书籍数量
    @NotNull(message = "Library must be specified")
    private int library;       // 所属图书馆

    public AddBookRequest(String isbn, String title, String author, String publisher, String category, int count, int library) {
        super(isbn, title, author, publisher, category);
        this.count = count;
        this.library = library;
    }
}
