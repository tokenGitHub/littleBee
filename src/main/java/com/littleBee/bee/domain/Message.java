package com.littleBee.bee.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Date;

@Data
@EntityScan
public class Message {
    private int id;
    private int sendUserId;
    private String sendName;
    private int receiveUserId;
    private String context;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date sendTime;
}
