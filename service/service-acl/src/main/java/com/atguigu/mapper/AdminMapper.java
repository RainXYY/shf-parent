package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Admin;

import java.util.List;

/**
 * Date:2022/5/18
 * Author:夏宇
 * Description:
 */
public interface AdminMapper extends BaseMapper<Admin> {
    //获取所有用户信息
    List<Admin> findAll();

    //根据username获取用户信息
    Admin getByUsername(String username);
}