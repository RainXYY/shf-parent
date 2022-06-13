package com.atguigu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.entity.UserInfo;

/**
 * Date:2022/5/25
 * Author:夏宇
 * Description:
 */

public interface UserInfoService {
    //根据手机号查询用户信息
    UserInfo getByPhone(String phone);
    //保存用户注册信息
    void insert(UserInfo userInfo);
}
