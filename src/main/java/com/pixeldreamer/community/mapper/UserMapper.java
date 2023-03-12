package com.pixeldreamer.community.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import com.pixeldreamer.community.model.Users;


@Mapper
public interface UserMapper {
    @Insert("INSERT INTO users (name, account_id, token, gmt_create, gmt_modified, avatar_url) values (#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl})")
    void insert(Users users);

    @Select("select * from users where token = #{token}")
    Users findByToken(@Param("token") String token);

    @Select("select * from users where id = #{id}")
    Users fingById(@Param("id") Integer id);
}
