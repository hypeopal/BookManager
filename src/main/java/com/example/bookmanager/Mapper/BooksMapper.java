package com.example.bookmanager.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BooksMapper {
    @Insert("insert into books (isbn, library) values (#{isbn}, #{library})")
    void insertBook(String isbn, int library);
}
