package org.kmd.platform.business.taojinbao.dto;

import org.kmd.platform.business.taojinbao.entity.Shop;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-30
 * Time: 下午2:03
 * To change this template use File | Settings | File Templates.
 */
public class ShopExtend   {
    private Shop shop;
    private int spreadNum;

    public int getSpreadNum() {
        return spreadNum;
    }

    public void setSpreadNum(int spreadNum) {
        this.spreadNum = spreadNum;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
