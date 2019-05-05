package com.littleBee.bee.dao;

import com.littleBee.bee.domain.Work;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WorkDao {
    List<Work> listWorkByPositionAndIdentity(@Param("province") String province, @Param("city") String city, @Param("identity") int identity);
}
