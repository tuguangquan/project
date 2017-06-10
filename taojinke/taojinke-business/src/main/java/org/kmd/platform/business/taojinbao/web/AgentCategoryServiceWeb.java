package org.kmd.platform.business.taojinbao.web;

import org.kmd.platform.business.taojinbao.entity.AgentCategory;
import org.kmd.platform.business.taojinbao.service.AgentCategoryService;
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
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-6-10
 * Time: 上午11:42
 * To change this template use File | Settings | File Templates.
 */
@Component
@Path("/agentCategory")
public class AgentCategoryServiceWeb {

    private static PlatformLogger logger = PlatformLogger.getLogger(GoodsServiceWeb.class);
    @Autowired
    private AgentCategoryService agentCategoryService;
    @Autowired
    private UserService userService;

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/get")
    @POST
    public String getAgentCategory(@Context HttpServletRequest request) {
        long agentId = userService.getCurrentAgentId(request);
        List<AgentCategory> agentCategoryList = agentCategoryService.getByAgentId(agentId);
        return JsonResultUtils.getObjectResultByStringAsDefault(agentCategoryList, JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/add")
    @POST
    public String addAgentCategory(@Context HttpServletRequest request) {
        long agentId = userService.getCurrentAgentId(request);
        List<AgentCategory> agentCategoryList = agentCategoryService.getByAgentId(agentId);
        return JsonResultUtils.getObjectResultByStringAsDefault(agentCategoryList, JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/getByAgentId")
    @POST
    public String getByAgentId( @FormParam("agentId") String agentId) {
        List<AgentCategory> agentCategoryList = null;
        if(agentId==null ||agentId.trim().equals("")){
            try {
                Long aid = Long.parseLong(agentId);
                agentCategoryList = agentCategoryService.getByAgentId(aid);
            } catch (Exception e){
                return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能为空!");
            }
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(agentCategoryList, JsonResultUtils.Code.SUCCESS);
    }
}
