package org.whut.seis.business.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.seis.business.service.AuthorityPowerService;
import org.whut.seis.business.service.PowerService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiaozhujun
 * Date: 14-3-23
 * Time: 下午6:22
 * To change this template use File | Settings | File Templates.
 */
@Component
@Path("/power")
public class PowerServiceWeb {

    @Autowired
    private PowerService powerService;

    @Autowired
    private AuthorityPowerService authorityPowerService;

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/add")
    @POST
    public String add(@FormParam("resource") String resource,@FormParam("type") String type,@FormParam("description") String description){
        return "";
    }

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/list")
    @GET
    public String list(){
        return "";
    }

    @Produces( MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/update")
    @POST
    public String update(@FormParam("jsonString") String jsonString){
        return "";
    }

    @Produces( MediaType.APPLICATION_JSON+ ";charset=UTF-8")
    @Path("/delete")
    @POST
    public String delete(@FormParam("jsonString") String jsonString){
        return "";
}
}

