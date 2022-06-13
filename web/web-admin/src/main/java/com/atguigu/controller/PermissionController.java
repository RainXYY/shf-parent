package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Permission;
import com.atguigu.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Date:2022/5/28
 * Author:夏宇
 * Description:
 */
@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController{
    private final static String PAGE_INDEX = "permission/index";
    private static final String PAGE_CREATE = "permission/create";
    private static final String LIST_ACTION = "redirect:/permission";
    private static final String PAGE_ERROR = "common/errorPage";
    private static final String PAGE_EDIT = "permission/edit";
    @Reference
    private PermissionService permissionService;
    @GetMapping
    public String index(Model model){
        //调用业务层方法获取权限列表
        List<Permission> permissionList = permissionService.findAllMenu();
        //将权限列表储存到请求域
        model.addAttribute("list",permissionList);
        return PAGE_INDEX;
    }

    @GetMapping("/create")
    public String create(Permission permission,Model model){
        model.addAttribute("permission",permission);
        return PAGE_CREATE;
    }

    @PostMapping("/save")
    public String save(Permission permission,Model model){
        permissionService.insert(permission);
        return successPage(model,"新增菜单成功");
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id,Model model){
        try {
            //调用业务层的方法删除
            permissionService.delete(id);
            return LIST_ACTION;
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage",e.getMessage());
            return PAGE_ERROR;
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,Model model){
        Permission permission = permissionService.getById(id);
        model.addAttribute("permission",permission);
        return PAGE_EDIT;
    }

    @PostMapping("/update")
    public String update(Permission permission,Model model){
        permissionService.update(permission);
        return successPage(model,"修改菜单成功");
    }
}
