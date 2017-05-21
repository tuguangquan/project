package org.kmd.platform.business.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.kmd.platform.business.user.entity.*;
import org.kmd.platform.business.user.service.*;
import org.kmd.platform.fundamental.logger.PlatformLogger;
import org.kmd.platform.fundamental.util.json.JsonMapper;
import org.kmd.platform.fundamental.util.json.JsonResultUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * User: John
 * Date: 14-3-25
 * Time: 下午1:42
 * To change this template use File | Settings | File Templates.
 */
@Component
@Path("/authority")
public class AuthorityServiceWeb {

    private static PlatformLogger logger = PlatformLogger.getLogger(AuthorityServiceWeb.class);

    @Autowired
    AuthorityService authorityService;

    @Autowired
    UserService userService;

    @Autowired
    UserAuthorityService userAuthorityService;

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/add")
    @POST
    public String add(@FormParam("name") String name,@FormParam("description") String description, @FormParam("status") String status){
        long existAuthorityId;
        try{
            existAuthorityId = authorityService.getIdByName(name);

        }
        catch(Exception ex){
            existAuthorityId = 0;
        }
        if(existAuthorityId==0){
            Authority authority = new Authority();
            authority.setName(name);
            authority.setDescription(description);
            authority.setStatus(Integer.parseInt(status));
            authorityService.add(authority);
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
        }
        else{
            return JsonResultUtils.getObjectResultByStringAsDefault("fail",JsonResultUtils.Code.ERROR);
        }
    }

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/update")
    @POST
    public String update(@FormParam("jsonString") String jsonString){
        SubAuthority subAuthority = JsonMapper.buildNonDefaultMapper().fromJson(jsonString,SubAuthority.class);
        long authorityId= subAuthority.getId();
        String authorityName = subAuthority.getName();
        String authorityDescription = subAuthority.getDescription();
        int  authorityStatus = subAuthority.getStatus();
        Authority authority = new Authority();
        authority.setId(authorityId);
        authority.setName(authorityName);
        authority.setDescription(authorityDescription);
        authority.setStatus(authorityStatus);
        return  JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
    }

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/delete")
    @POST
    public String delete(@FormParam("jsonString") String jsonString){
        Authority authority = JsonMapper.buildNonDefaultMapper().fromJson(jsonString,Authority.class);
        String authorityName = authority.getName();
        int result = authorityService.delete(authority);
        List<UserAuthority> userAuthorityList = userAuthorityService.findByAuthorityName(authorityName);
        if(userAuthorityList.size()>0){
            for(UserAuthority ua:userAuthorityList){
                String userName = ua.getUserName();
                User user = userService.findByName(userName);
                String role = user.getRole();
                String[] roles = role.split(";");
                String[] newRoles = new String[roles.length-1];
                int temp=0;
                for(int i = 0;i<roles.length;i++){
                    if(!roles[i].equals(authorityName)){
                        newRoles[temp]=roles[i]+";";
                        temp++;
                    }
                }
                String roles2 = "";
                String nr;
                for(int i=0;i<newRoles.length;i++){
                    roles2+=newRoles[i];
                }
                if(roles2.equals("")){
                    nr="";
                }
                else{
                    nr = roles2.substring(0,roles2.length()-1);
                }
                user.setRole(nr);
                userService.update(user);
                userAuthorityService.delete(ua);
            }
        }
        if(result>0){
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
        }
        else{
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }
    }

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/list")
    @GET
    public String list(){
        List<Authority> list = authorityService.list();
        return JsonResultUtils.getObjectResultByStringAsDefault(list, JsonResultUtils.Code.SUCCESS);
    }

}
