package com.example.bookmanager.DTO;

import com.example.bookmanager.Type.BookCategory;
import com.example.bookmanager.Type.BookStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class BorrowedBook extends BookDTO {
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private int renewTimes;
    private boolean returned;

    public BorrowedBook(Long id, String isbn, String title, String author, String publisher,
                        BookStatus status, String library, BookCategory category,
                        LocalDateTime borrowDate, LocalDateTime returnDate,
                        int renewTimes, boolean returned) {
        super(id, isbn, title, author, publisher, status, library, category);
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.renewTimes = renewTimes;
        this.returned = returned;
    }
}
