package org.kmd.platform.business.taojinbao.web;

import org.kmd.platform.business.taojinbao.entity.Goods;
import org.kmd.platform.business.taojinbao.entity.GoodsSelect;
import org.kmd.platform.business.taojinbao.service.GoodsSelectService;
import org.kmd.platform.business.taojinbao.service.GoodsService;
import org.kmd.platform.business.user.service.UserService;
import org.kmd.platform.fundamental.logger.PlatformLogger;
import org.kmd.platform.fundamental.util.json.JsonResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
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
    @Autowired
    private GoodsSelectService goodsSelectService;
    @Autowired
    private UserService userService;

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/findByCondition")
    @POST
    public String findByCondition(@Context HttpServletRequest request,@FormParam("cateId") String cateId,@FormParam("topRate") String topRate,@FormParam("lowerRate") String lowerRate,
                                  @FormParam("topPrice") String topPrice,@FormParam("lowerPrice") String lowerPrice,@FormParam("goodsName") String goodsName,@FormParam("pageSize") String pageSize,@FormParam("pageIndex") String pageIndex){
        int index=0;
        int size = 10;
        if (pageSize!=null && !pageSize.trim().equals("")){
            try{
                size = Integer.parseInt(pageSize);
            }catch (Exception e){
                return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不合法!");
            }

        }
        if (pageIndex!=null && !pageIndex.trim().equals("")){
            try{
                index = Integer.parseInt(pageIndex)*size;
            }catch (Exception e){
                return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不合法!");
            }

        }
        List<Goods> goodsList = goodsService.findByCondition(topRate,lowerRate,topPrice,lowerPrice,goodsName,index,size);
        return JsonResultUtils.getObjectResultByStringAsDefault(goodsList, JsonResultUtils.Code.SUCCESS);
    }

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/goodsSelect")
    @POST
    public String goodsSelect(@Context HttpServletRequest request,@FormParam("cateId") String cateId,@FormParam("goodsId") String goodsId){
        if(cateId==null ||cateId.trim().equals("")||goodsId==null ||goodsId.trim().equals("")){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能为空!");
        }
        long agentId = userService.getCurrentAgentId(request);
        long userId = userService.getCurrentUserId(request);
        if(agentId==0|| userId==0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
        }
        GoodsSelect goodsSelect = new GoodsSelect();
        goodsSelect.setUserId(userId);
        goodsSelect.setAgentId(agentId);
        goodsSelect.setCate_id(Integer.parseInt(cateId));
        goodsSelect.setGoods_id(goodsId);
        goodsSelectService.add(goodsSelect);
        return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "添加成功!");
    }

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/goodsRemove")
    @POST
    public String goodsRemove(@Context HttpServletRequest request,@FormParam("goodsId") String goodsId){
        long agentId = userService.getCurrentAgentId(request);
        long userId = userService.getCurrentUserId(request);
        if(agentId==0|| userId==0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
        }
        GoodsSelect goodsSelect = new GoodsSelect();
        goodsSelect.setGoods_id(goodsId);
        goodsSelect.setAgentId(agentId);
        goodsSelect.setUserId(userId);
        int deleteId = goodsSelectService.delete(goodsSelect);
        if (deleteId>0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "移除选品库成功!");
        }
        return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "当前网络不稳定，请稍后再试!");
    }

    public static void main(String[] args) {

    }
}
