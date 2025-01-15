package com.example.bookmanager.Mapper;

import com.example.bookmanager.Entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BookMapper {
    @Select("select * from book_information where title like '%${title}%'")
    Book findByTitle(String title);
}
