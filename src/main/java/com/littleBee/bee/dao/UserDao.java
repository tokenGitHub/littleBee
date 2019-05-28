package com.littleBee.bee.dao;

import com.littleBee.bee.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {
    void insertUser(@Param("user") User user);
    String userLogin(@Param("userName") String userName);
    User selectUserByUserName(@Param("userName") String userName);
    User selectUserById(@Param("id") int id);
    List<User> listFriendByUserId(@Param("id") int id);
    List<User> listUserByUserTele(@Param("tele") String tele);
    List<User> listUserByRealName(@Param("realName") String realName);
    void examine(@Param("userId") int userId, @Param("status") int status);
}
