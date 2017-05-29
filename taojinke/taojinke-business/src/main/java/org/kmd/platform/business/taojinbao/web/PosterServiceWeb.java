package org.kmd.platform.business.taojinbao.web;

import net.sf.json.JSONObject;
import org.kmd.platform.business.taojinbao.entity.AgentInfo;
import org.kmd.platform.business.taojinbao.entity.Poster;
import org.kmd.platform.business.taojinbao.entity.Shop;
import org.kmd.platform.business.taojinbao.service.*;
import org.kmd.platform.business.taojinbao.util.AccessToken;
import org.kmd.platform.business.taojinbao.util.ImageRemarkUtil;
import org.kmd.platform.business.user.service.UserService;
import org.kmd.platform.fundamental.logger.PlatformLogger;
import org.kmd.platform.fundamental.util.json.JsonResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-29
 * Time: 下午4:12
 * To change this template use File | Settings | File Templates.
 */
@Component
@Path("/poster")
public class PosterServiceWeb {
    private static PlatformLogger log = PlatformLogger.getLogger(PosterServiceWeb.class);

    @Autowired
    private AgentInfoService agentInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    private WeiXinService weiXinService;
    @Autowired
    private PosterService posterService;
    @Autowired
    private ShopService shopService;

    @Path("/createPoster")
    @POST
    public String createPoster(@Context HttpServletRequest request,@FormParam("openId") String openId) throws Exception {
        String absolutePath = request.getSession().getServletContext().getRealPath(File.separator);
        if (openId == null || openId.trim().equals("")) {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能为空!");
        }
        Shop shop =  shopService.getByOpenId(openId);
            //根据agentId      得到会员id
        long sense_id = 0;
        if (shop ==null ){
             log.info("还不是会员，不能申请海报");
             return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "还不是会员，不能申请海报!");
         }else {
             sense_id = shop.getId();    // 会员id
         }
        long agentId =shop.getAgentId();
        AgentInfo agentInfo = agentInfoService.getAgentInfoByAgentId(agentId);
        if (agentInfo == null){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "还没有绑定公众号，请先绑定微信公众号");
        }
        AccessToken at = weiXinService.getAccessToken(agentInfo.getAppID(),agentInfo.getAppSecret());
        if (null != at) {
            // 调用接口所有关注着
            boolean b = createPoster(at.getToken(),agentInfo, sense_id, absolutePath);
            if (b){
                return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "success!");
            } else{
                return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "生成海报失败！！！");
            }
        }
        return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "凭证获取失败");
    }

    //生成海报
    public boolean createPoster(String accessToken,AgentInfo agentInfo,long sense_id, String absolutePath) throws Exception {
        //生成二维码
        String ticket = weiXinService.getQRCodeTicket(accessToken,sense_id);
        if (agentInfo == null)  {
            return false;
        }
        weiXinService.get_QR(agentInfo.getWeixinOriginalId()+".jpg", absolutePath+"posterImage\\", ticket);
        //将二维码水印到海报上
        log.info("给图片添加水印图片开始...");
        ImageRemarkUtil.setImageMarkOptions(1.0f, 115, 400, null, null);
        // 给图片添加水印图片
        ImageRemarkUtil.markImageByIcon(absolutePath+"posterImage\\"+agentInfo.getWeixinOriginalId()+".jpg", absolutePath+"posterImage\\"+1+".jpg", absolutePath+"posterImage\\"+agentInfo.getAgentId()+1+".jpg");
        System.out.println("给图片添加水印图片结束...");
        //上传图片到微信服务器，得到media_id，并存到数据库中
        JSONObject resultJSON = weiXinService.addMaterialEver(absolutePath + "posterImage\\" + agentInfo.getAgentId() + 1 + ".jpg", "image", accessToken) ;
        if (resultJSON == null){
            return false;
        }else{
            if (resultJSON.get("media_id") != null) {
                log.info("上传永久素材成功");
            } else {
                log.error("上传永久素材失败");
                return false;
            }
        }
        String media_id = resultJSON.getString("media_id");
        Poster poster = new Poster();
        poster.setWeiXinOriginId(agentInfo.getWeixinOriginalId());
        poster.setAgentId(agentInfo.getAgentId());
        poster.setType(1);
        poster.setMedia_id(media_id);
        posterService.add(poster);
        return true;
    }
}
