package org.kmd.platform.business.taojinbao.mapper;

import org.kmd.platform.business.taojinbao.entity.Poster;
import org.kmd.platform.fundamental.orm.mapper.AbstractMapper;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-29
 * Time: 上午10:18
 * To change this template use File | Settings | File Templates.
 */
public interface PosterMapper extends AbstractMapper<Poster> {
    public Poster getPosterByOpenId(String openId);
    public int deleteByOpenId(String openId);
}
