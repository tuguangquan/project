package org.kmd.platform.business.taojinbao.entity;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-29
 * Time: 上午10:16
 * To change this template use File | Settings | File Templates.
 */
public class Poster {
    private long id;
    private String  media_id;
    private long   agentId;
    private String  weiXinOriginId;
    private int type;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public long getAgentId() {
        return agentId;
    }

    public void setAgentId(long agentId) {
        this.agentId = agentId;
    }

    public String getWeiXinOriginId() {
        return weiXinOriginId;
    }

    public void setWeiXinOriginId(String weiXinOriginId) {
        this.weiXinOriginId = weiXinOriginId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
