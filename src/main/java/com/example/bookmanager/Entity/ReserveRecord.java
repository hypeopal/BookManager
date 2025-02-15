package com.example.bookmanager.Entity;

import com.example.bookmanager.Type.ReserveStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReserveRecord {
    private Long id;
    private Long userId;
    private Long bookId;
    private LocalDateTime reserveDate;
    private ReserveStatus status;
}
