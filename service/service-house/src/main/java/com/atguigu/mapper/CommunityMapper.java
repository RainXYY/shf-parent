package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseService;
import com.atguigu.entity.Community;

import java.util.List;

/**
 * Date:2022/5/22
 * Author:夏宇
 * Description:
 */
public interface CommunityMapper extends BaseMapper<Community> {
    List<Community> findAll();
}
