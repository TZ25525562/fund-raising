package com.tz.mvc.interceptor;

import com.tz.exception.AccessForbiddenException;
import com.tz.pojo.Admin;
import com.tz.util.Constant;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登陆的拦截器
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    /**
     * 拦截登录方法
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //1. 获取session对象和URL
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();


        // 2. 获取admin对象
        Admin admin = (Admin) session.getAttribute(Constant.LOGIN_ADMIN_NAME);

//        判断URL是不是第一次登录，如果是，放行
        if(uri.equals("/web_war_exploded/admin/do/login.html")){
            return true;
        }

        // 3. 判断admin是否存在
        if(admin == null){
            // 不存在则抛出异常
            throw new AccessForbiddenException(Constant.FORBIDDEN_WITHOUT_ACCOUNT);
        }
        else{
            // 存在则放行
            return true;
        }
    }
}
