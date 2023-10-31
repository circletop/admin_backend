package com.example.demo.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import java.util.List;
import com.example.demo.common.Result;
import com.example.demo.common.Constants;


import com.example.demo.service.IRoleService;
import com.example.demo.entity.Role;

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
@RequestMapping("/role")
@Api(tags="角色中心")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @ApiOperation(value = "新增或者修改信息",notes = "这里可以写一些详细信息")
    @PostMapping
    public Result save(@RequestBody Role role) {
        try{
        roleService.saveOrUpdate(role);
        return Result.success();
        } catch(SecurityException e) {
        return Result.error(Constants.CODE_500, "系统错误");
        }
    }

    @ApiOperation(value = "根据id删除数据",notes = "这里可以写一些详细信息")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        try{
            roleService.removeById(id);
            return Result.success();
        } catch ( SecurityException e) {
            return Result.error(Constants.CODE_500, "系统错误");
        }
    }

    @ApiOperation(value = "根据ids批量删除数据",notes = "这里可以写一些详细信息")
    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return roleService.removeByIds(ids);
    }

    @ApiOperation(value = "全量数据列表",notes = "这里可以写一些详细信息")
    @GetMapping
    public List<Role> findAll() {
        return roleService.list();
    }

    @ApiOperation(value = "根据id查询数据",notes = "这里可以写一些详细信息")
    @GetMapping("/{id}")
    public Role findOne(@PathVariable Integer id) {
        return roleService.getById(id);
    }

    @ApiOperation(value = "分页查询数据",notes = "这里可以写一些详细信息")
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam(required = false, defaultValue = "") String name) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();

        // 参数非空
        if(name != null && !name.isEmpty()) {
            queryWrapper.like("name", name);
        }
        queryWrapper.orderByDesc("id");
        return Result.success(roleService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}

