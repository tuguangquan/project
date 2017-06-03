package org.kmd.platform.business.taojinbao.mapper;

import org.kmd.platform.business.taojinbao.entity.Category;
import org.kmd.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;

/**
 * Created by Administrator on 2017/6/3.
 */
public interface CategoryMapper extends AbstractMapper<Category> {
    public List<Category> getByAgentId(long agentId);
}
