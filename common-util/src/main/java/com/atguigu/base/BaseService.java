package com.atguigu.base;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * 包名:com.atguigu.base
 *
 * @author 夏宇
 * 日期2022-05-1
 */
public interface BaseService<T> {
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
    PageInfo<T> findPage(Map<String, Object> filters);
}
