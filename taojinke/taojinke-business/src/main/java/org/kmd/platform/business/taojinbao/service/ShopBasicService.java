package org.kmd.platform.business.taojinbao.service;

import org.kmd.platform.business.taojinbao.entity.ShopBasic;
import org.kmd.platform.business.taojinbao.mapper.ShopBasicMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2017\6\4 0004.
 */
public class ShopBasicService {
    @Autowired
    private ShopBasicMapper mapper;

    public void add(ShopBasic shopBasic){
        mapper.add(shopBasic);
    }

    public int updateByid(ShopBasic shopBasic){
       return mapper.update(shopBasic);

    }

}
