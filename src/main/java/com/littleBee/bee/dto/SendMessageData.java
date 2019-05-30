package com.littleBee.bee.dto;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Data
@EntityScan
public class SendMessageData {
    private int userId;
    private int targetUserId;
    private String context;
}
