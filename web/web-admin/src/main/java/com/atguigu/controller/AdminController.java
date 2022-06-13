package com.atguigu.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Role;
import com.atguigu.service.AdminService;
import com.atguigu.service.RoleService;
import com.atguigu.util.FileUtil;
import com.atguigu.util.QiniuUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Date:2022/5/19
 * Author:夏宇
 * Description:
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    private static final String PAGE_INDEX = "admin/index";
    private static final String PAGE_EDIT = "admin/edit";
    private static final String LIST_ACTION = "redirect:/admin";
    private final static String PAGE_UPLOAD_SHOW = "admin/upload";
    private static final String PAGE_ASSIGN_SHOW ="admin/assignShow";

    @Reference
    private AdminService adminService;
    @Reference
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping
    public String index(@RequestParam Map filters, Model model) {
        //先判断是否有pageNum pageSize ,没有就给其赋默认值
        if (!filters.containsKey("pageSize")) {
            filters.put("pageSize", 10);
        }
        if (!filters.containsKey("pageNum")) {
            filters.put("pageNum", 1);
        }
        //调用业务层的方法查询分页数据
        PageInfo pageInfo = adminService.findPage(filters);
        //将分页数据储存在请求域
        model.addAttribute("page",pageInfo);
        //将搜索条件储存在请求域
        model.addAttribute("filters",filters);
        return PAGE_INDEX;
    }
    //添加(用于添加了Spring Security框架,现在添加用户需要对密码进行加密处理)
    @PostMapping("/save")
    public String save(Admin admin,Model model){
        //对密码进行加密
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        //调用业务层的方法保存用户信息
        adminService.insert(admin);
        return successPage(model,"保存用户成功");
    }
    //根据id获取用户信息
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        //调用业务层的方法根据id查询用户信息
        Admin admin = adminService.getById(id);
        //将查询到的用户信息存储到请求域中
        model.addAttribute("admin",admin);
        return PAGE_EDIT;
    }
    //修改用户信息
    @PostMapping("/update")
    public String update(Model model,Admin admin){
        //调用业务层方法
        adminService.update(admin);
        //显示成功页面,提示修改用户成功
        return successPage(model,"修改用户信息成功");
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id")Long id){
        //调用业务层的方法根据id删除
        adminService.delete(id);
        //重定向查询分页查询
        return LIST_ACTION;
    }

    @GetMapping("/uploadShow/{id}")
    public String uploadShow(@PathVariable("id") Long id,Model model){
        //将用户id带到上传页面
        model.addAttribute("id",id);
        return PAGE_UPLOAD_SHOW;
    }

    @PostMapping("/upload/{id}")
    public String upload(@PathVariable("id") Long id,
                         @RequestParam("file") MultipartFile multipartFile,
                         Model model) throws IOException, IOException {
        //id是用户的id
        //1. 将图片上传到七牛云
        //获取当前图片名
        String originalFilename = multipartFile.getOriginalFilename();
        //并生成一个唯一的文件名
        String uuidName = FileUtil.getUUIDName(originalFilename);
        QiniuUtils.upload2Qiniu(multipartFile.getBytes(),uuidName);

        //2. 将图片信息保存到数据库
        //2.1 获取图片的url
        String headUrl = QiniuUtils.getUrl(uuidName);
        //2.2 封装信息到Admin
        Admin admin = new Admin();
        admin.setId(id);
        admin.setHeadUrl(headUrl);
        //2.3 更新admin
        adminService.update(admin);

        //3. 显示成功页面
        return successPage(model,"上传头像成功");
    }

    @GetMapping("/assignShow/{id}")
    public String assignShow(@PathVariable("id") Long id,Model model){
        //1.调用业务层方法获取用户已分配和未分配的角色列表
        Map<String, List<Role>> roleListMap = roleService.findRolesByAdminId(id);
        //2.将查询到的数据存储到请求域,请求域中的key分别是assignRoleList和unAssignRoleList
        model.addAllAttributes(roleListMap);
        //3. 将id存储到请求域: 为了在分配角色页面中拿到adminId
        model.addAttribute("adminId",id);
        return PAGE_ASSIGN_SHOW;
    }

    @PostMapping("/assignRole")
    public String assignRole(@RequestParam("adminId") Long adminId,@RequestParam("roleIds") List<Long> roleIds,
                             Model model){
        //调用业务层的方法保存给用户分配角色
        roleService.saveAdminRole(adminId,roleIds);
        return successPage(model,"给用户分配角色成功");
    }
}
