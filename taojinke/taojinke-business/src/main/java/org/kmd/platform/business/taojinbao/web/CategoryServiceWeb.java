package org.kmd.platform.business.taojinbao.web;

import org.kmd.platform.business.taojinbao.entity.Category;
import org.kmd.platform.business.taojinbao.service.CategoryService;
import org.kmd.platform.business.user.service.UserService;
import org.kmd.platform.fundamental.config.FundamentalConfigProvider;
import org.kmd.platform.fundamental.logger.PlatformLogger;
import org.kmd.platform.fundamental.util.json.JsonResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Administrator on 2017/6/3.
 */
@Component
@Path("/category")
public class CategoryServiceWeb {
    private static PlatformLogger logger = PlatformLogger.getLogger(GoodsServiceWeb.class);

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;



//    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
//    @Path("/update")
//    @POST
//    public String update(@Context HttpServletRequest request,@FormParam("id") String id,@FormParam("name") String name,@FormParam("description") String description,@FormParam("status") String status,
//                      @FormParam("display") String display){
//        long agentId = userService.getCurrentAgentId(request);
//        if(agentId==0){
//            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
//        }
//        Category category = new Category();
//        try {
//            category.setId(Long.parseLong(id));
//            category.setAgentId(agentId);
//            if (name!=null && !name.trim().equals("")){
//                category.setName(name);
//            }
//            if (description!=null && !description.trim().equals("")){
//                category.setDescription(description);
//            }
//            if (status!=null && !status.trim().equals("")){
//                category.setStatus(Integer.parseInt(status));
//            }
//            if (display!=null && !display.trim().equals("")){
//                category.setDisplay(Integer.parseInt(display));
//            }
//        }catch (Exception e){
//            logger.error("提供的参数不合法",e);
//            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不合法!");
//        }
//        categoryService.update(category);
//        return JsonResultUtils.getObjectResultByStringAsDefault(null, JsonResultUtils.Code.SUCCESS);
//    }

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/get")
    @POST
    public String get(@Context HttpServletRequest request){
        long agentId = userService.getCurrentAgentId(request);
        if(agentId==0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
        }
        List<Category> categories = categoryService.getByAgentId(agentId);
        return JsonResultUtils.getObjectResultByStringAsDefault(categories, JsonResultUtils.Code.SUCCESS);
    }

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/getAppCategory")
    @POST
    public String getAppCategory(@Context HttpServletRequest request){
        long agentId = userService.getCurrentAgentId(request);
        if(agentId==0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
        }
        List<Category> categories = categoryService.getByAgentId(agentId);
        return JsonResultUtils.getObjectResultByStringAsDefault(categories, JsonResultUtils.Code.SUCCESS);
    }

//    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
//    @Path("/delete")
//    @POST
//    public String delete(@Context HttpServletRequest request,@FormParam("id") String id){
//        if(id==null ||id.trim().equals("")){
//            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能为空!");
//        }
//        long agentId = userService.getCurrentAgentId(request);
//        if(agentId==0){
//            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
//        }
//        Category category = new Category();
//        category.setId(Long.parseLong(id));
//        //category.setAgentId(agentId);
//        int result = categoryService.delete(category);
//        if (result >0){
//            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "删除成功!");
//        }else{
//            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "删除失败!");
//        }
//    }
//
//    //上传用户图片
//    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
//    @Path("/uploadImage")
//    @POST
//    public String uploadImage(@Context HttpServletRequest request){
//        if(request==null){
//            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
//        }
//        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
//        MultipartFile file = multipartRequest.getFile("filename");
//        String filename = file.getOriginalFilename();
//        String[] temp = filename.split("\\.");
//        String suffix = temp[temp.length-1];
//
//        //获得用户图片路径
//        String userImgRootPath =  FundamentalConfigProvider.get("uploadImage.img.root.path") ;
//        String userImgRelativePath =  FundamentalConfigProvider.get("uploadImage.img.relative.path") ;
//        String userId = multipartRequest.getParameter("userId");
//        if(userId==null||userId.equals("")){
//            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(),"用户编号不能为空！");
//        }


//        String userImagePath =  userImgRootPath + userImgRelativePath+"/"+appId+"/"+userName+"."+suffix;
//        String userImageWebPath = userImgRelativePath+"/"+appId+"/"+userName+"."+suffix;
//
//
//
//        //如果文件存在则删除
//        File userImageFile = new File(userImagePath);
//        String oldImagePath = user.getImage();
//        if(oldImagePath!=null){
//            File oldImage = new File(userImgRootPath+oldImagePath);
//            if(oldImage.exists()){
//                oldImage.delete();
//            }
//        }
//        if(userImageFile.exists()){
//            userImageFile.delete();
//        }else{
//            File imageDir = new File(userImgRootPath+"/"+userImgRelativePath+"/"+appId);
//            if(!imageDir.exists()){
//                imageDir.mkdirs();
//            }
//        }
//
//        //写用户图片文件到指定路径
//        try {
//            file.transferTo(userImageFile);
//            User user1 = new User();
//            user1.setId(user.getId());
//            user1.setImage(userImageWebPath);
//            userService.updateUserImage(user1);
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }

        // 新增操作时，返回操作状态和状态码给客户端，数据区是为空的
      //  return JsonResultUtils.getObjectResultByStringAsDefault(null,JsonResultUtils.Code.SUCCESS);
   // }


}
