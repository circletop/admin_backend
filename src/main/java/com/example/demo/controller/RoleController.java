package com.example.demo.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Menu;
import com.example.demo.service.IMenuService;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Resource
    private IMenuService menuService;

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
    public Result findAll() {
        return Result.success(roleService.list());
    }

    @ApiOperation(value = "根据id查询数据",notes = "这里可以写一些详细信息")
    @GetMapping("/{id}")
    public Role findOne(@PathVariable Integer id) {
        return roleService.getById(id);
    }

    @ApiOperation(value = "根据角色key查询数据",notes = "这里可以写一些详细信息")
    @GetMapping("/{roleKey}")
    public Result findOneByRoleKey(@PathVariable String roleKey) {
        return Result.success(roleService.getByRoleKey(roleKey));
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

    /**
     * 根据角色id 分配菜单
     * @param roleId 角色id
     * @param menuIds 菜单id
     * @return
     */
    @ApiOperation(value = "根据id更新当前角色的菜单权限 ",notes = "这里可以写一些详细信息")
    @PostMapping("/roleMenu/{roleId}")
    public Result roleMenu(@PathVariable("roleId") Integer roleId, @RequestBody List<Integer> menuIds) {
        roleService.setRoleMenu(roleId, menuIds);
        return Result.success();
    }

    /**
     * 根据角色id菜单
     * @param roleId 角色id
     * @return
     */
    @ApiOperation(value = "根据id更新当前角色的菜单权限 ",notes = "这里可以写一些详细信息")
    @GetMapping("/roleMenu/{roleId}")
    public Result getRoleMenu(@PathVariable("roleId") Integer roleId) {
        return Result.success(roleService.getRoleMenu(roleId));
    }

    /**
     * 根据用户角色key获取权限菜单
     * @param roleKey 角色id
     * @return
     */
    @ApiOperation(value = "根据用户角色key获取权限菜单 ",notes = "这里可以写一些详细信息")
    @GetMapping("/roleMenu")
    public Result getAuthMenu(@RequestParam String roleKey) {
        // 查询当前角色
        Role currentRole = roleService.getByRoleKey(roleKey);
        Integer id = currentRole.getId();
        // 查询当前角色菜单
        List<Integer> menuIds = roleService.getRoleMenu(id);
        // 根据角色菜单id 过滤菜单
        // 查询菜单数据
        List<Menu> allMenus = menuService.findAllMenus();

        List<Menu> menus = filterMenu(menuIds, allMenus);
        return Result.success(menus);
    }

    /**
     * 通过所有菜单 和授权菜单id获取 授权菜单
     * @param menuIds
     * @param allMenus
     * @return
     */
    public List<Menu> filterMenu(List<Integer> menuIds, List<Menu> allMenus) {
        List<Menu> roleMenus = new ArrayList<>();
        for (Menu menu : allMenus) {
            if(menuIds.contains(menu.getId())) {
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            // 移除不在menuids里面的菜单数据
            children.removeIf(child -> !menuIds.contains(child.getId()));
            // 如果半选集合则需要判断是否有子集
            if(!children.isEmpty() && roleMenus.stream().noneMatch(p-> Objects.equals(p.getId(), menu.getId()))) {
                roleMenus.add(menu);
            }
        }

        return roleMenus;
    }

}

