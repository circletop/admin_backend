package com.example.demo.controller;



import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.common.Result;
import com.example.demo.common.Constants;


import com.example.demo.service.IMenuService;
import com.example.demo.entity.Menu;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author circletop
 * @since 2023-10-27
 */

@RestController
@RequestMapping("/menu")
@Api(tags="菜单管理")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @ApiOperation(value = "新增或者修改信息",notes = "这里可以写一些详细信息")
    @PostMapping
    public Result save(@RequestBody Menu menu) {
        try{
            if(menu.getPId() == null) {
                menu.setPId(null);
            }
            menuService.saveOrUpdate(menu);
            return Result.success();
        } catch(SecurityException e) {
            return Result.error(Constants.CODE_500, "系统错误");
        }
    }

    @ApiOperation(value = "根据id删除数据",notes = "这里可以写一些详细信息")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        try{
            menuService.removeById(id);
            return Result.success();
        } catch ( SecurityException e) {
            return Result.error(Constants.CODE_500, "系统错误");
        }
    }

    @ApiOperation(value = "根据ids批量删除数据",notes = "这里可以写一些详细信息")
    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return menuService.removeByIds(ids);
    }

    @ApiOperation(value = "全量数据列表",notes = "这里可以写一些详细信息")
    @GetMapping
    public Result findAll() {
        List<Menu> list = menuService.list();
        // 找出数据中的一级菜单
        List<Menu> parentNodes = list.stream().filter(menu -> menu.getPId() == null).collect(Collectors.toList());
        setAllChildren(parentNodes, list);
        return Result.success(parentNodes);
    }

    @ApiOperation(value = "根据id查询数据",notes = "这里可以写一些详细信息")
    @GetMapping("/{id}")
    public Menu findOne(@PathVariable Integer id) {
        return menuService.getById(id);
    }

    @ApiOperation(value = "分页查询数据",notes = "这里可以写一些详细信息")
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam(required = false, defaultValue = "") String menuName) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        // 参数非空
        if(menuName != null && !menuName.isEmpty()) {
            queryWrapper.like("menu_name", menuName);
        }
        // todo 按条件模糊查询
        // 查询1级菜单
        queryWrapper.isNull("pid");
        queryWrapper.orderByDesc("id");
        Page<Menu> page = menuService.page(new Page<>(pageNum, pageSize), queryWrapper);
        if(page.getRecords() == null) {
            return Result.success(new ArrayList<>());
        }
        queryWrapper = new QueryWrapper<>();
        // 查询2级菜单
        queryWrapper.isNotNull("pid");
        List<Menu> list = menuService.list(queryWrapper);
        for(Menu parent: page.getRecords()) {
            setChildren(parent, list);
        }
        return Result.success(page);


    }

    // 循环递归生成子菜单
    public void setChildren(Menu parent, List<Menu> childList) {
        for(Menu child: childList) {
           if(parent.getId().equals(child.getPId())) {
               if(parent.getChildren() == null) {
                   parent.setChildren(new ArrayList<>());
               }
               parent.getChildren().add(child);
               setChildren(child, childList);
           }
        }
    }


    // 全量数据递归
    public void setAllChildren(List<Menu> parentNodes, List<Menu> list ) {
        for (Menu parentNode : parentNodes) {
            List<Menu> childList = list.stream().filter(m -> parentNode.getId().equals(m.getPId())).collect(Collectors.toList());
            parentNode.setChildren(childList);
            setAllChildren(childList, list);
        }
    }
}

