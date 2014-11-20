package org.whut.seis.business.mapper;

import org.whut.seis.business.entity.AuthorityPower;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiaozhujun
 * Date: 14-3-16
 * Time: 下午11:01
 * To change this template use File | Settings | File Templates.
 */
public interface AuthorityPowerMapper extends AbstractMapper<AuthorityPower> {
    public List<AuthorityPower> getAuthorityPowerList();
    public int deleteByAuthorityName(String name);
    public List<String> getResourcesByAuthorityName(String name);
    public int deleteByPowerResource(String powerResource);
}
