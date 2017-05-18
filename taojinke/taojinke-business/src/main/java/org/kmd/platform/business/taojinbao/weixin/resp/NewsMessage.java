package org.kmd.platform.business.taojinbao.weixin.resp;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-18
 * Time: 下午6:39
 * To change this template use File | Settings | File Templates.
 */
public class NewsMessage extends BaseMessage{
    // 图文消息个数，限制为10条以内
   private int ArticleCount;
     // 多条图文消息信息，默认第一个item为大图
     private List<Article> Articles;

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<Article> getArticles() {
        return Articles;
    }

    public void setArticles(List<Article> articles) {
        Articles = articles;
    }
}
