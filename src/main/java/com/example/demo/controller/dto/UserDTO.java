package com.example.demo.controller.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 接寿前端登陆请求的参数
 */
@Data
public class UserDTO {
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
    private String role;
}
