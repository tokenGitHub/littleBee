package com.littleBee.bee.service.work;

import com.littleBee.bee.domain.Work;

import java.util.List;

public interface WorkService {
    List<Work> listWorkByPositionAndIdentity(String province, String city, int identity);
}
