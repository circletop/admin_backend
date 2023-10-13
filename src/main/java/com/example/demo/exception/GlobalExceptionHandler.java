package com.example.demo.exception;

import com.example.demo.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 如果抛出的serviceException, 则调用该方法
     * @param e 业务异常
     * @return Result
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException e) {
        return Result.error(e.getCode(),e.getMessage());
    }
}
