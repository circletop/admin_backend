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


import com.example.demo.service.ICourseService;
import com.example.demo.entity.Course;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author circletop
 * @since 2023-11-14
 */

@RestController
@RequestMapping("/course")
@Api(tags="课程管理")
public class CourseController {

    @Resource
    private ICourseService courseService;

    @ApiOperation(value = "新增或者修改信息",notes = "这里可以写一些详细信息")
    @PostMapping
    public Result save(@RequestBody Course course) {
        try{
        courseService.saveOrUpdate(course);
        return Result.success();
        } catch(SecurityException e) {
        return Result.error(Constants.CODE_500, "系统错误");
        }
    }

    @ApiOperation(value = "根据id删除数据",notes = "这里可以写一些详细信息")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        try{
            courseService.removeById(id);
            return Result.success();
        } catch ( SecurityException e) {
            return Result.error(Constants.CODE_500, "系统错误");
        }
    }

    @ApiOperation(value = "根据ids批量删除数据",notes = "这里可以写一些详细信息")
    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return courseService.removeByIds(ids);
    }

    @ApiOperation(value = "全量数据列表",notes = "这里可以写一些详细信息")
    @GetMapping
    public List<Course> findAll() {
        return courseService.list();
    }

    @ApiOperation(value = "根据id查询数据",notes = "这里可以写一些详细信息")
    @GetMapping("/{id}")
    public Course findOne(@PathVariable Integer id) {
        return courseService.getById(id);
    }

    @ApiOperation(value = "分页查询数据",notes = "这里可以写一些详细信息")
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(courseService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}

