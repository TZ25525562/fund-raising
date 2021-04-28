package com.tz.service.impl;

import com.tz.pojo.Admin;
import com.tz.mapper.AdminMapper;
import com.tz.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author TZ
 * @since 2021-03-09
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
