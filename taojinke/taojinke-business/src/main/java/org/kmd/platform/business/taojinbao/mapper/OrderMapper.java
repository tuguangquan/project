package org.kmd.platform.business.taojinbao.mapper;

import org.kmd.platform.business.taojinbao.entity.Order;
import org.kmd.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-6-10
 * Time: 下午2:00
 * To change this template use File | Settings | File Templates.
 */
public interface OrderMapper extends AbstractMapper<Order> {
    public List<Order> getShopByPid3(String pid);
    public List<Order> getByOrderStatus(String orderStatus);
}
