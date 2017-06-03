package org.kmd.platform.business.taojinbao.service;

import org.kmd.platform.business.taojinbao.entity.AgentGoodsRule;
import org.kmd.platform.business.taojinbao.mapper.AgentGoodsRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2017/6/3.
 */
public class AgentGoodsRuleService {

    @Autowired
    private AgentGoodsRuleMapper mapper;

    public void add(AgentGoodsRule agentGoodsRule){
        mapper.add(agentGoodsRule);
    }

    public int update(AgentGoodsRule agentGoodsRule){
        return mapper.update(agentGoodsRule);
    }
    public AgentGoodsRule getByAgentId(long agentId){
        return mapper.getByAgentId(agentId);
    }
}
