package com.littleBee.bee.service.work;

import com.littleBee.bee.domain.Work;

import java.util.List;

public interface WorkService {
    List<Work> listWorkByPositionAndIdentity(String province, String city, int identity);
    List<Work> listAllFullTimeWork();
    List<Work> listAllWork();
    List<Work> listWorkRecord(int userId);

    void saveWork(Work work);
}
