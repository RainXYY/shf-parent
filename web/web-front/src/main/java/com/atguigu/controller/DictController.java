package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Date:2022/5/24
 * Author:夏宇
 * Description:
 */
@RestController
@RequestMapping("/dict")
public class DictController {
    @Reference
    private DictService dictService;

    @RequestMapping("/findDictListByParentId/{parentId}")
    public Result findDictListByParentId(@PathVariable("parentId") Long parentId){
        //调用业务层方法根据父节点id查询子节点列表
        List<Dict> dictList = dictService.findDictListByParentId(parentId);
        //将数据封装到Result中返回
        return Result.ok(dictList);
    }

    @RequestMapping("/findDictListByParentDictCode/{dictCode}")
    public Result findDictListByParentDictCode(@PathVariable("dictCode") String dictCode){
        //调用业务层的方法根据父节点的dictCode查询子节点列表
        List<Dict> dictList = dictService.findDictListByParentDictCode(dictCode);
        //封装响应数据进行响应
        return Result.ok(dictList);
    }
}
