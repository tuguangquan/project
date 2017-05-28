package org.kmd.platform.business.taojinbao.service;

import org.kmd.platform.business.taojinbao.entity.AgentInfo;
import org.kmd.platform.business.taojinbao.mapper.AgentInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-17
 * Time: 下午9:23
 * To change this template use File | Settings | File Templates.
 */
public class AgentInfoService {
    @Autowired
    private AgentInfoMapper mapper;

    public void add(AgentInfo agentInfo){
        mapper.add(agentInfo);
    }

    public int update(AgentInfo agentInfo){
        return mapper.update(agentInfo);
    }

    public int delete(AgentInfo agentInfo){
        return mapper.delete(agentInfo);
    }

    public List<AgentInfo> list(){
        return mapper.findByCondition(new HashMap<String, Object>());
    }
    public AgentInfo getAgentInfoByAgentName(String agentName){
        return mapper.getAgentInfoByAgentName(agentName);
    }

    public AgentInfo getAgentInfoByAppID(String   appId) {
            return mapper.getAgentInfoByAppID(appId);
    }

    public  AgentInfo getAgentInfoByAgentId(long agentId){
        if(agentId==0 ){
            return null;
        }
        return mapper.getAgentInfoByAgentId(agentId);
    }

}
