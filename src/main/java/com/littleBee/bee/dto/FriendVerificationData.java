package com.littleBee.bee.dto;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Data
@EntityScan
public class FriendVerificationData {
    private int recordId;
    private int agreeOrNot;
}
