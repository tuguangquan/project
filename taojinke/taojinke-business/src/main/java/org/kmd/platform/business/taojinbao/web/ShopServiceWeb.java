package org.kmd.platform.business.taojinbao.web;

import org.kmd.platform.business.taojinbao.entity.Shop;
import org.kmd.platform.business.taojinbao.service.ShopService;
import org.kmd.platform.business.taojinbao.util.AccessToken;
import org.kmd.platform.business.taojinbao.util.ShopEunm;
import org.kmd.platform.fundamental.logger.PlatformLogger;
import org.kmd.platform.fundamental.util.json.JsonResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

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
    final String STATUS="启用";
    final String role = "ROLE_ADMIN";
    @Autowired
    private ShopService shopService;

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
    public String audit(@FormParam("openId") String openId,@FormParam("pid") String pid) {
        if(pid==null ||pid.trim().equals("")){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能为空!");
        }
        Shop shop = new Shop();
        shop.setOpenId(openId);
        shop.setPid(pid);
        shop.setStatus(ShopEunm.RESP_STATUS_AUDITED);//审核通过
        shopService.update(shop);
        return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "审核通过");
    }
}
