package org.kmd.platform.business.taojinbao.web;

import org.kmd.platform.business.taojinbao.entity.AgentInfo;
import org.kmd.platform.business.taojinbao.service.AgentInfoService;
import org.kmd.platform.fundamental.logger.PlatformLogger;
import org.kmd.platform.fundamental.util.json.JsonResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-17
 * Time: 下午9:24
 * To change this template use File | Settings | File Templates.
 */
@Component
@Path("/agentInfo")
public class AgentInfoServiceWeb {
    private static PlatformLogger logger = PlatformLogger.getLogger(AgentInfoServiceWeb.class);

    @Autowired
    private AgentInfoService agentInfoService;

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/add")
    @POST
    public String add(@FormParam("weiXinId") String weiXinId,@FormParam("weiXinOriginalId") String weiXinOriginalId,@FormParam("appID") String appID,@FormParam("appSecret") String appSecret,@FormParam("agentId") String agentId,@FormParam("profit") String profit){
        if(weiXinId==null ||weiXinId.trim().equals("")|| weiXinOriginalId==null|| weiXinOriginalId.trim().equals("") ||appID==null|| appID.trim().equals("")||appSecret==null|| appSecret.trim().equals("")||agentId==null||agentId.trim().equals("")||profit==null||profit.trim().equals("")){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能为空!");
        }

        AgentInfo  agentInfoExist = agentInfoService.getIdByAppID(appID);
        if(null!=agentInfoExist){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "该代理商已存在!");
        }
        AgentInfo  agentInfo = new AgentInfo();
        agentInfo.setWeixinId(weiXinId);
        agentInfo.setWeixinOriginalId(weiXinOriginalId);
        agentInfo.setAgentId(agentId);
        agentInfo.setAppSecret(appSecret);
        agentInfo.setProfit(Float.parseFloat(profit));
        agentInfoService.add(agentInfo);
        return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
    }
}
