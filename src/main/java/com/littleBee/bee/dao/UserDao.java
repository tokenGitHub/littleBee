package com.littleBee.bee.dao;

import com.littleBee.bee.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {
    void insertUser(@Param("user") User user);
    String userLogin(@Param("userName") String userName);
    User selectUserByUserName(@Param("userName") String userName);
    User selectUserById(@Param("id") int id);
}
