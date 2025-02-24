package com.example.bookmanager.DTO;

import com.example.bookmanager.Type.BookCategory;
import com.example.bookmanager.Type.BookStatus;
import com.example.bookmanager.Type.ReserveStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReservedBook extends BookDTO {
    private LocalDateTime reserveDate;
    private LocalDateTime borrowDate;
    private ReserveStatus reserveStatus;

    public ReservedBook(Long id, String isbn, String title, String author, String publisher,
                        BookStatus status, String library, BookCategory category,
                        LocalDateTime reserveDate, LocalDateTime borrowDate, ReserveStatus reserveStatus) {
        super(id, isbn, title, author, publisher, status, library, category);
        this.reserveDate = reserveDate;
        this.reserveStatus = reserveStatus;
        this.borrowDate = borrowDate;
    }
}
