package com.littleBee.bee.service.work;

import com.littleBee.bee.domain.User;
import com.littleBee.bee.domain.Work;

import java.util.List;

public interface WorkService {
    List<Work> listWorkByPositionAndIdentity(String city, int identity);
    List<Work> listAllFullTimeWork();
    List<Work> listAllWork();
    List<Work> listWorkRecord(int userId);

    void saveWork(Work work);
    List<Work> listAllReleaseWorkByUserIdAndIdentity(int userId, int identity);
    List<User> listUserByWorkId(int workId);
    List<Work> listWorkByWorkName(String workName);

    void examine(int workId, int status);
    void insertWorkRecord(int userId,int workId);
}
