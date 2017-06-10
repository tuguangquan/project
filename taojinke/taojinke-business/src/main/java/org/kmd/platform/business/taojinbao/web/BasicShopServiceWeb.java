package org.kmd.platform.business.taojinbao.web;

import org.kmd.platform.business.taojinbao.entity.AgentInfo;
import org.kmd.platform.business.taojinbao.entity.BasicShop;
import org.kmd.platform.business.taojinbao.service.AgentInfoService;
import org.kmd.platform.business.taojinbao.service.BasicShopService;
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
@Component
@Path("/basicShop")
public class BasicShopServiceWeb {
    private static PlatformLogger logger = PlatformLogger.getLogger(GoodsServiceWeb.class);
    @Autowired
    private UserService userService;

    @Autowired
    private BasicShopService basicShopService;

    @Autowired
    private AgentInfoService agentInfoService;

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/submit")
    @POST
    public String submit(@Context HttpServletRequest request, @FormParam("title") String title, @FormParam("subhead") String subhead, @FormParam("description") String description) {
        long agentId = userService.getCurrentAgentId(request);
        if (agentId == 0) {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
        }
        BasicShop basicShop = basicShopService.getShopByAgentId(agentId);
        long id = basicShop.getId();
        if (id > 0) {
            basicShop.setAgentId(agentId);
            if (title != null && !title.trim().equals("")) {
                basicShop.setTitle(title);
            }
            if (description != null && !description.trim().equals("")) {
                basicShop.setDescription(description);
            }
            if (subhead != null && !subhead.trim().equals("")) {
                basicShop.setSubhead(subhead);
            }
            basicShopService.update(basicShop);
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "更改成功，审核中！！！");
        } else {
            AgentInfo agentInfo = agentInfoService.getAgentInfoByAgentId(agentId);
            if (agentInfo == null) {
                return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "微信未绑定!");
            }
            String originId = agentInfo.getWeixinOriginalId();
            basicShop.setAgentId(agentId);
            basicShop.setDescription(description);
            basicShop.setOriginId(originId);
            basicShop.setSubhead(subhead);
            basicShop.setTitle(title);
            basicShopService.add(basicShop);
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "添加成功，审核中！！！");
        }
    }

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/get")
    @POST
    public String get(@Context HttpServletRequest request) {
        long agentId = userService.getCurrentAgentId(request);
        if (agentId == 0) {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
        }
        BasicShop basicShop = basicShopService.getShopByAgentId(agentId);
        return JsonResultUtils.getObjectResultByStringAsDefault(basicShop, JsonResultUtils.Code.SUCCESS);
    }

}
