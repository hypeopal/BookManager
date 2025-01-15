package com.example.bookmanager.Mapper;

import com.example.bookmanager.DTO.BookDTO;
import com.example.bookmanager.Entity.BookInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookMapper {
    @Select("select * from book_information where title like '%${title}%'")
    BookInformation findByTitle(String title);

    @Select("select b.id, b.isbn, bi.title, bi.author, bi.publisher " +
            "from books b " +
            "join book_information bi on b.isbn = bi.isbn")
    List<BookDTO> findAllBooks();
}
