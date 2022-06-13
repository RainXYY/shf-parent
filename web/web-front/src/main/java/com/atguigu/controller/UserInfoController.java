package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.UserInfo;
import com.atguigu.entity.bo.LoginBo;
import com.atguigu.entity.bo.RegisterBo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.service.UserInfoService;
import com.atguigu.util.MD5;
import com.google.gson.internal.$Gson$Preconditions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Date:2022/5/25
 * Author:夏宇
 * Description:
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Reference
    private UserInfoService userInfoService;
    @Autowired
    private JedisPool jedisPool;

    @GetMapping("/sendCode/{phone}")
    public Result sendCode(@PathVariable("phone") String phone, HttpSession session) {
        //本应该调用阿里云短信(短信SDK)给phone发送短信
        //现在模拟一个短信
        String code = "520";
        //实际开发中验证码是存储到Redis，并且设置时效性(一般5分钟);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.setex("code", 60, code);
        } finally {
            if (jedis != null) {
                jedis.close();
                if (jedis.isConnected()){
                    jedis.disconnect();
                }
            }
        }
        //验证码发送成功
        return Result.ok();
    }

    //@RequestBody:获取JSON类型得参数
    //@RequestParam:获取普通类型得参数
    @PostMapping("/register")
    public Result register(@RequestBody RegisterBo registerBo, HttpSession session) {
        //将registerBo里面的数据user_info表里面,先判断
        //1.校验验证码是否正确
        Jedis jedis = null;
        try {
            //1.1获取redis中保存的验证码
            jedis = jedisPool.getResource();
            String sessionCode = jedis.get("code");
            //1.2校验
            if (!registerBo.getCode().equalsIgnoreCase(sessionCode)) {
                //验证码校验失败
                return Result.build(null, ResultCodeEnum.CODE_ERROR);
            }
        } finally {
            if (jedis != null) {
                jedis.close();
                if (jedis.isConnected()) {
                    jedis.disconnect();
                }
            }
        }
        //2.校验手机号是否已经被注册
        //2.1调用业务层方法根据手机号查询用户信息
        UserInfo userInfo = userInfoService.getByPhone(registerBo.getPhone());
        //2.2判断查询到的信息是否为null,如果不为null,则表示手机号存在,就不能注册
        if (userInfo != null) {
            //说明手机号存在,不能注册
            return Result.build(null, ResultCodeEnum.PHONE_REGISTER_ERROR);
        }
        //3.对密码进行加密
        String encryptPassword = MD5.encrypt(registerBo.getPassword());
        //4.调用业务层的方法将数据储存到数据库中
        userInfo = new UserInfo();
        // userInfo.setPhone(registerBo.getPhone());
        //拷贝属性
        BeanUtils.copyProperties(registerBo, userInfo);
        //重新设置密码
        userInfo.setPassword(encryptPassword);
        //设置status为1(0代表锁定,1代表正常)
        userInfo.setStatus(1);
        userInfoService.insert(userInfo);
        return Result.ok();
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginBo loginBo,HttpSession session) {
        //根据手机号查找用户，判断手机号是否正确
        UserInfo userInfo = userInfoService.getByPhone(loginBo.getPhone());
        if (userInfo == null) {
            //说明你的手机号错了
            return Result.build(null, ResultCodeEnum.ACCOUNT_ERROR);
        }
        //判断账号是否被锁定了
        if (userInfo.getStatus() == 0) {
            //说明账号被锁定了
            return Result.build(null, ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }
        //判断密码是否正确
        if (!userInfo.getPassword().equals(MD5.encrypt(loginBo.getPassword()))) {
            //密码错误
            return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
        }
        //如果能走到这，说明登录成功，则将用户的信息存储到session中
        session.setAttribute("USER",userInfo);
        //将当前登录的用户信息响应给前端，让前端页面回显
        Map responseMapping = new HashMap();
        responseMapping.put("nickName",userInfo.getNickName());
        responseMapping.put("phone",userInfo.getPhone());
        return Result.ok(responseMapping);
    }

    @GetMapping("/logout")
    public Result logout(HttpSession session){
        //从session中移除当前用户信息，或者直接销毁session
        session.invalidate();
        return Result.ok();
    }
}
