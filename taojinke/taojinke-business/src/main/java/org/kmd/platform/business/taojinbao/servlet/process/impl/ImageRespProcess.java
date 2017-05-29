package org.kmd.platform.business.taojinbao.servlet.process.impl;

import org.kmd.platform.business.taojinbao.servlet.process.RespProcess;
import org.kmd.platform.business.taojinbao.util.MessageUtil;
import org.kmd.platform.business.taojinbao.weixin.resp.Image;
import org.kmd.platform.business.taojinbao.weixin.resp.ImageMessageResp;
import org.kmd.platform.business.taojinbao.weixin.resp.TextMessageResp;

import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/23 0023.
 */
public class ImageRespProcess implements RespProcess {

    @Override
    public String getRespMessage(Map<String, String> requestMap) {
        // 发送方帐号（open_id）
        String fromUserName = requestMap.get("FromUserName");
        // 公众帐号
        String toUserName = requestMap.get("ToUserName");
        // 消息内容
        String media_id = requestMap.get("Media_id");
        // 回复文本消息fromUserName
        ImageMessageResp  imageMessageResp = new ImageMessageResp();
        imageMessageResp.setToUserName(fromUserName);
        imageMessageResp.setFromUserName(toUserName);
        imageMessageResp.setCreateTime(new Date().getTime());
        imageMessageResp.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_IMAGE);
        Image image = new Image();
        image.setMediaId(media_id);
        //todo 根据msg在数据库中匹配回复消息，如果没有匹配上，则回复默认消息
        imageMessageResp.setImage(image);
        return MessageUtil.imageMessageToXml(imageMessageResp);
    }
}
