package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Admin;

import java.util.List;

/**
 * Date:2022/5/18
 * Author:夏宇
 * Description:
 */
public interface AdminService extends BaseService<Admin> {

    List<Admin> findAll();

    Admin getByUsername(String username);
}
