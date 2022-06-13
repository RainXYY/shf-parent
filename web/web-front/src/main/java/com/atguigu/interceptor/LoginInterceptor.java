package com.atguigu.interceptor;

import com.alibaba.fastjson.JSON;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Date:2022/5/26
 * Author:夏宇
 * Description:
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      //判断当前是否已经登录
        if (request.getSession().getAttribute("USER") == null) {
            //说明未登录
            //需要响应JSON数据告诉前端页面:"未登录",让前端能跳转到登录页面
            //向前端响应字符串,1.用JSON将Result对象转成字符串 2.用response响应
            response.getWriter().write(JSON.toJSONString(Result.build(null, ResultCodeEnum.LOGIN_AUTH)));
            return false;
        }
        return true;
    }
}
