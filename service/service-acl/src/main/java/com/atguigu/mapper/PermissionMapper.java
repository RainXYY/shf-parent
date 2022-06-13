package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Permission;

import java.util.List;

/**
 * Date:2022/5/27
 * Author:夏宇
 * Description:
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 查询所有权限列表
     * @return
     */
    List<Permission> findAll();
    /**
     * 查询用户的菜单权限列表
     * @param adminId
     * @return
     */
    List<Permission> findPermissionListByAdminId(Long adminId);

    /**
     * 根据父节点id查询子节点数量
     * @param id
     * @return
     */
    Long findCountByParentId(Long id);

    /**
     * 根据用户Id查询权限
     * @param adminId
     * @return
     */
    List<String> findCodePermissionListByAdminId(Long adminId);

    /**
     * 查询所有操作权限
     * @return
     */
    List<String> findAllCodePermission();
}
