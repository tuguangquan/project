package org.kmd.platform.business.taojinbao.web;

import org.kmd.platform.business.taojinbao.entity.AgentGoodsRule;
import org.kmd.platform.business.taojinbao.entity.Category;
import org.kmd.platform.business.taojinbao.service.AgentGoodsRuleService;
import org.kmd.platform.business.taojinbao.service.CategoryService;
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
import java.util.List;

/**
 * Created by Administrator on 2017/6/3.
 */
@Component
@Path("/rule")
public class AgentGoodsRuleServiceWeb {
    private static PlatformLogger logger = PlatformLogger.getLogger(GoodsServiceWeb.class);

    @Autowired
    private AgentGoodsRuleService agentGoodsRuleService;
    @Autowired
    private UserService userService;

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/add")
    @POST
    public String add(@Context HttpServletRequest request,@FormParam("taobao") String taobao,@FormParam("jd") String jd,@FormParam("wx") String wx,
                      @FormParam("netease") String netease,@FormParam("lowRate") String lowRate,@FormParam("topRate") String topRate,@FormParam("cateId") String cateId){
        long agentId = userService.getCurrentAgentId(request);
        if(agentId==0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
        }
        AgentGoodsRule agentGoodsRule = new AgentGoodsRule();
        try {
            agentGoodsRule.setTaobao(Integer.parseInt(taobao));
            agentGoodsRule.setAgentId(agentId);
            agentGoodsRule.setJd(Integer.parseInt(jd));
            agentGoodsRule.setLowRate(Integer.parseInt(lowRate));
            agentGoodsRule.setTopRate(Integer.parseInt(topRate));
            agentGoodsRule.setNetease(Integer.parseInt(netease));
            agentGoodsRule.setWx(Integer.parseInt(wx));
            agentGoodsRule.setCateId(cateId);
            agentGoodsRuleService.add(agentGoodsRule);
        }catch (Exception e){
            logger.error("提供的参数不合法",e);
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不合法!");
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(null, JsonResultUtils.Code.SUCCESS);
    }

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/update")
    @POST
    public String update(@Context HttpServletRequest request,@FormParam("id") String id,@FormParam("taobao") String taobao,@FormParam("jd") String jd,@FormParam("wx") String wx,
                         @FormParam("netease") String netease,@FormParam("lowRate") String lowRate,@FormParam("topRate") String topRate,@FormParam("cateId") String cateId){
        long agentId = userService.getCurrentAgentId(request);
        if(agentId==0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
        }
        AgentGoodsRule agentGoodsRule = new AgentGoodsRule();
        try {
            agentGoodsRule.setId(Long.parseLong(id));
            agentGoodsRule.setAgentId(agentId);
            if (taobao!=null && !taobao.trim().equals("")){
                agentGoodsRule.setTaobao(Integer.parseInt(taobao));
            }
            if (jd!=null && !jd.trim().equals("")){
                agentGoodsRule.setJd(Integer.parseInt(jd));
            }
            if (wx!=null && !wx.trim().equals("")){
                agentGoodsRule.setWx(Integer.parseInt(wx));
            }
            if (netease!=null && !netease.trim().equals("")){
                agentGoodsRule.setNetease(Integer.parseInt(netease));
            }
            if (lowRate!=null && !lowRate.trim().equals("")){
                agentGoodsRule.setLowRate(Integer.parseInt(lowRate));
            }
            if (topRate!=null && !topRate.trim().equals("")){
                agentGoodsRule.setTopRate(Integer.parseInt(topRate));
            }
            if (cateId!=null && !cateId.trim().equals("")){
                agentGoodsRule.setCateId(cateId);
            }
        }catch (Exception e){
            logger.error("提供的参数不合法",e);
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不合法!");
        }
        agentGoodsRuleService.update(agentGoodsRule);
        return JsonResultUtils.getObjectResultByStringAsDefault(null, JsonResultUtils.Code.SUCCESS);
    }

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/get")
    @POST
    public String get(@Context HttpServletRequest request){
        long agentId = userService.getCurrentAgentId(request);
        if(agentId==0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "请重新登录!");
        }
        AgentGoodsRule agentGoodsRule = agentGoodsRuleService.getByAgentId(agentId);
        return JsonResultUtils.getObjectResultByStringAsDefault(agentGoodsRule, JsonResultUtils.Code.SUCCESS);
    }


    }
