package org.kmd.platform.business.taojinbao.mapper;

import org.kmd.platform.business.taojinbao.entity.BasicShop;
import org.kmd.platform.fundamental.orm.mapper.AbstractMapper;

/**
 * Created by Administrator on 2017\6\4 0004.
 */
public interface BasicShopMapper extends AbstractMapper<BasicShop> {
    public BasicShop getShopByAgentId(long agentId);
}
