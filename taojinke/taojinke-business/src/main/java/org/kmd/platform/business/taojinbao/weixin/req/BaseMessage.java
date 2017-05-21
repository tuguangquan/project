package org.kmd.platform.business.taojinbao.weixin.req;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-18
 * Time: 下午6:31
 * To change this template use File | Settings | File Templates.
 */
public class BaseMessage {
    // 开发者微信号
      private String ToUserName;
        // 发送方帐号（一个OpenID）
               private String FromUserName;
       // 消息创建时间 （整型）
            	    private long CreateTime;
       // 消息类型（text/image/location/link）
                private String MsgType;
        // 消息id，64位整型
                private long MsgId;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public long getMsgId() {
        return MsgId;
    }

    public void setMsgId(long msgId) {
        MsgId = msgId;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }
}
