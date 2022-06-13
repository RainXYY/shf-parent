package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Permission;
import com.atguigu.entity.RolePermission;
import com.atguigu.helper.PermissionHelper;
import com.atguigu.mapper.PermissionMapper;
import com.atguigu.mapper.RolePermissionMapper;
import com.atguigu.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Date:2022/5/27
 * Author:夏宇
 * Description:
 */
@Service(interfaceClass = PermissionService.class)
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Override
    public BaseMapper<Permission> getEntityMapper() {
        return permissionMapper;
    }

    @Override
    public List<Map<String, Object>> findPermissionByRoleId(Long roleId) {
        //查询所有权限信息
        List<Permission> allPermissionList = permissionMapper.findAll();
        //查询出当前角色已分配的权限id
        List<Long> assignPermissionIdList = rolePermissionMapper.findPermissionIdListByRoleId(roleId);
        //创建一个List用于存储返回数据
        List<Map<String,Object>> permissionList = new ArrayList<>();
        //遍历出所有权限信息
        for (Permission permission : allPermissionList) {
            //创建一个Map用于存储返回数据
            Map<String,Object> map = new HashMap<>();
            //判断当前权限是否已分配
            if (assignPermissionIdList.contains(permission.getId())){
                //已分配
                map.put("checked",true);
            }else {
                //未分配
                map.put("checked",false);
            }
            //设置id
            map.put("id",permission.getId());
            //设置pId
            map.put("pId",permission.getParentId());
            //设置name
            map.put("name",permission.getName());
            //设置open
            map.put("open",true);
            //将map添加到List中
            permissionList.add(map);
        }
        return permissionList;
    }

    @Override
    public void saveRolePermission(Long roleId, List<Long> permissionIdList) {
        //查询当前角色的所有permissionId集合
        List<Long> rolePermissionIdList = rolePermissionMapper.findPermissionIdListByRoleId(roleId);
        //找出要移除的permissionId的集合
        List<Long> removePermissionIdList = rolePermissionIdList.stream()
                .filter(item -> !permissionIdList.contains(item))
                .collect(Collectors.toList());
        //删除角色与权限的绑定
        if (removePermissionIdList != null && removePermissionIdList.size() > 0) {
            rolePermissionMapper.removeRolePermission(roleId,removePermissionIdList);
        }

        //给角色添加权限
        //遍历出传过来的每一个permissionId
        for (Long permissionId : permissionIdList) {
            //根据roleId和permissionId查询角色权限信息
            RolePermission rolePermission = rolePermissionMapper.findByRoleIdAndPermissionId(roleId,permissionId);
            //判断当前roleId和permissionId是否存在关联
            if (rolePermission == null) {
                //之前从来未绑定过,则新增
                rolePermission = new RolePermission();
                rolePermission.setPermissionId(permissionId);
                rolePermission.setRoleId(roleId);
                rolePermissionMapper.insert(rolePermission);
            }else {
                if (rolePermission.getIsDeleted() == 1){
                    //之前绑定过,但是已经取消,则将is_deleted改为0
                    rolePermission.setIsDeleted(0);
                    rolePermissionMapper.update(rolePermission);
                }
            }
        }
    }

    @Override
    public List<Permission> findMenuPermissionByAdminId(Long adminId) {
        //判断当前是否是超级管理员
        List<Permission> permissionList = null;
        if (adminId == 1) {
            //超级管理员,查询所有权限
            permissionList = permissionMapper.findAll();
        }else {
            //不是超级管理员,根据adminId查询权限列表
            permissionList = permissionMapper.findPermissionListByAdminId(adminId);
        }
        //现在的permissionList中的每一个permission有啥: acl_permission表中的各个字段的数据
        //将权限列表构建成父子级结构:父子级菜单中需要啥呢?
        //创建一个新集合存储构建后的数据
        List<Permission> treeNodes = PermissionHelper.build(permissionList);
        return treeNodes;
    }

    @Override
    public List<Permission> findAllMenu() {
        //查询所有权限
        List<Permission> permissionList = permissionMapper.findAll();
        //调用工具类构建父子级菜单
        return PermissionHelper.build(permissionList);
    }

    @Override
    public List<String> findCodePermissionListByAdminId(Long adminId) {
        //判断是否是超级管理员
        if(adminId == 1){
            //是超级管理员,拥有所有的权限
            return permissionMapper.findAllCodePermission();
        }
        //是普通用户
        return permissionMapper.findCodePermissionListByAdminId(adminId);
    }

    @Override
    public void delete(Long id) {
        //判断当前菜单是否有子菜单,如果有就不能删除
       Long ChildCount = permissionMapper.findCountByParentId(id);
        if(ChildCount > 0){
            //说明有子菜单 就不能删除
            throw new RuntimeException("当前菜单有子菜单,不能删除");
        }else {
            //没有子菜单,可以删除
            super.delete(id);
        }
    }
}
