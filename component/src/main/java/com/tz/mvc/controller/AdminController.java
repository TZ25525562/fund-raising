package com.tz.mvc.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tz.exception.LoginAcctDuplicateException;
import com.tz.exception.LoginFailException;
import com.tz.exception.UpdateAccountRepeatException;
import com.tz.mapper.AdminMapper;
import com.tz.pojo.Admin;
import com.tz.util.Constant;
import com.tz.util.FundUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author TZ
 * @since 2021-03-09
 */
@Controller
public class AdminController {

    @Autowired
    AdminMapper adminMapper;

    /**
     * 登出功能实现
     * @param session
     * @return
     */
    @RequestMapping("/admin/to/logout.html")
    public String doLogout(HttpSession session){

        //强制session失效
        session.invalidate();
        return "redirect:/admin/to/login.html";
    }

    /**
     * 登录校验功能实现
     * @param loginAcct 前端传入的账号
     * @param userPswd  前端传入的密码
     * @param session
     * @return
     */
    @RequestMapping("/admin/do/login.html")
    public String doLogin(@RequestParam("loginAcct") String loginAcct,
                          @RequestParam("userPswd") String userPswd,
                          HttpSession session){

        // 1.用账号找出对应的admin对象List
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("login_acct",loginAcct);
        List<Admin> adminList = adminMapper.selectList(wrapper);

        // 2.如果adminList为空，则抛出登录失败异常
        if (adminList == null || adminList.isEmpty() == true){
            throw new LoginFailException(Constant.ACCOUNT_ACCESS_FAIL);
        }

        // 3. 如果adminList查询出来为多个，则抛出系统异常
        if(adminList.size() > 1){
            throw new RuntimeException(Constant.MESSAGE_LOGIN_NOT_UNIQUE);
        }

        // 4. 将传入的密码进行MD5加密
        String MD5Password = FundUtil.md5(userPswd);

        // 5. 取出数据库中密码
        Admin admin = adminList.get(0);
        String tblPassword = admin.getUserPswd();

        // 6. 对两个密码进行比较
        if(Objects.equals(MD5Password,tblPassword)){
            // 7. 比对一样，传入前端session
            session.setAttribute(Constant.LOGIN_ADMIN_NAME,admin);
        }else{
            // 8. 反之，抛出登录失败异常
            throw new LoginFailException(Constant.ACCOUNT_ACCESS_FAIL);
        }

        //重定向至主页面，防止多次查询数据库提交数据
        return "redirect:/admin/to/main.html";
    }

    /**
     * 统一的分页查询显示数据
     * @param keyword 查询的条件（user_name,login_acct,email）
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping("/admin/to/page.html")
    public String getPageInfo(@RequestParam(value = "keyword",defaultValue = "") String keyword,
                              //默认前往第一页，每页默认5条数据
                              @RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage,
                              @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                              ModelMap modelMap){

        // 1.查询符合对象的数据
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.like("login_acct", keyword).
                or().like("user_name", keyword).
                or().like("email", keyword);
        List<Admin> adminList = adminMapper.selectList(wrapper);

        // 2. 开启分页功能，封装进PageInfo对象中
        PageHelper.startPage(currentPage,pageSize);
        PageInfo pageInfo = new PageInfo(adminList);
        pageInfo.setPageSize(pageSize);
        pageInfo.setSize(pageSize);
        long pages = pageInfo.getTotal() % pageInfo.getPageSize();
        pageInfo.setPages((int)((pages > 0)?
                (pageInfo.getTotal() / pageInfo.getPageSize() + 1):
                pageInfo.getTotal() / pageInfo.getPageSize()));

        // 3. 写入modelMap中
        modelMap.addAttribute(Constant.PAGE_INFO_NAME,pageInfo);

        // 3. 前往页面
        return "admin-page";
    }

    /**
     * 删除单条数据并返回当前页面
     * @param adminId 要删除的数据id
     * @param pageNum 传入的当前页数
     * @param keyword 关键字
     * @return
     */
    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String remove(@PathVariable("adminId") Integer adminId,
                         @PathVariable("pageNum") Integer pageNum,
                         @PathVariable("keyword") String keyword){

        // 1. 删除数据
        adminMapper.deleteById(adminId);

        // 2. 重定向至原页面
        return "redirect:/admin/to/page.html?pageNum=" + pageNum + "&keyword=" + keyword;

    }


    /**
     * 保存用户数据
     * @param admin
     * @return
     */
    @RequestMapping("/admin/to/save.html")
    public String save(Admin admin){

        // 1. 生成当前时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = format.format(date);
        admin.setCreateTime(createTime);

        // 2. 对密码进行MD5加密
        String md5 = FundUtil.md5(admin.getUserPswd());
        admin.setUserPswd(md5);

        // 3. 保存数据,若账号重复,则抛出异常
        try {
            adminMapper.insert(admin);
        } catch (Exception e) {
            
            e.printStackTrace();
            if (e instanceof DuplicateKeyException){
                throw new LoginAcctDuplicateException(Constant.ACCOUNT_IS_IN_USE);
            }
        }

        // 4. 重定向至数据源的最后一页
        return "redirect:/admin/to/page.html?pageNum=" + Integer.MAX_VALUE + "&keyword=";

    }


    /**
     * 根据Id查询数据并回显到更新页面
     * @param adminId
     * @param modelMap
     * @return
     */
    @RequestMapping("/admin/to/edit/page.html")
    public String editPage(@RequestParam("adminId") String adminId,
                           ModelMap modelMap){

        // 1. 根据Id查询出数据
        Admin admin = adminMapper.selectById(adminId);

        // 2. 存入modelMap，回显至页面
        modelMap.addAttribute("admin",admin);

        return "admin-edit";

    }


    @RequestMapping("/admin/to/update.html")
    public String update(Admin admin, @RequestParam("pageNum") String pageNum,
                         @RequestParam("keyword") String keyword){

        // 1. 生成当前时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = format.format(date);
        admin.setCreateTime(createTime);

        // 2. 更新数据,若账号重复,则抛出异常
        try {
            adminMapper.updateById(admin);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof DuplicateKeyException){
                throw new UpdateAccountRepeatException(Constant.ACCOUNT_IS_IN_USE);
            }
        }

        return "redirect:/admin/to/page.html?pageNum=" + pageNum + "&keyword=" + keyword;

    }



}

