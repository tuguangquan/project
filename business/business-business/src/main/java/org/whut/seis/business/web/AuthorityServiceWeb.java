package org.whut.seis.business.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.seis.business.entity.*;
import org.whut.seis.business.service.*;

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
    @Autowired
    AuthorityService authorityService;

    @Autowired
    PowerService  powerService;

    @Autowired
    AuthorityPowerService authorityPowerService;

    @Autowired
    UserService userService;

    @Autowired
    UserAuthorityService userAuthorityService;

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/add")
    @POST
    public String add(@FormParam("name") String name,@FormParam("description") String description, @FormParam("status") String status,@FormParam("resource") String resource){
        /*if(name==null || name.trim().equals("") || description==null || description.trim().equals("") || status==null || status.trim().equals("")){
             return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能为空!");
         }*/
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
            long currentAuthorityId = authorityService.getIdByName(name);
            String[] resourceArray = resource.split(";");
            for(int i = 0;i<resourceArray.length;i++){
                long powerId = powerService.getIdByResource(resourceArray[i]);
                AuthorityPower authorityPower = new AuthorityPower();
                authorityPower.setAuthorityId(currentAuthorityId);
                authorityPower.setPowerId(powerId);
                authorityPower.setPowerResource(resourceArray[i]);
                authorityPower.setAuthorityName(name);
                authorityPowerService.add(authorityPower);
            }
            return "success";
        }
        else{
            return "ERROR";
        }


    }

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/update")
    @POST
    public String update(@FormParam("jsonString") String jsonString){
      return "ERROR";
    }

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/delete")
    @POST
    public String delete(@FormParam("jsonString") String jsonString){

            return "SUCCESS";

    }

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/list")
    @GET
    public String list(){
        List<Authority> list = authorityService.list();
        List<SubAuthority> listNew =new ArrayList<SubAuthority>();
        //List<AuthorityPower> authorityPowerList = authorityPowerService.getAuthorityPowerList();
        for(Authority a:list){
            SubAuthority subAuthority = new SubAuthority();
            String  authorityName = a.getName();
            subAuthority.setId(a.getId());
            subAuthority.setName(authorityName);
            subAuthority.setDescription(a.getDescription());
            subAuthority.setStatus(a.getStatus());
            List<String> resourceList = authorityPowerService.getResourcesByAuthorityName(authorityName);
            String resources = "";
            for(String s:resourceList){
                String s2 = s+";";
                resources+=s2;
                System.out.println(">>>>>>>>"+s);
            }
            System.out.println("<<<<<<<<"+resources);
            String r1;
            if(resources.equals("")){
                r1="";
            }
            else{
                r1 = resources.substring(0,resources.length()-1);
            }
            System.out.println(">>>>>>>>"+r1);
            // String r2 = "\'"+r1+"\'";
             /*String resource="";
             int length = authorityPowerList.size();
             for(int i = 0;i<length;i++){
                 String authorityName = authorityPowerList.get(i).getAuthorityName();
                 if(authorityName.equals(a.getName())){
                     String s = "\'"+authorityPowerList.get(i).getPowerResource()+"\'";
                     resource +=s ;
                 }
             } */
            subAuthority.setResource(r1);
            listNew.add(subAuthority);
        }
        return "";
    }
    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/show")
    @GET
    public String show(){
        List<Authority> list=authorityService.list();
        return "";
    }

}
