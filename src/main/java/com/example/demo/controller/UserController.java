package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Api(tags="用户中心")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "全量用户列表",notes = "这里可以写一些详细信息")
    @GetMapping
    public List<User> index() {
        return userMapper.findAll();
    }

    @ApiOperation(value = "新增或者修改用户信息",notes = "这里可以写一些详细信息")
    @PostMapping
    public boolean save(@RequestBody User user) {

//        return userService.save(user);
        return userService.saveUser(user);
    }

    @ApiOperation(value = "删除指定用户",notes = "这里可以写一些详细信息")
    @DeleteMapping("/{id}")
    public int delete(@PathVariable Integer id) {
        return userMapper.deleteById(id);
    }

    @ApiOperation(value = "分页查询用户信息",notes = "这里可以写一些详细信息")
    @GetMapping("/page")
    public Map<String, Object> findPage(@ApiParam(value = "页码", required = true) @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        int total = userMapper.findTotal();
        List<User> data = userMapper.findPage(pageNum, pageSize);
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);
        return res;
    }
}

