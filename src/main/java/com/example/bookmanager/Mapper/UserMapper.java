package com.example.bookmanager.Mapper;

import com.example.bookmanager.Entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("select count(*) from users where username = #{username}")
    int findByUsername(String username);

    @Insert("insert into users (username, password) values (#{username}, #{password})")
    void insertUser(String username, String password);

    @Select("select password from users where username = #{username}")
    String getPasswordHashByUsername(String username);

    @Select("select * from users where username = #{username}")
    User getUserByUsername(String username);

    @Delete("delete from users where username = #{username}")
    void deleteUser(String username);

    @Update("update users set admin = true where id = #{id}")
    void setAdmin(Long id);

    @Update("update users set admin = false where id = #{id}")
    void unsetAdmin(Long id);

    @Update("update users set status = false where id = #{id}")
    void banUser(Long id);

    @Update("update users set status = true where id = #{id}")
    void unbanUser(Long id);
}
