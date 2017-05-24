package org.kmd.platform.business.taojinbao.service;

import org.kmd.platform.business.taojinbao.entity.Goods;
import org.kmd.platform.business.taojinbao.mapper.AgentInfoMapper;
import org.kmd.platform.business.taojinbao.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-17
 * Time: 下午8:12
 * To change this template use File | Settings | File Templates.
 */
public class GoodsService {
    @Autowired
    private GoodsMapper mapper;
    public void add(Goods goods){
        mapper.add(goods);
    }

    public int update(Goods goods){
        return mapper.update(goods);
    }

    public int delete(Goods goods){
        return mapper.delete(goods);
    }

    public List<Goods> findByCondition(String top_rate,String lower_rate,String top_price,String lower_price,String goods_name){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("top_rate",top_rate);
        map.put("lower_rate",lower_rate);
        map.put("top_price",top_price);
        map.put("lower_price",lower_price);
        map.put("goods_name",goods_name);
        return mapper.findByCondition(new HashMap<String, Object>());
    }
}
