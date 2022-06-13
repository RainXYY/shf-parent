package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.House;
import com.atguigu.entity.bo.HouseQueryBo;
import com.atguigu.entity.vo.HouseVo;
import com.github.pagehelper.Page;

/**
 * Date:2022/5/22
 * Author:夏宇
 * Description:
 */
public interface HouseMapper extends BaseMapper<House> {
    /**
     * 查询前端房源分页列表数据
     * @param houseQueryBo
     * @return
     */
    Page<HouseVo> findListPage(HouseQueryBo houseQueryBo);
}
