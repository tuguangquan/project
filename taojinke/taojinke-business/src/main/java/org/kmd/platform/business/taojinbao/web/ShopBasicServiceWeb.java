package org.kmd.platform.business.taojinbao.web;

import org.kmd.platform.business.taojinbao.entity.AgentInfo;

import org.kmd.platform.business.taojinbao.entity.ShopBasic;
import org.kmd.platform.business.taojinbao.service.AgentInfoService;
import org.kmd.platform.business.taojinbao.service.ShopBasicService;
import org.kmd.platform.business.user.service.UserService;
import org.kmd.platform.fundamental.logger.PlatformLogger;
import org.kmd.platform.fundamental.util.json.JsonResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Created by Administrator on 2017\6\4 0004.
 */
//@Component
@Path("/shopBasic")
public class ShopBasicServiceWeb {
    private static PlatformLogger logger = PlatformLogger.getLogger(GoodsServiceWeb.class);
    @Autowired
    private UserService userService;

    @Autowired
    private ShopBasicService shopBasicService;

    @Autowired
    private AgentInfoService agentInfoService;

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/submit")
    @POST
    public String submit(@Context HttpServletRequest request,@FormParam("title") String title,@FormParam("subhead") String subhead,@FormParam("description") String description)  {

        long agentId = userService.getCurrentAgentId(request);
        if(agentId==0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");

        }
       // AgentInfo agentInfo = agentInfoService.getAgentInfoByAgentId(agentId);
        ShopBasic shopBasic = shopBasicService.getShopByaId (agentId);
        //ShopBasic shopBasic = new ShopBasic();
        long id = shopBasic.getId();
        if (id != 0) {

            try {

                shopBasic.setAgentId(agentId);
                if (title != null && !title.trim().equals("")) {
                    shopBasic.setTitle(title);
                }
                if (description != null && !description.trim().equals("")) {
                    shopBasic.setDescription(description);
                }
                if (subhead != null && !subhead.trim().equals("")) {
                    shopBasic.setSubhead(subhead);
                }

            } catch (Exception e) {
                logger.error("提供的参数不合法", e);
                return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不合法!");
            }
            shopBasicService.update(shopBasic);
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "更改成功，审核中！！！");
        }else { 
            AgentInfo agentInfo = agentInfoService.getAgentInfoByAgentId(agentId);
            if (agentInfo == null) {
                return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "微信未绑定!");

            }
            String originId = agentInfo.getWeixinOriginalId();
            shopBasic.setAgentId(agentId);
            shopBasic.setDescription(description);
            shopBasic.setOriginId(originId);
            shopBasic.setSubhead(subhead);
            shopBasic.setTitle(title);
            shopBasicService.add(shopBasic);
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "添加成功，审核中！！！");

        }

    }

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/get")
    @POST
    public String get(@Context HttpServletRequest request){
        long agentId = userService.getCurrentAgentId(request);
        if(agentId==0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
        }

        ShopBasic shopBasic = shopBasicService.getShopByaId(agentId);
        return JsonResultUtils.getObjectResultByStringAsDefault(shopBasic, JsonResultUtils.Code.SUCCESS);
    }

}
