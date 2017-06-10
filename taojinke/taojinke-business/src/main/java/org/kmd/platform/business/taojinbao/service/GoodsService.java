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

    public List<Goods> getByCategoryId(int categoryId){
        return mapper.getByCategoryId(categoryId);
    }


    public List<Goods> getLastGoods(int index,int size){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("index",index);
        map.put("size",size);
        return mapper.getLastGoods(map);
    }


    public List<Goods> findByCondition(String top_rate,String lower_rate,String top_price,String lower_price,String goods_name,int index,int size){
        HashMap<String, Object> map = new HashMap<String, Object>();
       if (top_rate.equals("")){
           map.put("top_rate",null);
       }
        if (lower_rate.equals("")){
            map.put("lower_rate",null);
        }
        if (top_price.equals("")){
            map.put("top_price",null);
        }
        if (lower_price.equals("")){
            map.put("lower_price",null);
        }
        if (goods_name.equals("")){
            map.put("goods_name",null);
        }
        map.put("index",index);
        map.put("size",size);
        return mapper.findByCondition(map);
    }
}
