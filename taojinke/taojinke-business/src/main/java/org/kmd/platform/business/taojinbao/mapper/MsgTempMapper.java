package org.kmd.platform.business.taojinbao.mapper;

import org.kmd.platform.business.taojinbao.entity.MsgTemp;
import org.kmd.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/25 0025.
 */
public interface MsgTempMapper extends AbstractMapper<MsgTemp> {
    public List<MsgTemp> getMsgTempByOriginId(String originId);
    public List<MsgTemp> getMsgTempByOriginIdAndModeMatch(Map<String,Object> map);

}
