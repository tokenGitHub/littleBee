package com.littleBee.bee.dto;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Data
@EntityScan
public class FriendRecord {
    private int id;
    private int userId;
    private int friendUserId;
    private String realName;
    private String context;
}
