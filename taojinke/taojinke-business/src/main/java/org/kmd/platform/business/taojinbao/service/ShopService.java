package org.kmd.platform.business.taojinbao.service;

import org.kmd.platform.business.taojinbao.entity.Poster;
import org.kmd.platform.business.taojinbao.entity.Shop;
import org.kmd.platform.business.taojinbao.mapper.ShopMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-29
 * Time: 下午4:12
 * To change this template use File | Settings | File Templates.
 */
public class ShopService {
    @Autowired
    private ShopMapper mapper;

    public void add(Shop shop){
        mapper.add(shop);
    }
    public void update(Shop shop){
        mapper.update(shop);
    }
    public Shop getByAgentId(long agentId){
        return mapper.getByAgentId(agentId);
    }
    public Shop getByAgentIdWithOutStatue(long agentId){
        return mapper.getByAgentIdWithOutStatue(agentId);
    }
    public Shop getByOpenId(String openId){
        return mapper.getByOpenId(openId);
    }


}
