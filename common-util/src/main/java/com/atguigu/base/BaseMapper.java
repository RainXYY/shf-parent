package com.atguigu.base;

import com.github.pagehelper.Page;

import java.util.Map;

/**
 * Date:2022/5/18
 * Author:夏宇
 * Description:
 */
public interface BaseMapper<T> {
    /**
     * 新增
     * @param t
     */
    void insert(T t);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    T getById(Long id);

    /**
     * 修改
     * @param t
     */
    void update(T t);

    /**
     * 根据id删除
     * @param id
     */
    void delete(Long id);

    /**
     * 分页搜索
     * @param filters
     * @return
     */
    Page<T> findPage(Map<String, Object> filters);
}
