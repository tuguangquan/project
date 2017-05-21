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

    public List<Goods> findByCondition(){
        return mapper.findByCondition(new HashMap<String, Object>());
    }
}
