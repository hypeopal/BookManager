package com.example.bookmanager.Mapper;

import com.example.bookmanager.Entity.LibraryConfigEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LibraryConfigMapper {
    @Select("select * from library_config")
    @Results({
            @Result(property = "key", column = "config_key"),
            @Result(property = "value", column = "config_value")
    })
    List<LibraryConfigEntity> findConfig();

    @Update("update library_config set config_value = #{value} where config_key = #{key}")
    void updateConfig(String key, String value);
}
