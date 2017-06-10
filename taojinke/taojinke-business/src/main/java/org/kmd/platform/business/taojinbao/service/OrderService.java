package org.kmd.platform.business.taojinbao.service;

import org.kmd.platform.business.taojinbao.entity.Order;
import org.kmd.platform.business.taojinbao.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-6-10
 * Time: 下午2:00
 * To change this template use File | Settings | File Templates.
 */
public class OrderService {
    @Autowired
    private OrderMapper mapper;

    public List<Order> getShopByPid3(String pid){
        return mapper.getShopByPid3(pid);
    }
    public List<Order> getByOrderStatus(String orderStatus){
        return mapper.getByOrderStatus(orderStatus);
    }
}
