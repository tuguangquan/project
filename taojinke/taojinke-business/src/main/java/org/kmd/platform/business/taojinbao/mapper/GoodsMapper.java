package org.kmd.platform.business.taojinbao.mapper;

import org.kmd.platform.business.taojinbao.entity.Goods;
import org.kmd.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-17
 * Time: 下午8:13
 * To change this template use File | Settings | File Templates.
 */
public interface GoodsMapper extends AbstractMapper<Goods> {
    public List<Goods> findByCondition(Map<String,Object> map);
}