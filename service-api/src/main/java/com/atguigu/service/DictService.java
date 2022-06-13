package com.atguigu.service;

import com.atguigu.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * Date:2022/5/22
 * Author:夏宇
 * Description:
 */
public interface DictService {
    /**
     * 根据父节点id查询数字字典
     * @param id
     * @return
     */
    List<Map<String,Object>> findZnodes(Long id);

    /**
     * 根据父节点的dictCode查询子节点列表集合
     * @param parentDictCode
     * @return
     */
    List<Dict> findDictListByParentDictCode(String parentDictCode);

    /**
     * 根据父节点的id查询其所有的子节点
     * @param parentId
     * @return
     */
    List<Dict> findDictListByParentId(Long parentId);
}
