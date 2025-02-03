package com.example.bookmanager.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

@Mapper
public interface BorrowRecordMapper {
    @Insert("insert into borrow_records (user_id, book_id, return_date) values (#{userId}, #{bookId}, #{returnDate})")
    void insertRecord(Long userId, Long bookId, LocalDateTime returnDate);

    @Select("select count(*) from borrow_records where user_id = #{userId}")
    int countBorrow(Long userId);
}
