package org.kmd.platform.business.taojinbao.mapper;

import org.kmd.platform.business.taojinbao.entity.MsgSub;
import org.kmd.platform.fundamental.orm.mapper.AbstractMapper;

/**
 * Created by Administrator on 2017/5/26 0026.
 */
public interface MsgSubMapper extends AbstractMapper<MsgSub> {
    public MsgSub getMsgSubByWeiXinOriginId(String weiXinOriginId);
}
