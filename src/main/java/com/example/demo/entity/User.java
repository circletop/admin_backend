package com.example.demo.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName(value="sys_user")
public class User {
    @TableId
    private Integer id;
    private String username;
    @JsonIgnore // 忽略password 字段， 不在前端展示
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String address;

}
