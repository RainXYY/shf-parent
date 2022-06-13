package com.atguigu.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.House;
import com.atguigu.entity.bo.HouseQueryBo;
import com.atguigu.entity.vo.HouseVo;
import com.atguigu.mapper.HouseMapper;
import com.atguigu.service.HouseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Date:2022/5/22
 * Author:夏宇
 * Description:
 */
@Service(interfaceClass = HouseService.class)
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {
    @Autowired
    private HouseMapper houseMapper;
    @Override
    public BaseMapper<House> getEntityMapper() {
        return houseMapper;
    }

    @Override
    public void publish(Long id, Integer status) {
        //要发布房源其实就是修改房源的status的值
        House house = new House();
        //设置房源的status
        house.setStatus(status);
        //设置房源的id
        house.setId(id);
        houseMapper.update(house);
    }

    @Override
    public PageInfo<HouseVo> findListPage(int pageNum, int pageSize, HouseQueryBo houseQueryBo) {
        //开启分页
        PageHelper.startPage(pageNum,pageSize);
        //调用业务层的方法查询分页数据，并且封装返回
        return new PageInfo<HouseVo>(houseMapper.findListPage(houseQueryBo),10);
    }
}
