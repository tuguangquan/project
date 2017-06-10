package org.kmd.platform.business.taojinbao.web;

import org.kmd.platform.business.taojinbao.dto.ShopExtend;
import org.kmd.platform.business.taojinbao.entity.AgentInfo;
import org.kmd.platform.business.taojinbao.entity.Order;
import org.kmd.platform.business.taojinbao.entity.Poster;
import org.kmd.platform.business.taojinbao.entity.Shop;
import org.kmd.platform.business.taojinbao.service.*;
import org.kmd.platform.business.taojinbao.util.AccessToken;
import org.kmd.platform.business.taojinbao.util.ShopEunm;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-29
 * Time: 下午4:22
 * To change this template use File | Settings | File Templates.
 */
@Component
@Path("/shop")
public class ShopServiceWeb {
    private static PlatformLogger logger = PlatformLogger.getLogger(ShopServiceWeb.class);

    @Autowired
    private ShopService shopService;
    @Autowired
    private UserService userService;
    @Autowired
    private PosterService posterService;

    @Autowired
    private WeiXinService weixinService;
    @Autowired
    private AgentInfoService agentInfoService;

    @Autowired
    private OrderService orderService;


    @Path("/add")
    @POST
    public String add(@FormParam("openId") String openId,@FormParam("qq") String qq,@FormParam("zfb") String zfb,@FormParam("name") String name,@FormParam("phone") String phone)  {
        if(openId==null ||openId.trim().equals("")|| qq==null|| qq.trim().equals("")
                ||zfb==null|| zfb.trim().equals("")||name==null|| name.trim().equals("")||phone==null||phone.trim().equals("")){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能为空!");
        }
        Shop shop = new Shop();
        shop.setName(name);
        shop.setOpenId(openId);
        shop.setPhone(phone);
        shop.setQq(qq);
        shop.setZfb(zfb);
        shop.setStatus(ShopEunm.RESP_STATUS_AUDITING);//审核中
        shopService.update(shop);
        return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "添加成功，审核中！！！");
    }
    @Path("/audit")
    @POST
    public String audit(@FormParam("openId") String openId,@FormParam("pid") String pid,@FormParam("shopName") String shopName,@FormParam("description") String description) {
        if(openId==null ||openId.trim().equals("")||pid==null ||pid.trim().equals("")){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能为空!");
        }
        Shop shop = new Shop();
        shop.setOpenId(openId);
        shop.setPid(pid);
        shop.setStatus(ShopEunm.RESP_STATUS_AUDITED);//审核通过
        if (shopName!=null && !shopName.trim().equals(""))   {
          shop.setShopName(shopName);
        }
        if (description!=null && !description.trim().equals(""))   {
            shop.setDescription(description);
        }
        shopService.update(shop);
        return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "审核通过");
    }

    @Path("/shopList")
    @POST
    public String shopList(@Context HttpServletRequest request) {
        long agentId = userService.getCurrentAgentId(request);
        if(agentId==0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
        }
        List<ShopExtend> shopExtends = new ArrayList<ShopExtend>();
        List<Shop> shopList =  shopService.shopList(agentId);
        //根据每个shop，去拿每个shop下面的推广量
        for(Shop shop:shopList){
            long shopId = shop.getId();
            //todo      去查hop下面的推广量，现在假以为为 0
             int spreadNum = 0;
            ShopExtend shopExtend = new ShopExtend();
            shopExtend.setShop(shop);
            shopExtend.setSpreadNum(spreadNum);
            shopExtends.add(shopExtend);
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(shopExtends, JsonResultUtils.Code.SUCCESS);
    }
    // 删除分店，对应应该删除分店的二维码信息，同时从微信公众素材中删除海报素材
    @Path("/delete")
    @POST
    public String delete(@Context HttpServletRequest request,@FormParam("openId") String openId) {
        if(openId==null ||openId.trim().equals("")){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能为空!");
        }
        long agentId = userService.getCurrentAgentId(request);
        if(agentId==0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
        }
        boolean deleted = delete( openId,agentId);
        if (deleted){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "delete success!");
        }else{
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "delete fail!");
        }
    }
    private boolean delete( String openId,long agentId){
        Shop shop = new Shop();
        shop.setOpenId(openId);
        int result = shopService.delete(shop);
        if (result>0){
            //删除素材
            Poster poster = posterService.getPosterByOpenId(openId);
            if (poster != null){
                posterService.deleteByOpenId(openId);
                //根据agentId拿appId和appSecret
                AgentInfo agentInfo = agentInfoService.getAgentInfoByAgentId(agentId);
                if (agentInfo!=null){    //删除图片素材，避免占用多余的素材空间
                    AccessToken accessToken = weixinService.getAccessToken(agentInfo.getAppID(),agentInfo.getAppSecret());
                    weixinService.del_material(poster.getMedia_id(),accessToken.getToken());
                }
            }
            return true;
        }else  return false;
    }
    @Path("/batchDelete")
    @POST
    public String batchDelete(@Context HttpServletRequest request,@FormParam("openIds") String openIds) {
        if(openIds==null ||openIds.trim().equals("")){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能为空!");
        }
        long agentId = userService.getCurrentAgentId(request);
        if(agentId==0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
        }
        try {
           String[] strings=  openIds.split(",");
           for (String str : strings){
               delete(str,agentId);
           }
        } catch (Exception e){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不合法!");
        }
        return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "delete success!");
    }
}
