package io.metaxk.module.mes.dal.mysql.order;

import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.module.mes.dal.dataobject.order.ProductPickItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 万界星空
 * @time 2023/7/21 14:22
 */
@Mapper
public interface ProductPickItemMapper extends BaseMapperX<ProductPickItem> {
}
