package com.atguigu.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Role;
import com.atguigu.service.PermissionService;
import com.atguigu.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Date:2022/5/17
 * Author:夏宇
 * Description:
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
    private static final String PAGE_CREATE = "role/create";
    @Reference
    private RoleService roleService;
    @Reference
    private PermissionService permissionService;

    private final static String PAGE_INDEX = "role/index";
    private static final String LIST_ACTION = "redirect:/role";
    private final static String PAGE_SUCCESS = "common/successPage";
    private final static String PAGE_EDIT = "role/edit";
    private static final String PAGE_ASSIGN_SHOW = "role/assignShow";

    @PreAuthorize("hasAnyAuthority('role.show')")
    @RequestMapping
    public String index(@RequestParam Map filters, Model model){
        //判断PageNum PageSize是否有值,没值就赋默认值
        if(!filters.containsKey("pageNum")) {
            filters.put("pageNum", 1);
        }
        if(!filters.containsKey("pageSize")) {
            filters.put("pageSize", 10);
        }
        //调用业务层的方法进行分页查询
        PageInfo<Role> pageInfo = roleService.findPage(filters);
        //将查询到的分页数据储存到请求域
        model.addAttribute("page", pageInfo);
        model.addAttribute("filters", filters);
        return PAGE_INDEX;
    }
    //添加角色 跳转到添加页面
    @PreAuthorize("hasAnyAuthority('role.create')")
    @GetMapping("/create")
    public String create(){
        return PAGE_CREATE;
    }
    //添加角色
    @PreAuthorize("hasAnyAuthority('role.create')")
    @PostMapping("/save")
    public String save(Role role , Model model){
        roleService.insert(role);
        model.addAttribute("messagePage","新增角色成功");
        return PAGE_SUCCESS;
    }
    //根据Id查询要修改的角色信息
    @PreAuthorize("hasAnyAuthority('role.edit')")
    @GetMapping("/edit/{id}")
    public String edit(Model model,@PathVariable Long id) {
        Role role = roleService.getById(id);
        model.addAttribute("role",role);
        return PAGE_EDIT;
    }
    //修改角色信息
    @PreAuthorize("hasAnyAuthority('role.edit')")
    @PostMapping("/update")
    public String update(Role role,Model model) {
        roleService.update(role);
        model.addAttribute("messagePage","更新角色成功");
        return PAGE_SUCCESS;
    }
    //删除角色信息
    @PreAuthorize("hasAnyAuthority('role.delete')")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        roleService.delete(id);
        return LIST_ACTION;
    }

    @PreAuthorize("hasAnyAuthority('role.assign')")
    @GetMapping("/assignShow/{roleId}")
    public String assignShow(@PathVariable("roleId") Long roleId,Model model){
        //调用业务层查询出角色已分配和未分配权限信息
        List<Map<String, Object>> permissionList = permissionService.findPermissionByRoleId(roleId);
        //将permissionList转成JSON字符串储存到请求域
        model.addAttribute("zNodes", JSON.toJSONString(permissionList));
        //将roleId储存到请求域
        model.addAttribute("roleId",roleId);
        return PAGE_ASSIGN_SHOW;
    }

    @PreAuthorize("hasAnyAuthority('role.assign')")
    @PostMapping("/assignPermission")
    public String assignPermission(@RequestParam("roleId") Long roleId,
                                   @RequestParam("permissionIds") List<Long> permissionIds,
                                   Model model){
        permissionService.saveRolePermission(roleId,permissionIds);
        return successPage(model,"设置角色权限成功");
    }
}
