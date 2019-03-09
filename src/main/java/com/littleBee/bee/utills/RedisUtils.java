package com.littleBee.bee.utills;

import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedisUtils<T> {
    @Resource
    private ValueOperations valueOperations;

    public void setValue(String redisKey, T value){
        valueOperations.set(redisKey, value);
    }

    public void setValue(String redisKey, T value, long second){
        valueOperations.set(redisKey, value, second);
    }

    public T getValue(String redisKey){
        return (T)(valueOperations.get(redisKey));
    }

}
