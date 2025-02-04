package com.example.bookmanager.Mapper;

import com.example.bookmanager.DTO.BookDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface BorrowRecordMapper {
    @Select("select b.id, b.isbn, bi.title, bi.author, bi.publisher, b.status, l.name, bi.category " +
            "from borrow_records br " +
            "join books b on b.id = br.book_id " +
            "join book_information bi on b.isbn = bi.isbn " +
            "join libraries l on l.id = b.library " +
            "where br.user_id = #{id}")
    List<BookDTO> getBorrowedBooks(Long id);

    @Insert("insert into borrow_records (user_id, book_id, return_date) values (#{userId}, #{bookId}, #{returnDate})")
    void insertRecord(Long userId, Long bookId, LocalDateTime returnDate);

    @Select("select count(*) from borrow_records where user_id = #{userId}")
    int countBorrow(Long userId);

    @Select("select count(*) from borrow_records where book_id = #{bookId} and user_id = #{userId}")
    int isBorrowedByUser(Long bookId, Long userId);

    @Delete("delete from borrow_records where book_id = #{bookId} and user_id = #{userId}")
    void deleteRecord(Long bookId, Long userId);
}
