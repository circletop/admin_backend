package com.example.demo.service.impl;

import com.example.demo.entity.Menu;
import com.example.demo.mapper.MenuMapper;
import com.example.demo.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author circletop
 * @since 2023-10-27
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<Menu> findAllMenus() {
        List<Menu> list = list();
        // 找出数据中的一级菜单
        List<Menu> parentNodes = list.stream().filter(menu -> menu.getPId() == null).collect(Collectors.toList());
        setAllChildren(parentNodes, list);
        return parentNodes;
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
