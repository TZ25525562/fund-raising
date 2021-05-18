package com.tz.mvc.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tz.mapper.MenuMapper;
import com.tz.pojo.Menu;
import com.tz.util.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author TZ
 * @since 2021-04-29
 */
@RestController
public class MenuController {

    @Autowired
    MenuMapper menuMapper;

    /**
     * 保存插入的菜单数据
     * @param menu
     * @return
     */
    @RequestMapping("/menu/save.json")
    public ResultJson<Menu> saveMenuItem(Menu menu){

//        插入数据
        menuMapper.insert(menu);
//        menuMapper.menuInsert();

        return ResultJson.successWithoutData();
    }

    /**
     * 获取菜单页面的树形结构
     * @return
     */
    @RequestMapping("/menu/get/tree.json")
    public ResultJson<Menu> getWholeTree(){

        // 1. 获取所有的menu对象集合
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.select("id","pid","name","icon","url");

        List<Menu> menuList = menuMapper.selectList(wrapper);

        // 2. 设置初始根结点
        Menu root = null;

        // 3. 设置map数组用来暂时存放id和对象对
        HashMap<Integer, Menu> menuHashMap = new HashMap<>();

        // 4. 每一对menu都放入map数组中
        for (Menu menu : menuList) {
            Integer id = menu.getId();
            menuHashMap.put(id,menu);
        }

        // 循环遍历list集合
        for (Menu menu : menuList) {
            Integer pid = menu.getPid();

            // 5. 如果当前对象没有父节点，则他为父节点
            if(pid == null){
                root = menu;
                continue;
            }

            // 6. 有父节点的话，则找到该父节点
            Menu fatherMenu = menuHashMap.get(pid);

            // 7. 将此节点添加到父节点的children集合中
            fatherMenu.getChildren().add(menu);
        }

        // 8. 返回该树形对象
        return ResultJson.successWithData(root);
    }
}

