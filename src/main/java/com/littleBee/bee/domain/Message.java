package com.littleBee.bee.domain;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Date;

@Data
@EntityScan
public class Message {
    private int id;
    private int sendUserId;
    private int receiveUserId;
    private String context;
    private Date sendTime;
}
