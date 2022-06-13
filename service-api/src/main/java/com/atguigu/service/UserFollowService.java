package com.atguigu.service;

import com.atguigu.entity.UserFollow;
import com.atguigu.entity.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

/**
 * Date:2022/5/26
 * Author:夏宇
 * Description:
 */
public interface UserFollowService {
    //根据用户id和房源id查询用户关注信息
    UserFollow findByUserIdAndHouseId(Long userId, Long houseId);
    //更新房源的关注信息
    void update(UserFollow userFollow);
    //新增关注消息
    void insert(UserFollow userFollow);
    /**
     * 分页查询用户关注列表
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     */
    PageInfo<UserFollowVo> findListPage(int pageNum, int pageSize, Long userId);
}
