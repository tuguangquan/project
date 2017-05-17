package org.kmd.platform.business.user.mapper;

import org.kmd.platform.business.user.entity.Authority;
import org.kmd.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;
import java.util.Map;
/**
 * Created with IntelliJ IDEA.
 * User: tuguangquan
 * Date: 14-3-16
 * Time: 下午8:14
 * To change this template use File | Settings | File Templates.
 */
public interface AuthorityMapper extends AbstractMapper<Authority> {
    public List<Authority> findByCondition(Map<String,Object> map);
    public long getIdByName(String name);
    public String getNameById(long id);
}
