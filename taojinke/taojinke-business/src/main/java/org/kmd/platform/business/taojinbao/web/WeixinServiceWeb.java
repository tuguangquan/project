package org.kmd.platform.business.taojinbao.web;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.kmd.platform.business.app.entity.App;
import org.kmd.platform.business.app.service.AppService;
import org.kmd.platform.business.taojinbao.entity.AgentInfo;
import org.kmd.platform.business.taojinbao.service.AgentInfoService;
import org.kmd.platform.business.taojinbao.service.WeiXinService;
import org.kmd.platform.business.taojinbao.util.AccessToken;
import org.kmd.platform.business.user.entity.User;
import org.kmd.platform.business.user.entity.UserAuthority;
import org.kmd.platform.business.user.service.AuthorityService;
import org.kmd.platform.business.user.service.UserAuthorityService;
import org.kmd.platform.business.user.service.UserService;
import org.kmd.platform.fundamental.logger.PlatformLogger;
import org.kmd.platform.fundamental.util.json.JsonResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;


/**
 * Created by Administrator on 2017/5/18 0018.
 */
@Component
@Path("/weiXin")
public class WeixinServiceWeb {

    private static PlatformLogger logger = PlatformLogger.getLogger(WeixinServiceWeb.class);


    final String STATUS="启用";
    final String role = "ROLE_ADMIN";
    @Autowired
    private AgentInfoService agentInfoService;
    @Autowired
    private AppService appService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private UserAuthorityService userAuthorityService;

    @Autowired
    private WeiXinService weiXinService;

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/add")
    @POST
    public String add(@FormParam("name") String name,@FormParam("description") String description,
                      @FormParam("weiXinId") String weiXinId,@FormParam("weiXinOriginalId") String weiXinOriginalId,
                      @FormParam("appID") String appId,@FormParam("appSecret") String appSecret,@FormParam("token") String token,
                      @FormParam("encodingAESKey") String encodingAESKey){
        if(weiXinId==null ||weiXinId.trim().equals("")|| weiXinOriginalId==null|| weiXinOriginalId.trim().equals("")
                ||appId==null|| appId.trim().equals("")||appSecret==null|| appSecret.trim().equals("")||
                token==null||token.trim().equals("")||name==null|| name.trim().equals("")
                ||description==null|| description.trim().equals("") ||encodingAESKey==null|| encodingAESKey.trim().equals("")){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能为空!");
        }
        AgentInfo  agentInfoExist = agentInfoService.getAgentInfoByAppID(appId);
        if(null!=agentInfoExist){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "该代理商已存在!");
        }
        long id;
        try{
            id = appService.getIdByName(weiXinId);
        }
        catch (Exception ex){
            id=0;
        }
        if(id==0){ //添加一个代理商
            Date now=new Date();
            App app=new App();
            app.setName(name);
            app.setDescription("");
            app.setStatus("启用");
            app.setCreatetime(now);
            appService.add(app);
        }else{
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请设置一个独一无二的名字!");
        }
        long appIdForAgent = appService.getIdByName(name);
        //为管理员添加账户
        User user = new User();
        user.setName(name);

        user.setRole(role);
        user.setStatus(STATUS);
        user.setAppId(appIdForAgent);
        userService.add(user);

        //为管理员设置角色
        long userId =  userService.getIdByName(name,appIdForAgent);
        long authorityId = authorityService.getIdByName(role);
        UserAuthority userAuthority = new UserAuthority();
        userAuthority.setUserId(userId);
        userAuthority.setAuthorityId(authorityId);
        userAuthority.setUserName(name);
        userAuthority.setAuthorityName(role);
        userAuthority.setAppId(appIdForAgent);
        userAuthorityService.add(userAuthority);

        //添加代理商信息
        AgentInfo  agentInfo = new AgentInfo();
        agentInfo.setWeixinId(weiXinId);
        agentInfo.setWeixinOriginalId(weiXinOriginalId);
        agentInfo.setAgentId(appIdForAgent);
        agentInfo.setAppSecret(appSecret);
        agentInfoService.add(agentInfo);
        return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
    }


    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/defineMenu")
    @POST
    public String defineMenu(@FormParam("jsonMenu") String jsonMenu,@FormParam("agentName") String agentName) {
        if (jsonMenu == null || jsonMenu.trim().equals("")) {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能为空!");
        }
        try {
            long agentId=appService.getIdByName(agentName);
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
    public String searchAllUser(@FormParam("agentName") String agentName) {
        //从微信获取所有关注者
        long agentId=appService.getIdByName(agentName);
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
    public String sendMsgToSomeUser(@FormParam("agentName") String agentName,@FormParam("openIds") String openIds,@FormParam("msg") String msg) {
        JSONArray jsonArray = JSONArray.fromObject(openIds);
        long agentId=appService.getIdByName(agentName);
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
