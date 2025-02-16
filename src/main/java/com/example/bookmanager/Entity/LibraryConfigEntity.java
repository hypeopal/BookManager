package com.example.bookmanager.Entity;

import lombok.Data;

@Data
public class LibraryConfigEntity {
    private Long id;
    private String key;
    private String value;
    private String description;
}
