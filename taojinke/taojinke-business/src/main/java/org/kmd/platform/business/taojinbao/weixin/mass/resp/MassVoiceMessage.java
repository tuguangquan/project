package org.kmd.platform.business.taojinbao.weixin.mass.resp;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-18
 * Time: 下午9:31
 * To change this template use File | Settings | File Templates.
 */
public class MassVoiceMessage extends MassBaseMessage{
    private Voice voice;

    public Voice getVoice() {
        return voice;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }
}
