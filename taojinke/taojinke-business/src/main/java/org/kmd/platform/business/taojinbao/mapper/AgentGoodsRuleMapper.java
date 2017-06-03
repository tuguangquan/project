package org.kmd.platform.business.taojinbao.mapper;

import org.kmd.platform.business.taojinbao.entity.AgentGoodsRule;
import org.kmd.platform.fundamental.orm.mapper.AbstractMapper;

/**
 * Created by Administrator on 2017/6/3.
 */
public interface AgentGoodsRuleMapper extends AbstractMapper<AgentGoodsRule> {
    public AgentGoodsRule getByAgentId(long agentId);
}
