package org.whut.seis.business.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.whut.seis.business.entity.User;
import org.whut.seis.business.service.AuthorityService;
import org.whut.seis.business.service.UserAuthorityService;
import org.whut.seis.business.service.UserService;
import util.json.JsonResultUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiaozhujun
 * Date: 14-1-26
 * Time: 上午11:53
 * To change this template use File | Settings | File Templates.
 */
@Component
@Path("/user")
public class UserServiceWeb {

  @Autowired
  private UserService userService;


    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/getIdByName")
    @POST
    public String getIdByName(@FormParam("name") String name){
        if (name == null) {
            return "ERROR";
        }
        long id;
        try {
            id  = userService.getIdByName(name);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "ERROR";
        }
        // 新增操作时，返回操作状态和状态码给客户端，数据区是为空的
        return ""+id;
    }

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("findByName/{name}")
    @GET
    public String findByName(@PathParam("name") String name){
        if (name == null) {
            System.out.println(name);
            return "ERROR";
        }
        User user;
        try {
            System.out.println(name);
            user = userService.findByName(name);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "ERROR";
        }
        // 新增操作时，返回操作状态和状态码给客户端，数据区是为空的
        return JsonResultUtils.getObjectResultByStringAsDefault(user, JsonResultUtils.Code.SUCCESS);
    }
    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/currentUser")
    @GET
    public String  currentUser(){

        return "";
    }

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/currentUserName")
    @GET
    public String  currentUserName(){

        return "";
    }

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/currentUserId")
    @GET
    public String  currentUserId(){
        return "";
    }

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/currentUserAppId")
    @GET
    public String  currentUserAppId(){
        return "";
    }
    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/add")
    @POST
    public String add(@FormParam("name") String name,@FormParam("password") String password,@FormParam("sex") String sex,@FormParam("role") String role,/*@FormParam("status") String status,*/@FormParam("appName") String appName){
          return "";
    }

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/list")
    @GET
    public String list(){


        return "";
    }

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/delete")
    @POST
    public String delete(@FormParam("jsonString") String jsonString){
        return "";
    }

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/update")
    @POST
    public String update(@FormParam("jsonString") String jsonString){
        return "";
    }

    //上传用户图片
    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/selfUploadImage")
    @POST
    public String selfUploadImage(@Context HttpServletRequest request){
        return "";
    }

    //上传用户图片
    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/uploadImage")
    @POST
    public String uploadImage(@Context HttpServletRequest request){
        return "";}


    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/keepAlive")
    @POST
    public String keepAlive(){
        return "";
    }
}
