package org.kmd.platform.business.taojinbao.web;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.kmd.platform.business.taojinbao.entity.AgentInfo;
import org.kmd.platform.business.taojinbao.service.AgentInfoService;
import org.kmd.platform.business.taojinbao.service.WeiXinService;
import org.kmd.platform.business.taojinbao.util.AccessToken;
import org.kmd.platform.fundamental.logger.PlatformLogger;
import org.kmd.platform.fundamental.util.json.JsonResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


/**
 * Created by Administrator on 2017/5/18 0018.
 */
@Component
@Path("/weiXin")
public class WeixinServiceWeb {

    private static PlatformLogger logger = PlatformLogger.getLogger(WeixinServiceWeb.class);
    @Autowired
    private AgentInfoService agentInfoService;
    @Autowired
    private WeiXinService weiXinService;

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/defineMenu")
    @POST
    public String defineMenu(@FormParam("jsonMenu") String jsonMenu,@FormParam("agentId") String agentId) {
        if (jsonMenu == null || jsonMenu.trim().equals("")) {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能为空!");
        }
        try {
            AgentInfo agentInfo = agentInfoService.getAgentInfoByAgentId(agentId);
            if (agentInfo == null){
                return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "还没有绑定公众号，请先绑定微信公众号");
            }
            AccessToken at = weiXinService.getAccessToken(agentInfo.getAppID(),agentInfo.getAppSecret());
            if (null != at) {
                // 调用接口创建菜单
                int result = weiXinService.createMenu(jsonMenu, at.getToken());
                // 判断菜单创建结果
                if (0 == result){
                    logger.info("自定义菜单成功！");
                }else{
                    logger.info("自定义菜单失败，错误码：" + result);
                    return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "菜单创建失败，错误码：" + result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "菜单创建成功!");
    }
    @Path("/searchAllUser")
    @POST
    public String searchAllUser(@FormParam("agentId") String agentId) {
        //从微信获取所有关注者
        AgentInfo agentInfo = agentInfoService.getAgentInfoByAgentId(agentId);
        if (agentInfo == null){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "还没有绑定公众号，请先绑定微信公众号");
        }
        AccessToken at = weiXinService.getAccessToken(agentInfo.getAppID(),agentInfo.getAppSecret());
        if (null != at) {
            // 调用接口创建菜单
            List openIdList = weiXinService.getUser(at.getToken());
            return JsonResultUtils.getObjectResultByStringAsDefault(openIdList, JsonResultUtils.Code.SUCCESS);
        }
        return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "凭证获取失败");
    }

    @Path("/sendMsgToSomeUser")
    @POST
    public String sendMsgToSomeUser(@FormParam("agentId") String agentId,@FormParam("openIds") String openIds,@FormParam("msg") String msg) {
        JSONArray jsonArray = JSONArray.fromObject(openIds);
        AgentInfo agentInfo = agentInfoService.getAgentInfoByAgentId(agentId);
        if (agentInfo == null){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "还没有绑定公众号，请先绑定微信公众号");
        }
        AccessToken at = weiXinService.getAccessToken(agentInfo.getAppID(),agentInfo.getAppSecret());
        if (null != at) {
            // 调用接口创建菜单
            int result = weiXinService.sendMsgToSomeUser(msg,jsonArray,at.getToken());
            if (result==0){
                return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "发送成功");
            }else
                return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "发送失败，请稍后再试");
        }
        return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "凭证获取失败");
    }

}
