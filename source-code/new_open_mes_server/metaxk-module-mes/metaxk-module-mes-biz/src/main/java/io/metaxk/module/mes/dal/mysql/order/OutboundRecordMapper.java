package io.metaxk.module.mes.dal.mysql.order;

import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.module.mes.dal.dataobject.order.OutboundRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 万界星空
 * @time 2023/7/19 15:15
 */
@Mapper
public interface OutboundRecordMapper extends BaseMapperX<OutboundRecord> {
}
