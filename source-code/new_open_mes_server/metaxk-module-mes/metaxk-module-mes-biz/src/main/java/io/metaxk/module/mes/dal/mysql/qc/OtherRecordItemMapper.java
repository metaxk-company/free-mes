package io.metaxk.module.mes.dal.mysql.qc;

import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.dal.dataobject.qc.OtherRecord;
import io.metaxk.module.mes.dal.dataobject.qc.OtherRecordItem;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author 万界星空
 * @time 2023/8/19 10:28
 */
@Mapper
public interface OtherRecordItemMapper extends BaseMapperX<OtherRecordItem> {

    default List<OtherRecordItem> findOtherRecordItem(String recordNumber){
        LambdaQueryWrapperX<OtherRecordItem> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eq(OtherRecordItem::getRecordNumber, recordNumber);
        return selectList(lambdaQueryWrapperX);
    }
}
