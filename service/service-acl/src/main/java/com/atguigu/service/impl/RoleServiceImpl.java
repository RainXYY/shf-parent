package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.AdminRole;
import com.atguigu.entity.Role;
import com.atguigu.mapper.AdminRoleMapper;
import com.atguigu.mapper.RoleMapper;
import com.atguigu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date:2022/5/17
 * Author:夏宇
 * Description:
 */
@Transactional(propagation = Propagation.SUPPORTS)
@Service(interfaceClass = RoleService.class)
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    @Override
    public List<Role> findAll() {
        return roleMapper.findAll() ;
    }

    @Override
    public Map<String, List<Role>> findRolesByAdminId(Long adminId) {
        //1.查询出所有角色列表
        List<Role> allRoleList = roleMapper.findAll();
        //2.根据adminId查询当前已分配的角色id集合
        List<Long> assignRoleIdList = adminRoleMapper.findRoleIdListByAdminId(adminId);
        //3.判断哪些角色已经分配,哪些角色未分配,分别放入两个集合中
        //3.1先创建两个集合,放入已分配和未分配的角色列表
        List<Role> assignRoleList = new ArrayList<>();
        List<Role> unAssignRoleList = new ArrayList<>();
        //3.2遍历出每个角色
        for (Role role : allRoleList) {
        //3.3判断角色是否已经分配
            if (assignRoleIdList.contains(role.getId())) {
                //已分配
                assignRoleList.add(role);
            }else {
                //未分配
                unAssignRoleList.add(role);
            }
        }
        //3.4将两个集合储存到Map中并返回
        Map<String,List<Role>> roleListMap = new HashMap<>();
        roleListMap.put("assignRoleList",assignRoleList);
        roleListMap.put("unAssignRoleList",unAssignRoleList);
        return roleListMap;
    }

    @Override
    public void saveAdminRole(Long adminId, List<Long> roleIds) {
        //1. 查询当前用户已分配的id集合
        List<Long> assignRoleIds = adminRoleMapper.findRoleIdListByAdminId(adminId);
        //2. 找出需要移除的角色id
        //使用Stream流完成
    /*List<Long> removeRoleIds = assignRoleIds.stream()
                .filter(roleId -> !roleIds.contains(roleId)).collect(Collectors.toList());*/

        List<Long> removeRoleIds = new ArrayList<>();
        //不使用Stream流
        for (Long roleId : assignRoleIds) {
            if (!roleIds.contains(roleId)) {
                //要移除
                removeRoleIds.add(roleId);
            }
        }
        //3. 调用持久层的方法移除用户和这些角色的绑定
        if (removeRoleIds != null && removeRoleIds.size() > 0) {
            adminRoleMapper.removeAdminRole(adminId,removeRoleIds);
        }
        //4. 遍历出新传过来的每一个角色id
        for (Long roleId : roleIds) {
            //判断当前角色id是否已绑定(分配)
            AdminRole adminRole = adminRoleMapper.findByAdminIdAndRoleId(adminId, roleId);
            if (adminRole == null) {
                //说明之前从未绑定
                //新增数据
                adminRole = new AdminRole();
                adminRole.setAdminId(adminId);
                adminRole.setRoleId(roleId);
                adminRoleMapper.insert(adminRole);
            }else {
                //说明之前绑定过
                if (adminRole.getIsDeleted() == 1) {
                    //但是已经移除绑定了
                    //修改is_deleted为0
                    adminRole.setIsDeleted(0);
                    adminRoleMapper.update(adminRole);
                }
            }
        }
    }

    @Override
    public BaseMapper<Role> getEntityMapper() {
        return roleMapper;
    }
}
