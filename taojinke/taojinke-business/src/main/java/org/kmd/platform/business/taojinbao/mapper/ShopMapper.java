package org.kmd.platform.business.taojinbao.mapper;

import org.kmd.platform.business.taojinbao.entity.Shop;
import org.kmd.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-29
 * Time: 下午4:08
 * To change this template use File | Settings | File Templates.
 */
public  interface ShopMapper extends AbstractMapper<Shop> {
    public List<Shop> getByAgentId(long agentId);
    public List<Shop> shopList(long agentId);
    public Shop getByOpenId(String openId);
}
