package org.kmd.platform.business.taojinbao.service;

import org.kmd.platform.business.taojinbao.entity.Category;
import org.kmd.platform.business.taojinbao.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2017/6/3.
 */
public class CategoryService {

    @Autowired
    private CategoryMapper mapper;

    public void add(Category category){
        mapper.add(category);
    }

    public int update(Category category){
        return mapper.update(category);
    }

    public int delete(Category category){
        return mapper.delete(category);
    }
    public List<Category> getByAgentId(long agentId){
        return mapper.getByAgentId(agentId);
    }
}
