package org.kmd.platform.business.taojinbao.web;

import org.kmd.platform.business.taojinbao.entity.Goods;
import org.kmd.platform.business.taojinbao.service.GoodsService;
import org.kmd.platform.fundamental.logger.PlatformLogger;
import org.kmd.platform.fundamental.util.json.JsonResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-17
 * Time: 下午8:12
 * To change this template use File | Settings | File Templates.
 */
@Component
@Path("/goods")
public class GoodsServiceWeb {
    
    private static PlatformLogger logger = PlatformLogger.getLogger(GoodsServiceWeb.class);

    @Autowired
    private GoodsService goodsService;

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/findByCondition")
    @POST
    public String findByCondition(@FormParam("cate_id") String cate_id,@FormParam("title") String title,@FormParam("appID") String appID,@FormParam("appSecret") String appSecret,@FormParam("agentId") String agentId,@FormParam("profit") String profit){
        List<Goods> goodsList = goodsService.findByCondition();
        return JsonResultUtils.getObjectResultByStringAsDefault(goodsList, JsonResultUtils.Code.SUCCESS);
    }
}
