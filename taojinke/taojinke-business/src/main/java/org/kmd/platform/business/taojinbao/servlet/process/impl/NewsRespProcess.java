package org.kmd.platform.business.taojinbao.servlet.process.impl;

import org.kmd.platform.business.taojinbao.servlet.process.RespProcess;
import org.kmd.platform.business.taojinbao.util.MessageUtil;
import org.kmd.platform.business.taojinbao.weixin.resp.Article;
import org.kmd.platform.business.taojinbao.weixin.resp.NewsMessageResp;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/23 0023.
 */
public class NewsRespProcess implements RespProcess {

    @Override
    public String getRespMessage(Map<String, String> requestMap) {
        // 发送方帐号（open_id）
        String fromUserName = requestMap.get("FromUserName");
        // 公众帐号
        String toUserName = requestMap.get("ToUserName");
        int ArticleCount = 1;
        List<Article> Articles = new ArrayList<Article>();
        Article article = new Article();
        article.setTitle("");
        article.setDescription("");
        article.setPicUrl("");
        article.setUrl("");
        Articles.add(article);
        NewsMessageResp newsMessageResp = new NewsMessageResp();
        newsMessageResp.setFromUserName(fromUserName);
        newsMessageResp.setToUserName(toUserName);
        newsMessageResp.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessageResp.setFuncFlag(0);
        newsMessageResp.setArticleCount(ArticleCount);
        newsMessageResp.setCreateTime(new Date().getTime());
        newsMessageResp.setArticles(Articles);
        return MessageUtil.newsMessageToXml(newsMessageResp);
    }
}
