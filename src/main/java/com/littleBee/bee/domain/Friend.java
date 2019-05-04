package com.littleBee.bee.domain;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Data
@EntityScan
public class Friend {
    private int id;
    private int user_id;
    private int friend_user_id;
    private String friendName;
}
