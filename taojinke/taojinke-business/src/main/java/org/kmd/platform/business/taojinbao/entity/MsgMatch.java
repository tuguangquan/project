package org.kmd.platform.business.taojinbao.entity;

/**
 * Created by Administrator on 2017/5/26 0026.
 */
public class MsgMatch {
    private long id;
    private String modeContent;
    private String msgMatch;
    private int priority;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModeContent() {
        return modeContent;
    }

    public void setModeContent(String modeContent) {
        this.modeContent = modeContent;
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
