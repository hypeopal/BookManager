package com.example.bookmanager.DTO;

import com.example.bookmanager.Type.BookCategory;
import com.example.bookmanager.Type.BookStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class BorrowedBook extends BookDTO {
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private int renewTimes;
    private boolean returned;
    private String borrowStatus;

    public BorrowedBook(Long id, String isbn, String title, String author, String publisher,
                        BookStatus status, String library, BookCategory category,
                        LocalDateTime borrowDate, LocalDateTime returnDate,
                        int renewTimes, boolean returned, String borrowStatus) {
        super(id, isbn, title, author, publisher, status, library, category);
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.renewTimes = renewTimes;
        this.returned = returned;
        this.borrowStatus = borrowStatus;
    }
}
