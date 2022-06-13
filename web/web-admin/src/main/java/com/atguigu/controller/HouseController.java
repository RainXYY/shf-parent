package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.en.DictCode;
import com.atguigu.en.HouseStatus;
import com.atguigu.entity.*;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Date:2022/5/22
 * Author:夏宇
 * Description:
 */
@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {
    private static final String PAGE_INDEX = "house/index";
    private static final String PAGE_CREATE = "house/create";
    private static final String PAGE_EDIT ="house/edit" ;
    private static final String LIST_ACTION = "redirect:/house";
    private static final String PAGE_SHOW = "house/show";
    @Reference
    private HouseService houseService;
    @Reference
    private CommunityService communityService;
    @Reference
    private DictService dictService;
    @Reference
    private HouseImageService houseImageService;
    @Reference
    private HouseBrokerService houseBrokerService;
    @Reference
    private HouseUserService houseUserService;

    @RequestMapping
    public String index(@RequestParam Map<String, Object> filters, Model model) {
        //处理pageNum和pageSize为空的情况
        if (!filters.containsKey("pageNum")) {
            filters.put("pageNum", 1);
        }
        if (!filters.containsKey("pageSize")) {
            filters.put("pageSize", 10);
        }
        //1. 分页搜索房源列表信息
        PageInfo<House> pageInfo = houseService.findPage(filters);

        //2. 将房源分页信息存储到请求域
        model.addAttribute("page", pageInfo);

        //3. 将搜索条件存储到请求域
        model.addAttribute("filters", filters);

        //4. 查询所有小区、以及字典里的各种列表存储到请求域
        saveAllDictToRequestScope(model);

        return PAGE_INDEX;
    }

    /**
     * 查询所有小区以及字典里的各种列表存储到请求域
     *
     * @param model
     */
    private void saveAllDictToRequestScope(Model model) {
        // 查询所有小区
        List<Community> communityList = communityService.findAll();
        // 查询各种初始化列表:户型列表、楼层列表、装修情况列表....
        List<Dict> houseTypeList = dictService.findDictListByParentDictCode(DictCode.HOUSETYPE.getMessage());
        List<Dict> floorList = dictService.findDictListByParentDictCode(DictCode.FLOOR.getMessage());
        List<Dict> buildStructureList = dictService.findDictListByParentDictCode(DictCode.BUILDSTRUCTURE.getMessage());
        List<Dict> directionList = dictService.findDictListByParentDictCode(DictCode.DIRECTION.getMessage());
        List<Dict> decorationList = dictService.findDictListByParentDictCode(DictCode.DECORATION.getMessage());
        List<Dict> houseUseList = dictService.findDictListByParentDictCode(DictCode.HOUSEUSE.getMessage());
        // 将所有小区存储到请求域
        model.addAttribute("communityList", communityList);
        // 将各种列表存储到请求域
        model.addAttribute("houseTypeList", houseTypeList);
        model.addAttribute("floorList", floorList);
        model.addAttribute("buildStructureList", buildStructureList);
        model.addAttribute("directionList", directionList);
        model.addAttribute("decorationList", decorationList);
        model.addAttribute("houseUseList", houseUseList);
    }

    @GetMapping("/create")
    public String create(Model model){
        saveAllDictToRequestScope(model);
        return PAGE_CREATE;
    }

    @PostMapping("/save")
    public String save(House house,Model model){
        //未发布
        house.setStatus(HouseStatus.UNPUBLISHED.code);
        houseService.insert(house);
        return successPage(model,"添加房源信息成功");
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,Model model){
        House house = houseService.getById(id);
        model.addAttribute("house",house);

        saveAllDictToRequestScope(model);

        return PAGE_EDIT;
    }
    @PostMapping("/update")
    public String update(House house,Model model){
        houseService.update(house);
        return successPage(model,"修改房源信息成功");
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        houseService.delete(id);
        return LIST_ACTION;
    }

    @GetMapping("/publish/{id}/{status}")
    public String publish(@PathVariable("id") Long id,@PathVariable("status") Integer status){
        houseService.publish(id,status);
        return LIST_ACTION;
    }

    @GetMapping("/{houseId}")
    public String detail(@PathVariable("houseId") Long houseId,Model model){
        //1. 根据房源id查询房源详情
        House house = houseService.getById(houseId);
        //2. 根据小区id查询小区详情
        Community community = communityService.getById(house.getCommunityId());
        //3. 根据房源id查询房源的房源图片列表
        List<HouseImage> houseImage1List = houseImageService.findHouseImageList(houseId,1);
        //4. 根据房源id查询房源的房产图片列表
        List<HouseImage> houseImage2List = houseImageService.findHouseImageList(houseId,2);
        //5. 根据房源id查询房源的经纪人列表
        List<HouseBroker> houseBrokerList = houseBrokerService.findHouseBrokerListByHouseId(houseId);
        //6. 根据房源id查询房源的房东列表
        List<HouseUser> houseUserList = houseUserService.findHouseUserListByHouseId(houseId);
        //将上述查询到的内容存储到请求域
        model.addAttribute("house",house);
        model.addAttribute("community",community);
        model.addAttribute("houseImage1List",houseImage1List);
        model.addAttribute("houseImage2List",houseImage2List);
        model.addAttribute("houseBrokerList",houseBrokerList);
        model.addAttribute("houseUserList",houseUserList);

        return PAGE_SHOW;
    }
}
