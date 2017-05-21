package org.kmd.platform.business.taojinbao.weixin.req;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-18
 * Time: 下午6:31
 * To change this template use File | Settings | File Templates.
 */
public class TextMessageReq extends ReqBaseMessage {
    // 消息内容
	private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
