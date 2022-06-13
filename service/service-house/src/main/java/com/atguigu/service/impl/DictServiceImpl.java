package com.atguigu.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.entity.Dict;
import com.atguigu.mapper.DictMapper;
import com.atguigu.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Date:2022/5/22
 * Author:夏宇
 * Description:
 */
@Transactional
@Service(interfaceClass = DictService.class)
public class  DictServiceImpl implements DictService {
    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private JedisPool jedisPool;

    @Override
    public List<Map<String, Object>> findZnodes(Long id) {
        //1. 调用持久层方法，根据父节点id查询List<Dict>
        List<Dict> dictList = dictMapper.findListByParentId(id);

        List<Map<String, Object>> znodes = new ArrayList<>();
        //2. 将dictList转成List<Map>
        for (Dict dict : dictList) {
            Map<String, Object> znode = new HashMap();
            znode.put("id", dict.getId());
            znode.put("name", dict.getName());
            znode.put("isParent", dictMapper.countIsParent(dict.getId()) > 0);

            //将znode添加到znodes中
            znodes.add(znode);
        }
        return znodes;
    }

    @Override
    public List<Dict> findDictListByParentDictCode(String parentDictCode) {
        Jedis jedis = null;
        try {
            //1. 从redis中根据parentDictCode获取数据
            //1.1 获取jedis对象(连接)
            jedis = jedisPool.getResource();
            //1.2 调用jedis对象的get(key)获取值
            String value = jedis.get("shf:dict:parentDictCode:" + parentDictCode);
            //1.3 判断redis中是否有数据
            if (!StringUtils.isEmpty(value)) {
                //说明redis中有数据
                //1.4 将redis中的数据(JSON),转成List<Dict>，并返回
                return JSON.parseArray(value, Dict.class);
            }
            //2 如果redis中没有数据，则从MySQL中查询
            List<Dict> dictList = dictMapper.findDictListByParentDictCode(parentDictCode);
            //2.1 将数据转成JSON存储到redis中
            jedis.set("shf:dict:parentDictCode:" + parentDictCode, JSON.toJSONString(dictList));
            //2.2 返回集合
            return dictList;
        } finally {
            if (jedis != null) {
                //关闭连接
                jedis.close();
                //断开连接
                if (jedis.isConnected()) {
                    jedis.disconnect();
                }
            }
        }
    }

    @Override
    public List<Dict> findDictListByParentId(Long parentId) {
        Jedis jedis = null;
        try {
            //1. 从redis中根据parentDictCode获取数据
            //1.1 获取jedis对象(连接)
            jedis = jedisPool.getResource();
            //1.2 调用jedis对象的get(key)获取值
            String value = jedis.get("shf:dict:parentId:" + parentId);
            //1.3 判断redis中是否有数据
            if (!StringUtils.isEmpty(value)) {
                //说明redis中有数据
                //1.4 将redis中的数据(JSON),转成List<Dict>，并返回
                return JSON.parseArray(value, Dict.class);
            }
            //2 如果redis中没有数据，则从MySQL中查询
            List<Dict> dictList = dictMapper.findListByParentId(parentId);
            //2.1 将数据转成JSON存储到redis中
            jedis.set("shf:dict:parentId:" + parentId, JSON.toJSONString(dictList));
            //2.2 返回集合
            return dictList;
        } finally {
            if (jedis != null) {
                //关闭连接
                jedis.close();
                //断开连接
                if (jedis.isConnected()) {
                    jedis.disconnect();
                }
            }
        }
    }
}

