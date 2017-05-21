package org.kmd.platform.business.taojinbao.weixin.req;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-18
 * Time: 下午6:36
 * To change this template use File | Settings | File Templates.
 */
public class VoiceMessage  extends BaseMessage{
    // 媒体ID
        private String MediaId;
        // 语音格式
            private String Format;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }
}
