package com.example.bookmanager.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ReserveRecordMapper {
    @Insert("insert into reserve_records (user_id, book_id) values (#{userId}, #{bookId})")
    void insertRecord(Long userId, Long bookId);

    @Select("select count(*) from reserve_records where user_id = #{userId} and status = 'RESERVED'::reserve_status")
    int countReserve(Long userId);

    @Select("select id from reserve_records where book_id = #{bookId} and user_id = #{userId} and status = 'RESERVED'::reserve_status")
    Long getReserveIdByBookIdAndUserId(Long bookId, Long userId);

    @Update("update reserve_records set status = #{status}::reserve_status where id = #{reserveId}")
    void setStatus(Long reserveId, String status);
}
