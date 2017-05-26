package org.kmd.platform.business.taojinbao.entity;

/**
 * Created by Administrator on 2017/5/25 0025.
 */
public class MsgTemp {
    private long id;
    private long agentId;
    private String msgType;
    private int actionType;
    private String modeContent;
    private String pushMode;
    private String weiXinOriginId;

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

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public String getModeContent() {
        return modeContent;
    }

    public void setModeContent(String modeContent) {
        this.modeContent = modeContent;
    }

    public String getPushMode() {
        return pushMode;
    }

    public void setPushMode(String pushMode) {
        this.pushMode = pushMode;
    }

    public String getWeiXinOriginId() {
        return weiXinOriginId;
    }

    public void setWeiXinOriginId(String weiXinOriginId) {
        this.weiXinOriginId = weiXinOriginId;
    }
}
