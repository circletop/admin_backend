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
    public List<Menu> findAll() {
        return menuService.list();
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
        queryWrapper.orderByDesc("id");
        return Result.success(menuService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}

