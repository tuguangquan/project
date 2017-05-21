package org.kmd.platform.business.taojinbao.entity;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-17
 * Time: 下午9:23
 * To change this template use File | Settings | File Templates.
 */
public class AgentInfo {
    private int id    ;
    private String weixinId;
    private String weixinOriginalId;
    private String appID;
    private String appSecret;
    private long agentId;
    private long agentName;
    private int fansAddNum;
    private int fansCountNum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeixinId() {
        return weixinId;
    }

    public void setWeixinId(String weixinId) {
        this.weixinId = weixinId;
    }

    public String getWeixinOriginalId() {
        return weixinOriginalId;
    }

    public void setWeixinOriginalId(String weixinOriginalId) {
        this.weixinOriginalId = weixinOriginalId;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public long getAgentId() {
        return agentId;
    }

    public void setAgentId(long agentId) {
        this.agentId = agentId;
    }

    public int getFansAddNum() {
        return fansAddNum;
    }

    public void setFansAddNum(int fansAddNum) {
        this.fansAddNum = fansAddNum;
    }

    public int getFansCountNum() {
        return fansCountNum;
    }

    public void setFansCountNum(int fansCountNum) {
        this.fansCountNum = fansCountNum;
    }

    public long getAgentName() {
        return agentName;
    }

    public void setAgentName(long agentName) {
        this.agentName = agentName;
    }
}
