package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.AdminRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Date:2022/5/27
 * Author:夏宇
 * Description:
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {
    //根据管理员Id查询当前已经分配的角色Id的集合
    List<Long> findRoleIdListByAdminId(Long adminID);
    //移除用户需要移除的角色
    void removeAdminRole(@Param("adminId") Long adminId, @Param("removeRoleIds") List<Long> removeRoleIds);
    //根据adminId和roleId查询用户分配的角色
    AdminRole findByAdminIdAndRoleId(@Param("adminId") Long adminId,@Param("roleId") Long roleId);
}
