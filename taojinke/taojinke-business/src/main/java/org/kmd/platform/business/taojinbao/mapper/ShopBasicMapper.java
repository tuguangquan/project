package org.kmd.platform.business.taojinbao.mapper;

import org.kmd.platform.business.taojinbao.entity.ShopBasic;
import org.kmd.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;

/**
 * Created by Administrator on 2017\6\4 0004.
 */
public interface ShopBasicMapper  extends AbstractMapper<ShopBasic> {
    public ShopBasic getShopByAgentId(long agentId);


}
