package com.pixeldreamer.community.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import com.pixeldreamer.community.model.Users;


@Mapper
public interface UserMapper {
    @Insert("INSERT INTO users (name, account_id, token, gmt_create, gmt_modified) values (#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified})")
    void insert(Users users);
}
