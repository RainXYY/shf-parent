package com.atguigu.base;

import com.atguigu.util.CastUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 包名:com.atguigu.base
 *
 * @author 夏宇
 * 日期2022-05-18
 */
@Transactional(propagation = Propagation.REQUIRED)
public abstract class BaseServiceImpl<T> {
    public abstract BaseMapper<T> getEntityMapper();

    public void insert(T t) {
        getEntityMapper().insert(t);
    }


    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public T getById(Long id) {
        return getEntityMapper().getById(id);
    }

    public void update(T t) {
        getEntityMapper().update(t);
    }

    public void delete(Long id) {
        getEntityMapper().delete(id);
    }

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public PageInfo<T> findPage(Map<String, Object> filters) {
        //将pageSize和pageNum强转成int类型
        int pageNum = CastUtil.castInt(filters.get("pageNum"),1);
        int pageSize = CastUtil.castInt(filters.get("pageSize"),10);
        PageHelper.startPage(pageNum,pageSize);
        //调用持久层的方法查询数据集
        //封装返回结果
        return new PageInfo<>(getEntityMapper().findPage(filters),10);
    }
}
