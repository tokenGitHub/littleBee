package com.littleBee.bee.utills;

import lombok.Data;

public class JsonUtils {
    @Data
    static class Result{
        private String result;
        private Object data;

        public Result(String result, Object object){
            this.result = result;
            this.data = object;
        }
    }

    public static Result getSuccessResult(Object object){
        return new Result("success", object);
    }

    public static Result getFailResult(Object object){
        return new Result("fail", object);
    }
}
