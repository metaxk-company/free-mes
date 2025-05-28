package io.metaxk.module.mes.dal.mysql.qc;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.qc.vo.ReceiveRecordQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.ProcessRecord;
import io.metaxk.module.mes.dal.dataobject.qc.ReceiveRecord;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author 万界星空
 * @time 2023/7/26 14:59
 */
@Mapper
public interface ReceiveRecordMapper extends BaseMapperX<ReceiveRecord> {

    default PageResult<ReceiveRecord> findPage(ReceiveRecordQueryVo queryVo){
        LambdaQueryWrapperX<ReceiveRecord> queryWrapperX = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotBlank(queryVo.getRecordCode())){
            queryWrapperX.eq(ReceiveRecord::getRecordCode,queryVo.getRecordCode());
        }
        if(StringUtils.isNotBlank(queryVo.getRecNumber())){
            queryWrapperX.eq(ReceiveRecord::getRecNumber,queryVo.getRecNumber());
        }
        if(StringUtils.isNotBlank(queryVo.getInspectUser())){
            queryWrapperX.eq(ReceiveRecord::getInspectUser,queryVo.getInspectUser());
        }

        if(StringUtils.isBlank(queryVo.getRecordCode()) &&  StringUtils.isBlank(queryVo.getRecNumber()) && StringUtils.isBlank(queryVo.getInspectUser())
                && StringUtils.isBlank(queryVo.getItemCode()) && StringUtils.isBlank(queryVo.getModel()) && StringUtils.isBlank(queryVo.getSpec())){
            queryWrapperX.isNotNull(ReceiveRecord::getId);
        }
        queryWrapperX.orderByAsc(ReceiveRecord::getStatus);
        return  selectPage(queryVo,queryWrapperX);
    }
}
