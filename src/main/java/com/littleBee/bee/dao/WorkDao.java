package com.littleBee.bee.dao;

import com.littleBee.bee.domain.User;
import com.littleBee.bee.domain.Work;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WorkDao {
    List<Work> listWorkByPositionAndIdentity(@Param("city") String city, @Param("identity") int identity);
    List<Work> listAllFullTimeWork();
    List<Work> listAllWork();
    List<Work> listWorkRecord(@Param("userId") int userId);

    void saveWork(@Param("work") Work work);
    List<Work> listAllReleaseWorkByUserIdAndIdentity(@Param("userId") int userId, @Param("identity") int identity);
    List<User> listUserByWorkId(@Param("workId") int workId);
    List<Work> listWorkByWorkName(@Param("workName") String workName);
}
