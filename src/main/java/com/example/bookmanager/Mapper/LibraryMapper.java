package com.example.bookmanager.Mapper;

import com.example.bookmanager.DTO.BookDTO;
import com.example.bookmanager.Entity.Library;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LibraryMapper {
    @Select("select * from libraries")
    List<Library> findAllLibrary();

    @Select("select b.id, b.isbn, bi.title, bi.author, bi.publisher, b.status, l.name as library, bi.category " +
            "from libraries l " +
            "join books b on l.id = b.library " +
            "join book_information bi on b.isbn = bi.isbn " +
            "where l.name = #{name}")
    List<BookDTO> findAllBooksByLib(String name);
}
