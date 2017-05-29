package org.kmd.platform.business.taojinbao.web;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.kmd.platform.business.taojinbao.entity.AgentInfo;
import org.kmd.platform.business.taojinbao.entity.MsgSub;
import org.kmd.platform.business.taojinbao.entity.MsgTemp;
import org.kmd.platform.business.taojinbao.service.AgentInfoService;
import org.kmd.platform.business.taojinbao.service.MsgSubService;
import org.kmd.platform.business.taojinbao.service.MsgTempService;
import org.kmd.platform.business.taojinbao.service.WeiXinService;
import org.kmd.platform.business.taojinbao.util.AccessToken;
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
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.File;
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
    private UserService userService;
    @Autowired
    private WeiXinService weiXinService;
    @Autowired
    private MsgTempService msgTempService;
    @Autowired
    private MsgSubService msgSubService;

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/submit")
    @POST
    public String add(@Context HttpServletRequest request,@FormParam("name") String name,@FormParam("description") String description,
                      @FormParam("weiXinId") String weiXinId,@FormParam("weiXinOriginalId") String weiXinOriginalId,
                      @FormParam("appID") String appId,@FormParam("appSecret") String appSecret,@FormParam("token") String token,
                      @FormParam("encodingAESKey") String encodingAESKey){
        if(weiXinId==null ||weiXinId.trim().equals("")|| weiXinOriginalId==null|| weiXinOriginalId.trim().equals("")
                ||appId==null|| appId.trim().equals("")||appSecret==null|| appSecret.trim().equals("")||
                token==null||token.trim().equals("")||name==null|| name.trim().equals("")
                ||description==null|| description.trim().equals("") ||encodingAESKey==null|| encodingAESKey.trim().equals("")){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能为空!");
        }
        //校验appId appSecret
        AccessToken accessToken = weiXinService.getAccessToken(appId,appSecret);
        if (accessToken == null){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "设置的appId，appSecret有误!");
        }
        long agentId = userService.getCurrentAgentId(request);
        if(agentId==0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
        }
        AgentInfo  agentInfoExist = agentInfoService.getAgentInfoByAppID(appId);
        if(null!=agentInfoExist){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "该公众号已绑定!");
        }
        AgentInfo  agentInfoByAgentId = agentInfoService.getAgentInfoByAgentId(agentId);
        if(agentInfoByAgentId!=null){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "您已绑定了一个公众号，目前只支持绑定一个微信公众号!");
        }
        //添加代理商信息
        AgentInfo  agentInfo = new AgentInfo();
        agentInfo.setWeixinId(weiXinId);
        agentInfo.setWeixinOriginalId(weiXinOriginalId);
        agentInfo.setAgentId(agentId);
        agentInfo.setAppID(appId);
        agentInfo.setAppSecret(appSecret);
        agentInfo.setAgentName(name);
        agentInfo.setToken(token);
        agentInfo.setDescription(description);
        agentInfo.setEncodingAESKey(encodingAESKey);
        agentInfoService.add(agentInfo);
        return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/update")
    @POST
    public String reset(@Context HttpServletRequest request,@FormParam("name") String name,@FormParam("description") String description,
                      @FormParam("weiXinId") String weiXinId,@FormParam("weiXinOriginalId") String weiXinOriginalId,
                      @FormParam("appID") String appId,@FormParam("appSecret") String appSecret,@FormParam("token") String token,
                      @FormParam("encodingAESKey") String encodingAESKey){
        if(weiXinId==null ||weiXinId.trim().equals("")|| weiXinOriginalId==null|| weiXinOriginalId.trim().equals("")
                ||appId==null|| appId.trim().equals("")||appSecret==null|| appSecret.trim().equals("")||
                token==null||token.trim().equals("")||name==null|| name.trim().equals("")
                ||description==null|| description.trim().equals("") ||encodingAESKey==null|| encodingAESKey.trim().equals("")){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能为空!");
        }
        //校验appId appSecret
        AccessToken accessToken = weiXinService.getAccessToken(appId,appSecret);
        if (accessToken == null){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "设置的appId，appSecret有误!");
        }
        long agentId = userService.getCurrentAgentId(request);
        if(agentId==0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
        }
        AgentInfo  agentInfoExist = agentInfoService.getAgentInfoByAppID(appId);
        if(null==agentInfoExist){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "该公众号还没有绑定!");
        }
        //添加代理商信息
        AgentInfo  agentInfo = new AgentInfo();
        agentInfo.setId(agentInfoExist.getId());
        agentInfo.setWeixinId(weiXinId);
        agentInfo.setWeixinOriginalId(weiXinOriginalId);
        agentInfo.setAgentId(agentId);
        agentInfo.setAppID(appId);
        agentInfo.setAppSecret(appSecret);
        agentInfo.setAgentName(name);
        agentInfo.setDescription(description);
        agentInfo.setEncodingAESKey(encodingAESKey);
        agentInfoService.update(agentInfo);
        return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/get")
    @POST
    public String get(@Context HttpServletRequest request,@FormParam("appId") String appId){
        if( appId==null|| appId.trim().equals("")){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "weiXinOriginalId参数不能为空!");
        }
        AgentInfo  agentInfo = agentInfoService.getAgentInfoByAppID(appId);
        return JsonResultUtils.getObjectResultByStringAsDefault(agentInfo, JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/delete")
    @POST
    public String delete(@Context HttpServletRequest request,@FormParam("appId") String appId){
        if( appId==null|| appId.trim().equals("")){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "weiXinOriginalId参数不能为空!");
        }
        AgentInfo  agentInfo =  new AgentInfo();
        agentInfoService.delete(agentInfo);
        return JsonResultUtils.getObjectResultByStringAsDefault(agentInfo, JsonResultUtils.Code.SUCCESS);
    }


    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/defineMenu")
    @POST
    public String defineMenu(@Context HttpServletRequest request,@FormParam("jsonMenu") String jsonMenu) {
        if (jsonMenu == null || jsonMenu.trim().equals("")) {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能为空!");
        }
        try {
            long agentId = userService.getCurrentAgentId(request);
            if(agentId==0){
                return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
            }
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

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/getMenu")
    @POST
    public String getMenu(@Context HttpServletRequest request) {
        try {
            long agentId = userService.getCurrentAgentId(request);
            if(agentId==0){
                return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
            }
            AgentInfo agentInfo = agentInfoService.getAgentInfoByAgentId(agentId);
            if (agentInfo == null){
                return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "还没有绑定公众号，请先绑定微信公众号");
            }
            AccessToken at = weiXinService.getAccessToken(agentInfo.getAppID(),agentInfo.getAppSecret());
            if (null != at) {
                // 调用接口创建菜单
                String result = weiXinService.getMenu(at.getToken());
                // 判断菜单创建结果
                if (null != result){
                    logger.info("菜单查询成功！");
                    return JsonResultUtils.getObjectResultByStringAsDefault(result, JsonResultUtils.Code.SUCCESS);
                }else{
                    logger.info("菜单查询失败");
                    return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "菜单查询失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "菜单查询失败");
    }
    //上传微信素材
    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/addMaterial")
    @POST
    public String addMaterial(@Context HttpServletRequest request,@FormParam("type") String type){
        if(request==null){
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }
        try {
        long agentId = userService.getCurrentAgentId(request);
        if(agentId==0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
        }
        AgentInfo agentInfo = agentInfoService.getAgentInfoByAgentId(agentId);
        if (agentInfo == null){
             return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "还没有绑定公众号，请先绑定微信公众号");
        }
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        MultipartFile file = multipartRequest.getFile("filename");
        String filename = file.getOriginalFilename();
        String[] temp = filename.split("\\.");
        String suffix = temp[temp.length-1];
        //获得用户图片路径
        String userImgRootPath =  FundamentalConfigProvider.get("uploadImage.img.relative.path") ;
        String userImgRelativePath =  FundamentalConfigProvider.get("uploadImage.img.relative.path") ;
        String userImagePath =  userImgRootPath + userImgRelativePath+"/"+agentId+"."+suffix;
            AccessToken at = weiXinService.getAccessToken(agentInfo.getAppID(), agentInfo.getAppSecret());
            if (null != at) {
                JSONObject jsonObject = null;
                if (type == null || type.trim().equals(""))     {
                    jsonObject = weiXinService.addMaterialEver(userImagePath, at.getToken());
                }else {
                    jsonObject = weiXinService.addMaterialEver(userImagePath,type,at.getToken());
                }
                // 判断菜单创建结果
                if (jsonObject != null){
                    logger.info("上传永久素材成功");
                    return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "上传永久素材成功");
                }else{
                    logger.info("上传永久素材失败");
                    return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "上传永久素材失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 新增操作时，返回操作状态和状态码给客户端，数据区是为空的
        return JsonResultUtils.getObjectResultByStringAsDefault("upload success",JsonResultUtils.Code.SUCCESS);
    }

    @Path("/searchAllUser")
    @POST
    public String searchAllUser(@Context HttpServletRequest request) {
        //从微信获取所有关注者
        long agentId = userService.getCurrentAgentId(request);
        if(agentId==0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
        }
        AgentInfo agentInfo = agentInfoService.getAgentInfoByAgentId(agentId);
        if (agentInfo == null){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "还没有绑定公众号，请先绑定微信公众号");
        }
        AccessToken at = weiXinService.getAccessToken(agentInfo.getAppID(),agentInfo.getAppSecret());
        if (null != at) {
            // 调用接口所有关注着
            List openIdList = weiXinService.getUser(at.getToken());
            return JsonResultUtils.getObjectResultByStringAsDefault(openIdList, JsonResultUtils.Code.SUCCESS);
        }
        return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "凭证获取失败");
    }



    @Path("/sendMsgToSomeUser")
    @POST
    public String sendMsgToSomeUser(@Context HttpServletRequest request,@FormParam("openIds") String openIds,@FormParam("msg") String msg) {
        JSONArray jsonArray = JSONArray.fromObject(openIds);
        long agentId = userService.getCurrentAgentId(request);
        if(agentId==0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
        }
        AgentInfo agentInfo = agentInfoService.getAgentInfoByAgentId(agentId);
        if (agentInfo == null){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "还没有绑定公众号，请先绑定微信公众号");
        }
        AccessToken at = weiXinService.getAccessToken(agentInfo.getAppID(),agentInfo.getAppSecret());
        if (null != at) {
            // 调用接口创建菜单
            int result = weiXinService.sendMsgToSomeUser(msg, jsonArray, at.getToken());
            if (result==0){
                return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "发送成功");
            }else
                return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "发送失败，请稍后再试");
        }
        return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "凭证获取失败");
    }

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/addMsgTemp")
    @POST
    public String addMsgTemp(@Context HttpServletRequest request,@FormParam("msgType") String msgType,@FormParam("modeContent") String modeContent,@FormParam("msgMatch") String msgMatch,@FormParam("priority") String priority){
        if(msgType==null ||msgType.trim().equals("")||modeContent==null ||modeContent.trim().equals("")){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能为空!");
        }
        long agentId = userService.getCurrentAgentId(request);
        if(agentId==0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
        }
        AgentInfo agentInfo = agentInfoService.getAgentInfoByAgentId(agentId);
        if (agentInfo==null){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "信息绑定不全，请联系管理员补全信息!");
        }
        String weiXinOriginId = agentInfo.getWeixinOriginalId();
        MsgTemp msgTemp =new MsgTemp();
        msgTemp.setAgentId(agentId);
        msgTemp.setModeContent(modeContent);
        msgTemp.setWeiXinOriginId(weiXinOriginId);
        msgTemp.setMsgType(msgType);
        if (msgMatch!=null){
            msgTemp.setModeMatch(msgMatch);
        }
        if (priority!=null){
            msgTemp.setPriority(Integer.parseInt(priority));
        }
        msgTempService.add(msgTemp);
        return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/addSubMsg")
    @POST
    public String addSubMsg(@Context HttpServletRequest request,@FormParam("content") String content){
        if(content==null ||content.trim().equals("")){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "添加关注回复不能为空!");
        }
        long agentId = userService.getCurrentAgentId(request);
        if(agentId==0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
        }
        AgentInfo agentInfo = agentInfoService.getAgentInfoByAgentId(agentId);
        if (agentInfo==null){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "信息绑定不全，请联系管理员补全信息!");
        }
        String weiXinOriginId = agentInfo.getWeixinOriginalId();
        MsgSub msgSub = msgSubService.getMsgSubByWeiXinOriginId(weiXinOriginId);
        MsgSub msgUpdate = new MsgSub();
        msgUpdate.setContent(content);
        msgUpdate.setAgentId(agentId);
        msgUpdate.setWeiXinOriginId(weiXinOriginId);
        if (msgSub == null){
            msgSubService.add(msgUpdate);
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "添加成功!");
        }else{
            msgSubService.update(msgUpdate);
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
        }
    }

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/msgTempList")
    @POST
    public String msgTempList(@Context HttpServletRequest request){
        long agentId = userService.getCurrentAgentId(request);
        if(agentId==0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
        }
        AgentInfo agentInfo = agentInfoService.getAgentInfoByAgentId(agentId);
        if (agentInfo==null){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "信息绑定不全，请联系管理员补全信息!");
        }
        String weiXinOriginId = agentInfo.getWeixinOriginalId();
        List<MsgTemp> msgTempList = msgTempService.list(weiXinOriginId,agentId);
        return JsonResultUtils.getObjectResultByStringAsDefault(msgTempList, JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/test")
    @GET
    public String test(@Context HttpServletRequest request){
        request.getSession().getServletContext().getRealPath(File.separator);
        return JsonResultUtils.getObjectResultByStringAsDefault( "", JsonResultUtils.Code.SUCCESS);
    }


}
