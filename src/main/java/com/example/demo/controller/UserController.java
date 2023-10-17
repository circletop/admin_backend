package com.example.demo.controller;



import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Constants;
import com.example.demo.common.Result;
import com.example.demo.controller.dto.UserDTO;
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

    @ApiOperation(value = "登陆",notes = "这里可以写一些详细信息")
    @PostMapping("/login")
    public Result  login(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        UserDTO dto = userService.login(userDTO);
        return Result.success(dto);
    }

    @ApiOperation(value = "注册",notes = "这里可以写一些详细信息")
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "必填参数错误缺失");
        }
        User dto = userService.register(userDTO);
        return Result.success(dto);
    }

    @ApiOperation(value = "根据username查询用户信息",notes = "这里可以写一些详细信息")
    @GetMapping("/username/{username}")
    public Result findByUsername(@PathVariable String username) {
        return Result.success(userService.findByUsername(username));
    }

    @ApiOperation(value = "新增或者修改信息",notes = "这里可以写一些详细信息")
    @PostMapping
    public Result save(@RequestBody User user) {
        try{
            userService.saveOrUpdate(user);
            return Result.success();
        } catch(SecurityException e) {
            return Result.error(Constants.CODE_500, "系统错误");
        }
    }

    @ApiOperation(value = "根据id删除数据",notes = "这里可以写一些详细信息")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        try{
            userService.removeById(id);
            return Result.success();
        } catch ( SecurityException e) {
            return Result.error(Constants.CODE_500, "系统错误");
        }
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
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                               @ApiParam(value = "用户名", required = false) @RequestParam(required = false, defaultValue = "") String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 参数非空
        if(username != null && !username.isEmpty()) {
            queryWrapper.like("username", username);
        }
        queryWrapper.orderByDesc("id");
        return Result.success(userService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}

