package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Community;

import java.util.List;

/**
 * Date:2022/5/22
 * Author:夏宇
 * Description:
 */
public interface CommunityService extends BaseService<Community> {
    List<Community> findAll();

}
