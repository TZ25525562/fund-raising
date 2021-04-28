package com.tz.util;

import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 整个项目的工具类
 */
public class FundUtil {

    /**
     * 判断当前请求是否为Ajax请求，是否需要返回Json数据
     * @param httpServletRequest 请求对象
     * @return
     *          true:是Ajax请求
     *          false:不是Ajax请求
     */
    public static boolean judgeRequestType(HttpServletRequest httpServletRequest){

        //1.获取消息头信息
        String acceptInformation = httpServletRequest.getHeader("Accept");
        String requestInformation = httpServletRequest.getHeader("X-Requested-With");

        return (acceptInformation != null && acceptInformation.length()>0 && acceptInformation.contains("application/json"))
                ||
                (requestInformation != null && requestInformation.length()>0 && requestInformation.equals("XMLHttpRequest"));
    };

    /**
     * 对传入的字符串判断并进行MD5加密
     * @param source 传入的字符串
     * @return 加密后的字符串
     */
    public static String md5(String source) {

        // 1.判断字符串是否有效
        if (source == null || source.length() == 0){

            // 抛出异常
            throw  new RuntimeException(Constant.MASSAGE_STRING_INVALIDATE);
        }

        // 2. 用MD5加密密码

        String md5Digest = DigestUtils.md5DigestAsHex(source.getBytes());
        return md5Digest;
    }
}
