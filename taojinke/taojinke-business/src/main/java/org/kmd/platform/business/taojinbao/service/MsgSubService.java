package org.kmd.platform.business.taojinbao.service;

import org.kmd.platform.business.taojinbao.entity.MsgSub;
import org.kmd.platform.business.taojinbao.mapper.MsgSubMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2017/5/26 0026.
 */
public class MsgSubService {
    @Autowired
    private MsgSubMapper mapper;

    public void add(MsgSub msgSub){
        mapper.add(msgSub);
    }
    public void update(MsgSub msgSub){
        mapper.update(msgSub);
    }
    public MsgSub getMsgSubByWeiXinOriginId(String weiXinOriginId){
       return mapper.getMsgSubByWeiXinOriginId(weiXinOriginId);
    }

}
