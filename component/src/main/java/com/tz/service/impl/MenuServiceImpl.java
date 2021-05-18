package com.tz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tz.mapper.MenuMapper;
import com.tz.pojo.Menu;
import com.tz.service.MenuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author TZ
 * @since 2021-04-29
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}
