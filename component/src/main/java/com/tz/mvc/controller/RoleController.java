package com.tz.mvc.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tz.mapper.RoleMapper;
import com.tz.pojo.Role;
import com.tz.util.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author TZ
 * @since 2021-04-20
 */
@Controller
public class RoleController {

    @Autowired
    RoleMapper roleMapper;

    /**
     * 保存角色数据
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping("/role/save.json")
    public ResultJson<String> saveRole(Role role){

        // 插入角色数据
        roleMapper.insert(role);
        return ResultJson.successWithoutData();
    }

    /**
     * 更新角色信息
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping("/role/update.json")
    public ResultJson<String> updateRole(Role role){

        // 根据Id更新角色数据
        roleMapper.updateById(role);
        return ResultJson.successWithoutData();
    }

    /**
     * 获取角色信息并封装
     * @param pageNum 当前页面数
     * @param pageSize 每页数量
     * @param keyword  关键词
     * @return
     */
    @ResponseBody
    @RequestMapping("/role/get/page/info.json")
    public ResultJson<PageInfo<Role>> getPageInfo(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                  @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                                  @RequestParam(value = "keyword",defaultValue = "" )String keyword){
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.like("name",keyword);
        // 1. 获取数据
        List<Role> roleList = roleMapper.selectList(wrapper);
        // 2. 开启分页
        PageHelper.startPage(pageNum,pageSize);
        try {
            // 3. 封装并且返回Json数据
            PageInfo<Role> pageInfo = new PageInfo<>(roleList);
            return ResultJson.successWithData(pageInfo);
        } catch (Exception e) {
            // 4. 否则返回异常错误信息
            return ResultJson.failWithData(e.getMessage());
        }
    }


    /**
     * 根据传入的Id数组删除角色数据
     * @param roleIdArray
     * @return
     */
    @ResponseBody
    @RequestMapping("/role/remove.json")
    public ResultJson<String> removeRoleByIdList(@RequestBody List<Integer> roleIdArray){

        // 根据Id数组删除角色数据
        roleMapper.deleteBatchIds(roleIdArray);
        return ResultJson.successWithoutData();
    }


}

