package org.kmd.platform.business.taojinbao.weixin.QRCode;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-23
 * Time: 下午8:56
 * To change this template use File | Settings | File Templates.
 */
public class QRCode {
    private String action_name;
    private ActionInfo action_info;

    public String getAction_name() {
        return action_name;
    }

    public void setAction_name(String action_name) {
        this.action_name = action_name;
    }

    public ActionInfo getAction_info() {
        return action_info;
    }

    public void setAction_info(ActionInfo action_info) {
        this.action_info = action_info;
    }
}
