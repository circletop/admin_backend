package com.example.demo.service;

import com.example.demo.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author circletop
 * @since 2023-10-27
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findAllMenus();
}
