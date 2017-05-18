package org.kmd.platform.business.taojinbao.weixin.mass.resp;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-18
 * Time: 下午6:28
 * To change this template use File | Settings | File Templates.
 */
public class MassTextMessage extends MassBaseMessage{
    private Text text;

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }
}
