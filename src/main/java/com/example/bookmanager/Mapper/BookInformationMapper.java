package com.example.bookmanager.Mapper;

import com.example.bookmanager.DTO.BookDTO;
import com.example.bookmanager.Entity.BookInformation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookInformationMapper {
    @Select("select * from book_information where title like '%${title}%'")
    BookInformation findByTitle(String title);

    @Select("select * from book_information where isbn = #{isbn}")
    BookInformation findByIsbn(String isbn);

    @Select("select b.id, b.isbn, bi.title, bi.author, bi.publisher, b.status " +
            "from books b " +
            "join book_information bi on b.isbn = bi.isbn")
    List<BookDTO> findAllBooks();

    @Insert("insert into book_information (isbn, title, author, publisher) values (#{isbn}, #{title}, #{author}, #{publisher})")
    void insertBookInformation(BookInformation bookInformation);

    @Select("select count(*) from book_information where isbn = #{isbn}")
    int existsByIsbn(String isbn);
}
