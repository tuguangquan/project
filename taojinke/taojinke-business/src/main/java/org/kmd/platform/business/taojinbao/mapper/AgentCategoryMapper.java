package org.kmd.platform.business.taojinbao.mapper;

import org.kmd.platform.business.taojinbao.entity.AgentCategory;
import org.kmd.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-6-10
 * Time: 上午11:29
 * To change this template use File | Settings | File Templates.
 */
public interface AgentCategoryMapper extends AbstractMapper<AgentCategory> {
    public List<AgentCategory> getByAgentId(long agentId);
}
