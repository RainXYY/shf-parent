package com.atguigu.mapper;

import com.atguigu.entity.Dict;

import java.util.List;

/**
 * Date:2022/5/22
 * Author:夏宇
 * Description:
 */
public interface DictMapper {
    /**
     * 根据父节点查询Dict列表
     * @param parentId
     * @return
     */
    List<Dict> findListByParentId(Long parentId);

    /**
     * 以当前节点id作为父节点查询子节点数量
     * @param id
     * @return
     */
    Integer countIsParent(Long id);

    /**
     * 根据父节点的dictCode查询子节点列表
     * @param parentDictCode
     * @return
     */
    List<Dict> findDictListByParentDictCode(String parentDictCode);
}
