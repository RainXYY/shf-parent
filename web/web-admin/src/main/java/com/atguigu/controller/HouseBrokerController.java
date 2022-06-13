package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;
import com.atguigu.service.AdminService;
import com.atguigu.service.HouseBrokerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Date:2022/5/23
 * Author:夏宇
 * Description:
 */
@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController extends BaseController {
    @Reference
    private AdminService adminService;
    @Reference
    private HouseBrokerService houseBrokerService;

    private static final String PAGE_CREATE = "houseBroker/create";
    private static final String PAGE_EDIT = "houseBroker/edit";
    private static final String SHOW_ACTION = "redirect:/house/";

    @GetMapping("/create")
    public String create(HouseBroker houseBroker, Model model) {
        //使用houseBroker封装请求参数中的houseId
        //将houseBroker储存到请求域
        model.addAttribute("houseBroker", houseBroker);
        //查询出所有管理员列表储存到请求域
        saveAdminListToModel(model);
        return PAGE_CREATE;
    }

    private void saveAdminListToModel(Model model) {
        //还要获取所有的经纪人 从Admin中获取所有管理员列表
        List<Admin> adminList = adminService.findAll();
        model.addAttribute("adminList", adminList);
    }

    @PostMapping("/save")
    public String save(HouseBroker houseBroker, Model model) {
        //调用adminService方法根据id获取获取管理员详情
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        //将管理员相关信息(头像,名字)储存到houseBroker中
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBroker.setBrokerName(admin.getName());
        houseBrokerService.insert(houseBroker);
        return successPage(model, "新增经纪人成功");
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        //id代表的是经纪人表中id字段的值
        //调用HouseBrokerService方法根据id查询经纪人信息
        HouseBroker houseBroker = houseBrokerService.getById(id);
        model.addAttribute("houseBroker", houseBroker);
        //查询出所有管理员列表储存到请求域
        saveAdminListToModel(model);
        return PAGE_EDIT;
    }

    @PostMapping("/update")
    public String update(Model model, HouseBroker houseBroker) {
        //调用AdminService方法根据id查询管理员信息
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        //将管理员相关信息设置到houseBroker中
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        //调用HouseBrokerService修改经纪人信息
        houseBrokerService.update(houseBroker);
        return successPage(model,"修改经纪人信息成功");
    }

    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId,@PathVariable("id") Long id){
        houseBrokerService.delete(id);

        return SHOW_ACTION + houseId;
    }
}
