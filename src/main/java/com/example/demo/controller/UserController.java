package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> index() {
        return userMapper.findAll();
    }

    @PostMapping
    public int save(@RequestBody User user) {

        return userService.save(user);

    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable Integer id) {
        return userMapper.deleteById(id);
    }

    @GetMapping("/page")
    public Map<String, Object> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        int total = userMapper.findTotal();
        List<User> data = userMapper.findPage(pageNum, pageSize);
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);
        return res;
    }
}

