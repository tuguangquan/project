package org.kmd.platform.business.taojinbao.weixin.req;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-18
 * Time: 下午6:33
 * To change this template use File | Settings | File Templates.
 */
public class ImageMessageReq extends ReqBaseMessage {
    // 图片链接
   	    private String PicUrl;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }
}
