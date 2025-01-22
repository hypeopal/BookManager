package com.example.bookmanager.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Library {
    private int id;
    private String name;
    private String address;
    private String phone;
}
