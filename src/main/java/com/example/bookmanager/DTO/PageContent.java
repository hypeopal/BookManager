package com.example.bookmanager.DTO;

import lombok.Data;

import java.util.List;

@Data
public class PageContent<T> {
    private int page;
    private int count;
    private List<T> content;
}
