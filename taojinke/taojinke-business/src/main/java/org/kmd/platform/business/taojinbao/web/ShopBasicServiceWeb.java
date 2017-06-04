package org.kmd.platform.business.taojinbao.web;

import org.kmd.platform.business.taojinbao.entity.AgentInfo;
import org.kmd.platform.business.taojinbao.entity.ShopBasic;
import org.kmd.platform.business.taojinbao.service.AgentInfoService;
import org.kmd.platform.business.taojinbao.service.ShopBasicService;
import org.kmd.platform.business.user.service.UserService;
import org.kmd.platform.fundamental.util.json.JsonResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

/**
 * Created by Administrator on 2017\6\4 0004.
 */
@Component
@Path("/shopBasic")
public class ShopBasicServiceWeb {

    @Autowired
    private UserService userService;

    @Autowired
    private ShopBasicService shopBasicService;

    @Autowired
    private AgentInfoService agentInfoService;

    @Path("/add")
    @POST
    public String add(@Context HttpServletRequest request,@FormParam("title") String title,@FormParam("subhead") String subhead,@FormParam("description") String description)  {
        if(title==null ||title.trim().equals("")|| subhead==null|| subhead.trim().equals("")){
             return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能为空!");
        }
        long agentId = userService.getCurrentAgentId(request);
        if(agentId==0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
        }
        AgentInfo agentInfo = agentInfoService.getAgentInfoByAgentId(agentId);
        if(agentInfo ==null){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "微信未绑定!");

        }
        String originId = agentInfo.getWeixinOriginalId();
        ShopBasic shopBasic = new ShopBasic();
        shopBasic.setAgentId(agentId);
        shopBasic.setDescription(description);
        shopBasic.setOriginId(originId);
        shopBasic.setSubhead(subhead);
        shopBasic.setTitle(title);
        shopBasicService.add(shopBasic);
        return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "添加成功，审核中！！！");
    }
}
