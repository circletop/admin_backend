package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接口统一返回包装类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;
    private String msg;
    private Object data;

    // 成功的处理
    public static Result success() {
        return new Result(Constants.CODE_200, "成功", null);
    }

    public static Result success(Object data) {
        return new Result(Constants.CODE_200, "成功", data);
    }

    // 失败的处理
    public static Result error(Integer code, String msg) {
        return new Result(code, msg, null);
    }

    public static Result error() {
        return new Result(Constants.CODE_500, "系统错误", null);
    }



}
