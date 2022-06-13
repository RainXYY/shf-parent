package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Permission;

import java.util.List;
import java.util.Map;

/**
 * Date:2022/5/27
 * Author:夏宇
 * Description:
 */
public interface PermissionService extends BaseService<Permission> {
    /**
     * 根据角色id查询已分配和未分配的权限
     * @param roleId
     * @return
     */
    List<Map<String,Object>> findPermissionByRoleId(Long roleId);

    /**
     * 保存权限角色信息
     * @param roleId
     * @param permissionIdList
     */
    void saveRolePermission(Long roleId,List<Long> permissionIdList);

    /**
     * 查询用户的所有权限
     * @param adminId
     * @return
     */
    List<Permission> findMenuPermissionByAdminId(Long adminId);

    /**
     * 查询所有的菜单
     * @return
     */
    List<Permission> findAllMenu();

    /**
     * 根据用户ID查询用户的操作权限
     * @param adminId
     * @return
     */
    List<String> findCodePermissionListByAdminId(Long adminId);
}
