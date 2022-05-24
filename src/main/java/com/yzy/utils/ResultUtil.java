package com.yzy.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yuanzhiyong
 * @version 1.0.0
 * @ClassName Result.java
 * @Description 统一结果封装
 * @createTime 2021年03月29日 16:57:00
 */
@Data
public class ResultUtil implements Serializable {

    private int code;           //200是正常，非200表示异常
    private String msg;
    private int count;
    private Object data;

    public static ResultUtil succ(Object data, int count) {

        return succ(200, "操作成功", count,  data);
    }

    public static ResultUtil succ(int code, String msg, int count, Object data) {
        ResultUtil r = new ResultUtil();
        r.setCode(code);
        r.setMsg(msg);
        r.setCount(count);
        r.setData(data);
        return r;
    }

    public static ResultUtil fail(String msg) {

        return fail(400, msg, 0,null);
    }


    public static ResultUtil fail(int code, String msg, int count, Object data) {
        ResultUtil r = new ResultUtil();
        r.setCode(code);
        r.setMsg(msg);
        r.setCount(count);
        r.setData(data);
        return r;
    }
}
