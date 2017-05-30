package org.kmd.platform.business.taojinbao.web;

import org.kmd.platform.business.app.entity.App;
import org.kmd.platform.business.app.service.AppService;
import org.kmd.platform.business.taojinbao.entity.AgentInfo;
import org.kmd.platform.business.taojinbao.service.AgentInfoService;
import org.kmd.platform.business.user.entity.User;
import org.kmd.platform.business.user.entity.UserAuthority;
import org.kmd.platform.business.user.service.AuthorityService;
import org.kmd.platform.business.user.service.UserAuthorityService;
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
import java.util.Date;

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

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/add")
    @POST
    public String add(@FormParam("name") String name,@FormParam("password") String password,@FormParam("agentName") String agentName){
        if(name==null ||name.trim().equals("")|| password==null|| password.trim().equals("") ){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能为空!");
        }
        AgentInfo  agentInfoExist = agentInfoService.getAgentInfoByAgentName(agentName);
        if(null!=agentInfoExist){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "代理商的名称已经存在!");
        }
        long id;
        try{
            id = appService.getIdByName(agentName);
        }
        catch (Exception ex){
            id=0;
        }
        if(id==0){ //添加一个代理商
            Date now=new Date();
            App app=new App();
            app.setName(agentName);
            app.setDescription("");
            app.setStatus("启用");
            app.setCreatetime(now);
            appService.add(app);
        }else{
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "代理商的名称已经存在!");
        }
        long appIdForAgent = appService.getIdByName(agentName);
        //为管理员添加账户
        User user = new User();
        user.setName(name);
        user.setPassword(password);
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
        return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
    }

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/basicSet")
    @POST
    public String basicSet(@Context HttpServletRequest request,@FormParam("audit") String audit,
                           @FormParam("agentMode") String agentMode,@FormParam("levelOne") String levelOne,
                           @FormParam("levelTwo") String levelTwo,@FormParam("levelThree") String levelThree){
        long agentId = userService.getCurrentAgentId(request);
        if(agentId==0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
        }
        App app = appService.getById(agentId);
        try{
            if(audit!=null &&!audit.trim().equals("") ){
                int aud = Integer.parseInt(audit);
                app.setAudit(aud);
            }
            if(audit!=null &&!audit.trim().equals("") ){
                int mode = Integer.parseInt(agentMode);
                app.setAgentMode(mode);
            }
            if(audit!=null &&!audit.trim().equals("") ){
                int one = Integer.parseInt(levelOne);
                app.setLevelOne(one);
            }
            if(audit!=null &&!audit.trim().equals("") ){
                int two = Integer.parseInt(levelTwo);
                app.setLevelTwo(two);
            }
            if(audit!=null &&!audit.trim().equals("") ){
                int three = Integer.parseInt(levelThree);
                app.setLevelThree(three);
            }
            appService.update(app);
        }catch (Exception e){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不合法!");
        }
        return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
    }

}
