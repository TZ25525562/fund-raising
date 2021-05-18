package com.tz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tz.pojo.Role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author TZ
 * @since 2021-04-20
 */
public interface RoleMapper extends BaseMapper<Role> {
//        自定义的获取授权的角色列表方法
        List<Role> getAssignedRole(Integer adminId);
        //自定义的获取没有授权的角色列表方法
        List<Role> getUnAssignedRole(Integer adminId);
}
