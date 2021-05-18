package com.tz.mvc.controller;



import com.tz.mapper.AdminMapper;
import com.tz.mapper.RoleMapper;
import com.tz.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author TZ
 * @since 2021-03-09
 */
@Controller
public class AssignController {

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    RoleMapper roleMapper;

    @RequestMapping("assign/to/assign/role/page.html")
    public String toAssignRolePage(@RequestParam("adminId") Integer adminId,
                                   ModelMap modelMap){
        // 1.查询已分配角色

        List<Role> assignedRoleList = roleMapper.getAssignedRole(adminId);
        // 2.查询未分配角色
        List<Role> unAssignedRoleList = roleMapper.getUnAssignedRole(adminId);

        // 3.存入模型（本质上其实是：request.setAttribute("attrName",attrValue);
        modelMap.addAttribute("assignedRoleList", assignedRoleList);
        modelMap.addAttribute("unAssignedRoleList", unAssignedRoleList);
        return "assign-role";

    }

}

