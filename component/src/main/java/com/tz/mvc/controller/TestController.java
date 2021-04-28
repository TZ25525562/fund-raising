package com.tz.mvc.controller;

import com.tz.mapper.AdminMapper;
import com.tz.pojo.Admin;
import com.tz.util.FundUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @Autowired
    AdminMapper adminMapper;

    @RequestMapping("/test1.html")
    public String test1(Model model){
        Admin admin = adminMapper.selectById(1);
        model.addAttribute("result",admin);
        return "target";
    }

    @RequestMapping("/test2.html")
    public void test2(){
        System.out.println(2/0);
    }

    @Test
    public void testMD5(){
        String password = FundUtil.md5("qwaszx123jkl");
        System.out.println(password);
    }


}
