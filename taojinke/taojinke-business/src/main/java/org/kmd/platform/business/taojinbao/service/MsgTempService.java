package org.kmd.platform.business.taojinbao.service;

import org.kmd.platform.business.taojinbao.entity.MsgTemp;
import org.kmd.platform.business.taojinbao.mapper.MsgTempMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/5/25 0025.
 */
public class MsgTempService {

    @Autowired
    private MsgTempMapper mapper;

    public void add(MsgTemp msgTemp){
        mapper.add(msgTemp);
    }
    public int update(MsgTemp msgTemp){
        return mapper.update(msgTemp);
    }
    public int delete(MsgTemp msgTemp){
        return mapper.delete(msgTemp);
    }
    public List<MsgTemp> getMsgTempByOriginId(String originId){
        if(originId==null ){
            return null;
        }
        return mapper.getMsgTempByOriginId(originId);
    }
    public List<MsgTemp> getMsgTempByOriginIdAndModeMatch(String originId,String modeMatch){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("originId",originId);
        map.put("modeMatch",modeMatch);
        return mapper.getMsgTempByOriginIdAndModeMatch(new HashMap<String, Object>());
    }
}
