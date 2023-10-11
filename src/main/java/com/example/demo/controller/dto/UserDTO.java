package com.example.demo.controller.dto;

import lombok.Data;

/**
 * 接寿前端登陆请求的参数
 */
@Data
public class UserDTO {
    private String username;
    private String password;
}
