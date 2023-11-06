package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("sys_role_menu")
@ApiModel(value = "RoleMenu对象",description = "角色菜单关系表")
@Data
public class RoleMenu {
    @ApiModelProperty("角色ID")
    private Integer roleId;
    @ApiModelProperty("菜单ID")
    private Integer menuId;
}
