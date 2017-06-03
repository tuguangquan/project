package org.kmd.platform.business.taojinbao.entity;

/**
 * Created by Administrator on 2017/6/3.
 */
public class AgentGoodsRule {
    private long id;
    private long agentId;
    private int taobao;
    private int jd;
    private int wx;
    private int netease;
    private int lowRate;
    private int topRate;
    private String cateId;

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

    public int getTaobao() {
        return taobao;
    }

    public void setTaobao(int taobao) {
        this.taobao = taobao;
    }

    public int getJd() {
        return jd;
    }

    public void setJd(int jd) {
        this.jd = jd;
    }

    public int getWx() {
        return wx;
    }

    public void setWx(int wx) {
        this.wx = wx;
    }

    public int getNetease() {
        return netease;
    }

    public void setNetease(int netease) {
        this.netease = netease;
    }

    public int getLowRate() {
        return lowRate;
    }

    public void setLowRate(int lowRate) {
        this.lowRate = lowRate;
    }

    public int getTopRate() {
        return topRate;
    }

    public void setTopRate(int topRate) {
        this.topRate = topRate;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }
}
