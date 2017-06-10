package org.kmd.platform.business.taojinbao.service;

import org.kmd.platform.business.taojinbao.entity.AgentCategory;
import org.kmd.platform.business.taojinbao.mapper.AgentCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-6-10
 * Time: 上午11:40
 * To change this template use File | Settings | File Templates.
 */
public class AgentCategoryService {

    @Autowired
    private AgentCategoryMapper mapper;

    public List<AgentCategory> getByAgentId(long agentId){
        return mapper.getByAgentId(agentId);
    }
}
