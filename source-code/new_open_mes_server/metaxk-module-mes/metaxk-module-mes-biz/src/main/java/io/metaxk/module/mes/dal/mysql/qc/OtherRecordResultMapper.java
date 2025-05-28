package io.metaxk.module.mes.dal.mysql.qc;

import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.qc.vo.OtherStandardResultVo;
import io.metaxk.module.mes.dal.dataobject.qc.OtherRecordResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author 万界星空
 * @time 2023/8/19 14:28
 */
@Mapper
public interface OtherRecordResultMapper extends BaseMapperX<OtherRecordResult> {

    default OtherRecordResult selectOtherRecordResultByNumber(String recordNumber){
        LambdaQueryWrapperX<OtherRecordResult> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OtherRecordResult::getRecordNumber, recordNumber);
        return selectOne(queryWrapperX);
    }

    public OtherStandardResultVo getAcount(OtherStandardResultVo otherStandardResultVo);
}
