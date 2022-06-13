package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.House;
import com.atguigu.entity.bo.HouseQueryBo;
import com.atguigu.entity.vo.HouseVo;
import com.github.pagehelper.PageInfo;

/**
 * Date:2022/5/22
 * Author:夏宇
 * Description:
 */
public interface HouseService extends BaseService<House> {
    /**
     * 发布房源的方法
     * @param id
     * @param status
     */
    void publish(Long id, Integer status);
    /**
     * 分页查询前端房源列表
     * @param pageNum
     * @param pageSize
     * @param houseQueryBo
     * @return
     */
    PageInfo<HouseVo> findListPage(int pageNum, int pageSize, HouseQueryBo houseQueryBo);
}
