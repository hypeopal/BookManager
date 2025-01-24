package com.example.bookmanager.Mapper;

import com.example.bookmanager.DTO.BookDTO;
import com.example.bookmanager.Entity.BookInformation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookInformationMapper {
    @Select("select * from book_information where title like '%${title}%'")
    BookInformation findByTitle(String title);

    @Select("select * from book_information where isbn = #{isbn}")
    BookInformation findByIsbn(String isbn);

    @Select("select b.id, b.isbn, bi.title, bi.author, bi.publisher, b.status, l.name " +
            "from book_information bi join books b on bi.isbn = b.isbn " +
            "left join libraries l on l.id = b.library" +
            " where b.id = #{id}")
    @Results({
            @Result(property = "library", column = "name"),
    })
    BookDTO findById(Long id);

    List<BookDTO> findAllBooks(String status);

    @Insert("insert into book_information (isbn, title, author, publisher) values (#{isbn}, #{title}, #{author}, #{publisher})")
    void insertBookInformation(BookInformation bookInformation);

    @Select("select count(*) from book_information where isbn = #{isbn}")
    int existsByIsbn(String isbn);

    @Delete("delete from book_information where isbn = #{isbn}")
    void deleteByIsbn(String isbn);
}
