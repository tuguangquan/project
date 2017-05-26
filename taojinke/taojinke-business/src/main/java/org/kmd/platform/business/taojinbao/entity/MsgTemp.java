package org.kmd.platform.business.taojinbao.entity;

/**
 * Created by Administrator on 2017/5/25 0025.
 */
public class MsgTemp {
    private long id;
    private long agentId;
    private String msgType;//文本、图片、声音...
    private String modeContent;
    private String msgMatch;
    private String weiXinOriginId;
    private int priority;

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

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getModeContent() {
        return modeContent;
    }

    public void setModeContent(String modeContent) {
        this.modeContent = modeContent;
    }

    public String getWeiXinOriginId() {
        return weiXinOriginId;
    }

    public void setWeiXinOriginId(String weiXinOriginId) {
        this.weiXinOriginId = weiXinOriginId;
    }

    public String getMsgMatch() {
        return msgMatch;
    }

    public void setMsgMatch(String msgMatch) {
        this.msgMatch = msgMatch;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
