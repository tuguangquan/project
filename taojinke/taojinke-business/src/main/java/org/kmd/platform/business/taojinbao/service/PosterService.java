package org.kmd.platform.business.taojinbao.service;

import net.sf.json.JSONObject;
import org.kmd.platform.business.taojinbao.entity.AgentInfo;
import org.kmd.platform.business.taojinbao.entity.Poster;
import org.kmd.platform.business.taojinbao.mapper.PosterMapper;
import org.kmd.platform.business.taojinbao.util.ImageRemarkUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-29
 * Time: 上午10:19
 * To change this template use File | Settings | File Templates.
 */
public class PosterService {
    @Autowired
    private PosterMapper mapper;

    public void add(Poster poster){
        mapper.add(poster);
    }
    public Poster getPosterByWeiXinOriginId(String weiXinOriginId){
        return mapper.getPosterByWeiXinOriginId(weiXinOriginId);
    }


}
