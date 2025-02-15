package com.example.bookmanager.Entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BorrowRecord {
    private Long userId;
    private Long bookId;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private int renewTimes;
    private boolean returned;
    private Long reserveId;
}
