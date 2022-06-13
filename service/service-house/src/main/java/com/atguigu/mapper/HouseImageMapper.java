package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Date:2022/5/23
 * Author:夏宇
 * Description:
 */
public interface HouseImageMapper extends BaseMapper<HouseImage> {
    /**
     * 根据房源id和type查询图片列表
     * @param houseId
     * @param type
     * @return
     */
    List<HouseImage> findHouseImageList(@Param("houseId") Long houseId, @Param("type") Integer type);
}
