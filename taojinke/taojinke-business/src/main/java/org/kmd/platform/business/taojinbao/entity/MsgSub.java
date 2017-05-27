package org.kmd.platform.business.taojinbao.entity;

/**
 * Created by Administrator on 2017/5/26 0026.
 */
public class MsgSub {
    private long id;
    private long agentId;
    private String weiXinOriginId ;
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
