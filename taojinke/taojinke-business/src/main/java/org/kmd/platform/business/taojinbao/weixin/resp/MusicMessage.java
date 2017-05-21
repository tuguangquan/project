package org.kmd.platform.business.taojinbao.weixin.resp;

/**
 * Created by Administrator on 2017/5/18 0018.
 */
public class MusicMessage extends BaseMessage{
    // 音乐
    private Music music;

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }
}
