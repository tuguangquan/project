package org.kmd.platform.business.taojinbao.weixin.req;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-18
 * Time: 下午6:35
 * To change this template use File | Settings | File Templates.
 */
public class LinkMessageReq extends ReqBaseMessage {
    // 消息标题
  	    private String Title;
    	    // 消息描述
              private String Description;
        // 消息链接
          	    private String Url;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
