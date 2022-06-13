package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Role;

import java.util.List;

/**
 * Date:2022/5/17
 * Author:夏宇
 * Description:
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 查询所有角色信息
     * @return
     */
    List<Role> findAll();

}
