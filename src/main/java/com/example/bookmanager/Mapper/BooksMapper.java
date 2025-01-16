package com.example.bookmanager.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BooksMapper {
    @Insert("insert into books (isbn) values (#{isbn})")
    void insertBook(String isbn);
}
