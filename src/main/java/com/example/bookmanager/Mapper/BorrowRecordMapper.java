package com.example.bookmanager.Mapper;

import com.example.bookmanager.DTO.BorrowedBook;
import com.example.bookmanager.Entity.BorrowRecord;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface BorrowRecordMapper {
    @Select("select b.id, b.isbn, bi.title, bi.author, bi.publisher, b.status, l.name, bi.category, br.borrow_date, br.return_date, br.renew_times, br.returned " +
            "from borrow_records br " +
            "join books b on b.id = br.book_id " +
            "join book_information bi on b.isbn = bi.isbn " +
            "join libraries l on l.id = b.library " +
            "where br.user_id = #{id}")
    @Results({
            @Result(property = "library", column = "name")
    })
    List<BorrowedBook> getBorrowedBooks(Long id);

    @Insert("insert into borrow_records (user_id, book_id, return_date) values (#{userId}, #{bookId}, #{returnDate})")
    void insertRecord(Long userId, Long bookId, LocalDateTime returnDate);

    @Select("select count(*) from borrow_records where user_id = #{userId} and returned = false")
    int countBorrow(Long userId);

    @Insert("insert into borrow_records (user_id, book_id, return_date, reserve_id) values (#{userId}, #{bookId}, #{returnDate}, #{reserveId})")
    void insertRecordWithReserve(Long userId, Long bookId, LocalDateTime returnDate, Long reserveId);

    @Update("update borrow_records set return_date = #{returnDate} where book_id = #{bookId} and user_id = #{userId} and returned = false")
    void setReturnDate(Long bookId, Long userId, LocalDateTime returnDate);

    @Update("update borrow_records set returned = #{status} where book_id = #{bookId} and user_id = #{userId} and returned = false")
    void setReturnStatus(Long bookId, Long userId, boolean status);

    @Select("select * from borrow_records where user_id = #{userId} and returned = false and book_id = #{bookId}")
    BorrowRecord getBorrowRecordByBookIdAndUserId(Long bookId, Long userId);

    @Update("update borrow_records set renew_times = #{i} where book_id = #{bookId} and user_id = #{userId} and returned = false")
    void setRenewTimes(Long bookId, Long userId, int i);
}
