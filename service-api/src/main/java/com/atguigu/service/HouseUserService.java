package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.HouseUser;

import java.util.List;

/**
 * Date:2022/5/23
 * Author:夏宇
 * Description:
 */
public interface HouseUserService extends BaseService<HouseUser> {
    /**
     * 根据房源id查询房东列表
     * @param houseId
     * @return
     */
    List<HouseUser> findHouseUserListByHouseId(Long houseId);
}
