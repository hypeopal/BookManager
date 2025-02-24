package com.example.bookmanager.Mapper;

import com.example.bookmanager.DTO.ReservedBook;
import com.example.bookmanager.Entity.ReserveRecord;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ReserveRecordMapper {
    @Insert("insert into reserve_records (user_id, book_id, borrow_date) values (#{userId}, #{bookId}, #{borrowDate})")
    void insertRecord(Long userId, Long bookId, LocalDateTime borrowDate);

    @Select("select count(*) from reserve_records where user_id = #{userId} and status = 'RESERVED'::reserve_status")
    int countReserve(Long userId);

    @Select("select id from reserve_records where book_id = #{bookId} and user_id = #{userId} and status = 'RESERVED'::reserve_status")
    Long getReserveIdByBookIdAndUserId(Long bookId, Long userId);

    @Select("select * from reserve_records where user_id = #{userId} and book_id = #{bookId} and status = 'RESERVED'::reserve_status")
    ReserveRecord getReserveRecordByUserIdAndBookId(Long userId, Long bookId);

    @Update("update reserve_records set status = #{status}::reserve_status where id = #{reserveId}")
    void setStatus(Long reserveId, String status);

    @Select("select b.id, b.isbn, bi.title, bi.author, bi.publisher, b.status, l.name as library, bi.category, rr.reserve_date, rr.borrow_date, rr.status as reserve_status " +
            "from reserve_records rr " +
            "join books b on rr.book_id = b.id " +
            "join book_information bi on b.isbn = bi.isbn " +
            "join libraries l on l.id = b.library " +
            "where rr.user_id = #{userId}")
    List<ReservedBook> getReservedBookByUserId(Long userId);

    @Select("select * from reserve_records where status = 'RESERVED'::reserve_status and borrow_date < now()")
    List<ReserveRecord> getOverdueRecord();

    @Update("update reserve_records set borrow_date = #{borrowDate} where id = #{reserveId}")
    void setBorrowDate(Long reserveId, LocalDateTime borrowDate);
}
