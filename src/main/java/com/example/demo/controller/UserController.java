package com.example.demo.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import java.util.List;

import com.example.demo.service.IUserService;
import com.example.demo.entity.User;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author circletop
 * @since 2023-10-09
 */
@RestController
@RequestMapping("/user")
@Api(tags="用户中心")
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "新增或者修改信息",notes = "这里可以写一些详细信息")
    @PostMapping
    public boolean save(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }

    @ApiOperation(value = "根据id删除数据",notes = "这里可以写一些详细信息")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return userService.removeById(id);
    }

    @ApiOperation(value = "根据ids批量删除数据",notes = "这里可以写一些详细信息")
    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return userService.removeByIds(ids);
    }

    @ApiOperation(value = "全量数据列表",notes = "这里可以写一些详细信息")
    @GetMapping
    public List<User> findAll() {
        return userService.list();
    }

    @ApiOperation(value = "根据id查询数据",notes = "这里可以写一些详细信息")
    @GetMapping("/{id}")
    public User findOne(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @ApiOperation(value = "分页查询数据",notes = "这里可以写一些详细信息")
    @GetMapping("/page")
    public Page<User> findPage(@RequestBody Integer pageNum, @RequestBody Integer pageSize,
                               @ApiParam(value = "用户名", required = false) @RequestParam(required = false, defaultValue = "") String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 参数非空
        if(username != null && !username.isEmpty()) {
            queryWrapper.like("username", username);
        }
        queryWrapper.orderByDesc("id");
        return userService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }
}

