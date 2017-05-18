package org.kmd.platform.business.taojinbao.weixin.mass.resp;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-18
 * Time: 下午9:22
 * To change this template use File | Settings | File Templates.
 */
public class MassBaseMessage {
    // 接收方帐号（收到的OpenID）
    private List<String> touser;

    private String msgtype;

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public List<String> getTouser() {
        return touser;
    }

    public void setTouser(List<String> touser) {
        this.touser = touser;
    }
}
