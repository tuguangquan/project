package org.kmd.platform.business.taojinbao.weixin.mass.resp;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-18
 * Time: 下午6:28
 * To change this template use File | Settings | File Templates.
 */
public class MassTextMessage extends MassBaseMessage{
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toJSON() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        buffer.append("\"touser\":[");
        String openId;
        for (int i = 0; i < this.getTouser().size(); i++) {
            openId = this.getTouser().get(i);
            // 判断是否追加逗号
            if (i < this.getTouser().size() - 1){
                buffer.append(String.format("\"%s\",", openId));
            }else{
                buffer.append(String.format("\"%s\"",openId));
            }
        }
        buffer.append("]").append(",");
        buffer.append("\"msgtype\":\"").append(this.getMsgtype()).append("\",");
        buffer.append("\"text\":{");
        buffer.append("\"content\":\"").append(this.getContent()).append("\"}");
        buffer.append("}");
        return buffer.toString();
    }
}
