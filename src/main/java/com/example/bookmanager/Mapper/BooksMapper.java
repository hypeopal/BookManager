package com.example.bookmanager.Mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface BooksMapper {
    @Insert("insert into books (isbn, library) values (#{isbn}, #{library})")
    void insertBook(String isbn, int library);

    @Delete("delete from books where id = #{id}")
    void deleteBook(Long id);

    @Select("select status from books where id = #{id}")
    String getStatusById(Long id);

    @Update("update books set status = #{status}::book_status where id = #{id}")
    void updateStatusById(Long id, String status);
}
