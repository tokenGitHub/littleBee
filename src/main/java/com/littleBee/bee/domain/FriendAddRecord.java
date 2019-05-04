package com.littleBee.bee.domain;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Data
@EntityScan
public class FriendAddRecord {
    private int id;
    private int userId;
    private int friendUserId;
    private String context;
    private int status;
}
