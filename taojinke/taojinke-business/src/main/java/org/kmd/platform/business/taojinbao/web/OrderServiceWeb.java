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
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
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
@Path("/order")
public class OrderServiceWeb {
    private static PlatformLogger logger = PlatformLogger.getLogger(OrderServiceWeb.class);

    @Autowired
    private ShopService shopService;


    @Autowired
    private OrderService orderService;

    @Path("/getOrder")
    @POST
    public String getOrder(@FormParam("openId") String openId) {
        if(openId==null ||openId.trim().equals("")){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能为空!");
        }
        Shop shop = shopService.getByOpenId(openId);
        if (shop == null){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "还没有开分店!");
        }
        String pid = shop.getPid();
        if (pid == null|| pid.equals("")){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "分店审核未通过!");
        }
        // 根据pid去获取订单信息
        List<Order> orderList = orderService.getShopByPid3(pid);
        return JsonResultUtils.getObjectResultByStringAsDefault(orderList, JsonResultUtils.Code.SUCCESS);
    }

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/getAppGoodsByCondition")
    @POST
    public String getAppGoodsByCondition(@FormParam("orderStatus") String orderStatus){
        if(orderStatus==null ||orderStatus.trim().equals("")){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能为空!");
        }
        List<Order> goodsList = orderService.getByOrderStatus(orderStatus);
        return JsonResultUtils.getObjectResultByStringAsDefault(goodsList, JsonResultUtils.Code.SUCCESS);
    }
}
