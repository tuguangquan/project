package org.kmd.platform.business.taojinbao.service;

import org.kmd.platform.business.taojinbao.entity.BasicShop;
import org.kmd.platform.business.taojinbao.mapper.BasicShopMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2017\6\4 0004.
 */
public class BasicShopService {
    @Autowired
    private BasicShopMapper mapper;

    public void add(BasicShop basicShop){
        mapper.add(basicShop);
    }

    public long update(BasicShop basicShop){
       return mapper.update(basicShop);

    }

    public BasicShop getShopByAgentId(long agentId){

        return mapper.getShopByAgentId(agentId);
    }

}
