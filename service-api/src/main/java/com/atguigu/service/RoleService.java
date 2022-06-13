package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * Date:2022/5/17
 * Author:夏宇
 * Description:
 */
public interface RoleService extends BaseService<Role> {
    //查询角色
    List<Role> findAll();
    //根据管理员id查询已分配和未分配的角色列表
    Map<String,List<Role>> findRolesByAdminId(Long adminId);

    void saveAdminRole(Long adminId,List<Long> roleIds);
}
