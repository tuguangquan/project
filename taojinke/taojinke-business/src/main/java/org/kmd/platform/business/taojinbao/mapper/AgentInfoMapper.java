package org.kmd.platform.business.taojinbao.mapper;

import org.kmd.platform.business.taojinbao.entity.AgentInfo;
import org.kmd.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-17
 * Time: 下午9:23
 * To change this template use File | Settings | File Templates.
 */
public interface AgentInfoMapper extends AbstractMapper<AgentInfo> {
    public List<AgentInfo> findByCondition(Map<String,Object> map);
    public AgentInfo getAgentInfoByAgentName(String agentName);
    public AgentInfo getAgentInfoByAgentId(long agentId);

}
