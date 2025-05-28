package io.metaxk.module.mes.dal.mysql.order;

import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.module.mes.dal.dataobject.order.InboundItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 采购入库子类Mapper
 *
 * @author 万界星空MES
 */
@Mapper
public interface InboundItemMapper extends BaseMapperX<InboundItem> {
}
