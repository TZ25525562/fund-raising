package com.tz.mvc.config;

import com.google.gson.Gson;
import com.tz.exception.AccessForbiddenException;
import com.tz.exception.LoginAcctDuplicateException;
import com.tz.exception.LoginFailException;
import com.tz.exception.UpdateAccountRepeatException;
import com.tz.util.Constant;
import com.tz.util.FundUtil;
import com.tz.util.ResultJson;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 异常处理类
 */
@ControllerAdvice
public class ExceptionResolver {

    /**
     * 通用的异常处理器模板
     * @param viewName 不是Ajax请求的返回视图名称
     * @param request  请求对象
     * @param response 响应对象
     * @param e 具体异常
     * @return
     * @throws Exception
     */
    private ModelAndView commonResolver(String viewName,HttpServletRequest request,HttpServletResponse response,Exception e)
            throws Exception {

        //1.拿到请求类型
        boolean requestType = FundUtil.judgeRequestType(request);
        
        //2.如果是Ajax请求
        if(requestType){

            //获取异常信息，并传入自定义返回类型中，返回给Json数据
            ResultJson<Object> resultJson = ResultJson.failWithData(e.getMessage());

            //创建Gson对象
            Gson gson = new Gson();

            //将Java对象转换成Json对象
            String json = gson.toJson(resultJson);

            //将Json数据传送给浏览器,不需要ModelAndView
            response.getWriter().write(json);
            return null;
        }
        //3.不是Ajax请求
        else{

            //创建ModelAndView对象
            ModelAndView modelAndView = new ModelAndView();

            //将异常对象传入ModelAndView
            modelAndView.addObject(Constant.EXCEPTION_NAME,e);

            //设置返回的页面视图，并返回对象
            modelAndView.setViewName(viewName);

            return modelAndView;
        }
    }

    /**
     * 处理空指针异常的方法
     * @param exception
     * @param request
     * @param response
     * @return
     */
    //将空指针异常和处理方法映射一起
    @ExceptionHandler(NullPointerException.class)
    public ModelAndView nullPointerExceptionResolver(NullPointerException exception,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception {
        //调用通用处理方法
        return commonResolver("system-error",request,response,exception);
    }
    /**
     * 处理登陆失败异常的方法
     * @param exception
     * @param request
     * @param response
     * @return
     */
    //将登陆失败异常和处理方法映射一起
    @ExceptionHandler(LoginFailException.class)
    public ModelAndView LoginFailExceptionResolver(LoginFailException exception,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception {
        String viewName = "admin-login";

        //返回登录界面
        return commonResolver(viewName,request,response,exception);
    }

    /**
     * 处理禁止登陆异常的方法
     * @param exception
     * @param request
     * @param response
     * @return
     */
    //将登陆失败异常和处理方法映射一起
    @ExceptionHandler(AccessForbiddenException.class)
    public ModelAndView AccessForbiddenExceptionResolver(LoginFailException exception,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception {
        String viewName = "admin-login";

        //返回登录界面
        return commonResolver(viewName,request,response,exception);
    }


    /**
     * 处理新增和更新用户数据账号重复异常的方法
     * @param exception
     * @param request
     * @param response
     * @return
     */
    //将登陆失败异常和处理方法映射一起
    @ExceptionHandler(LoginAcctDuplicateException.class)
    public ModelAndView LoginAcctDuplicateExceptionResolver(LoginAcctDuplicateException exception,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception {
        String viewName = "admin-add";

        //返回新增界面
        return commonResolver(viewName,request,response,exception);
    }


    /**
     * 处理更新用户数据账号重复异常的处理方法
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    //将登陆失败异常和处理方法映射一起
    @ExceptionHandler(UpdateAccountRepeatException.class)
    public ModelAndView LoginAcctDuplicateExceptionResolver(UpdateAccountRepeatException exception,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception {
        String viewName = "system-error";

        //返回系统错误界面
        return commonResolver(viewName,request,response,exception);
    }
}
