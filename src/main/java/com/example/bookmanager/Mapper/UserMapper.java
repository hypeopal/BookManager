package com.example.bookmanager.Mapper;

import com.example.bookmanager.Entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
}
