package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author circletop
 * @since 2023-10-27
 */
@Getter
@Setter
  @TableName("sys_menu")
@ApiModel(value = "Menu对象", description = "")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("Id")
      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("菜单名称")
      private String menuName;

      @ApiModelProperty("路径")
      private String path;

      @ApiModelProperty("图标")
      private String icon;

      @ApiModelProperty("菜单描述")
      private String description;

      @ApiModelProperty("父ID")
      @TableField(value = "pid")
      private Integer pId;

      @ApiModelProperty("层级")
      private Integer level;

      @ApiModelProperty("子菜单")
      @TableField(exist = false)
      private List<Menu> children;


}
