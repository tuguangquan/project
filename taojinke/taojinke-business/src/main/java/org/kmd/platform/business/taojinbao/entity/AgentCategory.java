package org.kmd.platform.business.taojinbao.entity;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-6-10
 * Time: 上午11:27
 * To change this template use File | Settings | File Templates.
 */
public class AgentCategory {
    private long id;
    private long agentId;
    private long categoryId;

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

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
