package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.HouseBroker;

import java.util.List;

/**
 * Date:2022/5/23
 * Author:夏宇
 * Description:
 */
public interface HouseBrokerMapper extends BaseMapper<HouseBroker> {
    /**
     * 根据房源id查询房源经纪人列表
     * @param houseId
     * @return
     */
    List<HouseBroker> findHouseBrokerListByHouseId(Long houseId);
}
