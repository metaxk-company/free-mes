package io.metaxk.module.mes.dal.mysql.qc;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.qc.vo.OtherRecordQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.OtherRecord;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 万界星空
 * @time 2023/8/19 10:43
 */
@Mapper
public interface OtherRecordMapper extends BaseMapperX<OtherRecord> {

    default PageResult<OtherRecord> findPage(OtherRecordQueryVo reqVO){

        LambdaQueryWrapperX<OtherRecord> queryWrapperX = new LambdaQueryWrapperX<>();

        if(StringUtils.isNotBlank(reqVO.getNumber())) {
            queryWrapperX.eq(OtherRecord::getNumber, reqVO.getNumber());
        }
        if(StringUtils.isNotBlank(reqVO.getWorkOrderCode())) {
            queryWrapperX.eq(OtherRecord::getWorkOrderCode, reqVO.getWorkOrderCode());
        }
        if(StringUtils.isNotBlank(reqVO.getModel())) {
            queryWrapperX.eq(OtherRecord::getModel, reqVO.getModel());
        }
        if(StringUtils.isNotBlank(reqVO.getSpec())) {
            queryWrapperX.eq(OtherRecord::getSpec, reqVO.getSpec());
        }
        if(StringUtils.isNotBlank(reqVO.getLineType())) {
            queryWrapperX.eq(OtherRecord::getLineType, reqVO.getLineType());
        }
        if(StringUtils.isBlank(reqVO.getNumber()) &&  StringUtils.isBlank(reqVO.getModel())
                && StringUtils.isBlank(reqVO.getSpec()) && StringUtils.isBlank(reqVO.getLineType())){
            queryWrapperX.isNotNull(OtherRecord::getId);
        }
        return  selectPage(reqVO,queryWrapperX);
    }
}
