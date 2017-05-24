package org.kmd.platform.business.taojinbao.service;

import org.kmd.platform.business.taojinbao.entity.GoodsSelect;
import org.kmd.platform.business.taojinbao.mapper.GoodsSelectMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2017/5/24 0024.
 */
public class GoodsSelectService {

    @Autowired
    private GoodsSelectMapper mapper;
    public void add(GoodsSelect goodsSelect){
        mapper.add(goodsSelect);
    }
    public int update(GoodsSelect goodsSelect){
        return mapper.update(goodsSelect);
    }
    public int delete(GoodsSelect goodsSelect){
        return mapper.delete(goodsSelect);
    }
}
