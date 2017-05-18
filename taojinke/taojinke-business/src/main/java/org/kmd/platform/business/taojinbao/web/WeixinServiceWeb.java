package org.kmd.platform.business.taojinbao.web;

import org.kmd.platform.business.taojinbao.service.WeiXinService;
import org.kmd.platform.business.taojinbao.util.AccessToken;
import org.kmd.platform.fundamental.logger.PlatformLogger;
import org.kmd.platform.fundamental.util.json.JsonResultUtils;
import org.springframework.stereotype.Component;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 * Created by Administrator on 2017/5/18 0018.
 */
@Component
@Path("/weiXin")
public class WeixinServiceWeb {

    private static PlatformLogger logger = PlatformLogger.getLogger(WeixinServiceWeb.class);

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/defineMenu")
    @POST
    public String defineMenu(@FormParam("jsonMenu") String jsonMenu) {
        if (jsonMenu == null || jsonMenu.trim().equals("")) {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能为空!");
        }
        try {
            String appId = "000000000000000000";
            // 第三方用户唯一凭证密钥
            String appSecret = "00000000000000000000000000000000";
            // 调用接口获取access_token
            AccessToken at = WeiXinService.getAccessToken(appId, appSecret);
            if (null != at) {
                // 调用接口创建菜单
                int result = WeiXinService.createMenu(jsonMenu, at.getToken());
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


}
