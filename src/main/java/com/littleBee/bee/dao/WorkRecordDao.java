package com.littleBee.bee.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WorkRecordDao {
    void insertWorkRecord(@Param("userId") int userId,@Param("workId") int workId);
    int selectActualByWorkId(@Param("workId") int workId);
}
