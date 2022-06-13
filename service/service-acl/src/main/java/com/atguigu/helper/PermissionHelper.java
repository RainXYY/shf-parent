package com.atguigu.helper;

import com.atguigu.entity.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * 包名:com.atguigu.helper
 *
 * @author Leevi
 * 日期2022-05-27  15:52
 */
public class PermissionHelper {
    /**
     * 将一个permission的集合构建出父子级菜单
     * @param originalPermissionList
     * @return
     */
    public static List<Permission> build(List<Permission> originalPermissionList) {
        List<Permission> treeNodes = new ArrayList<>();
        for (Permission permission : originalPermissionList) {
            //每个permission还缺少啥:level、children、parentName
            //如果permission的parentId是0表示你是一级菜单
            if (permission.getParentId() == 0) {
                //设置一级菜单的level
                permission.setLevel(1);
                //设置一级菜单的子菜单
                permission.setChildren(getChildren(permission,originalPermissionList));
                treeNodes.add(permission);
            }
        }
        return treeNodes;
    }

    /**
     * 获取某个权限的子菜单
     * @param permission
     * @param permissionList
     * @return
     */
    private static List<Permission> getChildren(Permission permission, List<Permission> permissionList) {
        //创建新集合存储子菜单
        List<Permission> children = new ArrayList<>();
        //遍历出每一个权限
        for (Permission treeNode : permissionList) {
            //判断遍历出来的每一个权限是否是permission的子菜单
            if (treeNode.getParentId().equals(permission.getId())) {
                //说明treeNode是permission的子菜单
                //设置子菜单的level
                treeNode.setLevel(permission.getLevel() + 1);
                //设置子菜单的children
                treeNode.setChildren(getChildren(treeNode,permissionList));
                //设置子菜单的parentName
                treeNode.setParentName(permission.getName());

                //将treeNode加到新集合中
                children.add(treeNode);
            }
        }
        return children;
    }
}
