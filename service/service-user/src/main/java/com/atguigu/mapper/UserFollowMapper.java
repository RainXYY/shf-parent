package com.atguigu.mapper;

import com.atguigu.entity.UserFollow;
import com.atguigu.entity.vo.UserFollowVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

/**
 * Date:2022/5/26
 * Author:夏宇
 * Description:
 */
public interface UserFollowMapper {
    //根据用户Id和房源Id查询用户信息
    UserFollow findByUserIdAndHouseId(@Param("userId") Long userId,@Param("houseId") Long houseId);
    //更新用户关注信息
    void update(UserFollow userFollow);
    //新增用户关注信息
    void insert(UserFollow userFollow);
    /**
     * 分页查询用户的关注列表
     * @param userId
     * @return
     */
    Page<UserFollowVo> findListPage(@Param("userId") Long userId);
}
