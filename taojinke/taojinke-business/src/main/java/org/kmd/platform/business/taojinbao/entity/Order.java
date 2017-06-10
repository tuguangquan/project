package org.kmd.platform.business.taojinbao.entity;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-6-10
 * Time: 下午1:57
 * To change this template use File | Settings | File Templates.
 */
public class Order {
    private  long id;
    private int uniacid;
    private int status;
    private int shopid;
    private int type;
    private String itemtitle;
    private String itemid;
    private String ctime;
    private String orderid;
    private String etime;
    private String orderstatus;
    private float orderfee;
    private float shouru_bili;
    private float shouru_yongjin;
    private String pid2;
    private String pid3;
    private int isjs;
    private int isnice;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getUniacid() {
        return uniacid;
    }

    public void setUniacid(int uniacid) {
        this.uniacid = uniacid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getItemtitle() {
        return itemtitle;
    }

    public void setItemtitle(String itemtitle) {
        this.itemtitle = itemtitle;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public float getOrderfee() {
        return orderfee;
    }

    public void setOrderfee(float orderfee) {
        this.orderfee = orderfee;
    }

    public float getShouru_bili() {
        return shouru_bili;
    }

    public void setShouru_bili(float shouru_bili) {
        this.shouru_bili = shouru_bili;
    }

    public float getShouru_yongjin() {
        return shouru_yongjin;
    }

    public void setShouru_yongjin(float shouru_yongjin) {
        this.shouru_yongjin = shouru_yongjin;
    }

    public String getPid2() {
        return pid2;
    }

    public void setPid2(String pid2) {
        this.pid2 = pid2;
    }

    public String getPid3() {
        return pid3;
    }

    public void setPid3(String pid3) {
        this.pid3 = pid3;
    }

    public int getIsjs() {
        return isjs;
    }

    public void setIsjs(int isjs) {
        this.isjs = isjs;
    }

    public int getIsnice() {
        return isnice;
    }

    public void setIsnice(int isnice) {
        this.isnice = isnice;
    }
}
