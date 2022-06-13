package com.atguigu.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Date:2022/5/30
 * Author:夏宇
 * Description:
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Reference
    private AdminService adminService;
    @Reference
    private PermissionService permissionService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //调用业务层方法 根据用户名查询用户
        Admin admin = adminService.getByUsername(username);
        if (admin == null){
            throw new UsernameNotFoundException("用户名不存在");
        }

        //获取当前用户权限列表
        List<String> permissionCodeList = permissionService.findCodePermissionListByAdminId(admin.getId());
        //创建一个集合储存授权数据
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        if(permissionCodeList != null && permissionCodeList.size() > 0){
            for (String permissionCode : permissionCodeList) {
                //每一个code对应一个SimpleGrantedAuthority
                if (permissionCode != null){
                    //将对象加入集合
                    grantedAuthorityList.add(new SimpleGrantedAuthority(permissionCode));
                }
            }
        }
        return new User(username,admin.getPassword(),
                grantedAuthorityList);
    }
}
