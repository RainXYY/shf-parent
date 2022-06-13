package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.*;
import com.atguigu.entity.bo.HouseQueryBo;
import com.atguigu.entity.vo.HouseVo;
import com.atguigu.result.Result;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date:2022/5/24
 * Author:夏宇
 * Description:
 */
@RestController
@RequestMapping("/house")
public class HouseController {
    @Reference
    private HouseService houseService;
    @Reference
    private CommunityService communityService;
    @Reference
    private HouseBrokerService houseBrokerService;
    @Reference
    private HouseImageService houseImageService;
    @Reference
    private HouseUserService houseUserService;
    @Reference
    private UserFollowService userFollowService;

    @PostMapping("/list/{pageNum}/{pageSize}")
    public Result findListPage(@PathVariable("pageNum") Integer pageNum,
                               @PathVariable("pageSize") Integer pageSize,
                               @RequestBody HouseQueryBo houseQueryBo){
        //调用业务层的方法查询分页数据
        PageInfo<HouseVo> pageInfo = houseService.findListPage(pageNum, pageSize, houseQueryBo);

        return Result.ok(pageInfo);
    }


    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id, HttpSession session){
        //1.根据房源id获取房源信息
        House house = houseService.getById(id);
        //2.根据小区id获取小区信息
        Community community = communityService.getById(house.getCommunityId());
        //3.根据房源id查询房源经纪人列表
        List<HouseBroker> houseBrokerList = houseBrokerService.findHouseBrokerListByHouseId(id);
        //4.根据房源id查询房源图片列表
        List<HouseImage> houseImageList = houseImageService.findHouseImageList(id, 1);
        //5.根据房源id查询房东列表
        List<HouseUser> houseUserList = houseUserService.findHouseUserListByHouseId(id);
        //6.查询当前用户是否关注了该房源
        //6.1获取当前用户
        UserInfo userInfo = (UserInfo) session.getAttribute("USER");
        boolean isFollow = false;
        //不一定登录了,所有要判断userInfo是否为空
        if(userInfo != null){
            //判断当前用户是否关注了当前房源
            //调用业务层UserFollowService方法查询用户是否已经关注房源
            UserFollow userFollow = userFollowService.findByUserIdAndHouseId(userInfo.getId(), id);
            if (userFollow != null && userFollow.getIsDeleted() == 0) {
                //已关注
                isFollow = true;
            }
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("house",house);
        resultMap.put("community",community);
        resultMap.put("houseBrokerList",houseBrokerList);
        resultMap.put("houseImage1List",houseImageList);
        resultMap.put("houseUserList",houseUserList);
        resultMap.put("isFollow",isFollow);
        //7.封装数据进行响应
        return Result.ok(resultMap);
    }
}
