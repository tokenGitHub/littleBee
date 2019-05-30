package com.littleBee.bee.dto;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Data
@EntityScan
public class FriendRecord {
    private int id;
    private int userId;
    private String realName;
    private String context;
    private int status;
}
