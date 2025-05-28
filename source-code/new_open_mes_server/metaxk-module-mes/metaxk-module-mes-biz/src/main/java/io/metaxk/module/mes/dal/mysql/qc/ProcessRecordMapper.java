package io.metaxk.module.mes.dal.mysql.qc;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.qc.vo.ProcessRecordQueryVo;
import io.metaxk.module.mes.dal.dataobject.pro.FeedbackHours;
import io.metaxk.module.mes.dal.dataobject.qc.ProcessRecord;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author 万界星空
 * @time 2023/7/10 13:33
 */
@Mapper
public interface ProcessRecordMapper extends BaseMapperX<ProcessRecord> {

    /**
     *  工序检验单条件分页查询
      * @param queryVo
     * @return  PageResult<ProcessRecord>
     */
   default PageResult<ProcessRecord> findPage(ProcessRecordQueryVo queryVo){
       LambdaQueryWrapperX<ProcessRecord> queryWrapperX = new LambdaQueryWrapperX<>();
       if(StringUtils.isNotBlank(queryVo.getRecordCode())){
           queryWrapperX.eq(ProcessRecord::getRecordCode,queryVo.getRecordCode());
       }
       if(StringUtils.isNotBlank(queryVo.getProcessCode())){
           queryWrapperX.eq(ProcessRecord::getProcessCode,queryVo.getProcessCode());
       }
       if(StringUtils.isNotBlank(queryVo.getReportUser())){
           queryWrapperX.eq(ProcessRecord::getReportUser,queryVo.getReportUser());
       }
       if(StringUtils.isNotBlank(queryVo.getOrderDate())){
           LocalDate requestDate = LocalDate.parse(queryVo.getOrderDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
           queryWrapperX.eq(ProcessRecord::getOrderDate, requestDate);
       }
       if(StringUtils.isBlank(queryVo.getRecordCode()) &&  StringUtils.isBlank(queryVo.getProcessCode()) && StringUtils.isBlank(queryVo.getReportUser())
       && StringUtils.isBlank(queryVo.getOrderDate())){
           queryWrapperX.isNotNull(ProcessRecord::getId);
       }
       queryWrapperX.orderByAsc(ProcessRecord::getStatus);
       return  selectPage(queryVo,queryWrapperX);
   }


    default ProcessRecord selectByIdAndCodeAndName(Long id, String processCode){
        LambdaQueryWrapperX<ProcessRecord> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ProcessRecord::getId,id);
        queryWrapperX.eq(ProcessRecord::getProcessCode,processCode);
        return selectOne(queryWrapperX);
    }

}
