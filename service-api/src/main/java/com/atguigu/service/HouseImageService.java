package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.HouseImage;

import java.util.List;

/**
 * Date:2022/5/23
 * Author:夏宇
 * Description:
 */
public interface HouseImageService extends BaseService<HouseImage> {
    /**
     * 根据房源id和type查询图片列表
     * @param houseId
     * @param type
     * @return
     */
    List<HouseImage> findHouseImageList(Long houseId, Integer type);
}
