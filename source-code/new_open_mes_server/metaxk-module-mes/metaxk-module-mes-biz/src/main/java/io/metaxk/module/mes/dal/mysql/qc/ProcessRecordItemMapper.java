package io.metaxk.module.mes.dal.mysql.qc;

import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.qc.vo.ProcessRecordItemsVo;
import io.metaxk.module.mes.dal.dataobject.qc.ProcessRecordItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/11 17:33
 */
@Mapper
public interface ProcessRecordItemMapper extends BaseMapperX<ProcessRecordItem> {

    public int getCount(Long processFormId);

    default List<ProcessRecordItem> selectCompareByNumber(String detectionOrderNumber, String status){
        LambdaQueryWrapperX<ProcessRecordItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ProcessRecordItem::getSortNumber,detectionOrderNumber);
        if(status != null && !"".equals(status)){
            queryWrapperX.eq(ProcessRecordItem::getStatus,status);
        }
        return selectList(queryWrapperX);
    }

    public int updateProcessRecordItem(@Param("recordId")Long recordId,@Param("itemBarCode")String itemBarCode,@Param("flag")String flag,@Param("detectionOrderNumber")String detectionOrderNumber);

    public String selectMaxDetectionOrderNumber(Long processFormId);

    default List<ProcessRecordItem> getProcessFormCompare(Long processFormId, String detectionOrderNumber){
        LambdaQueryWrapperX<ProcessRecordItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ProcessRecordItem::getRecordId,processFormId);
        queryWrapperX.eq(ProcessRecordItem::getSortNumber,detectionOrderNumber);
        return selectList(queryWrapperX);
    }

    public List<ProcessRecordItemsVo> getProcessFormCompares(@Param("recordId") Long recordId, @Param("processCode") String processCode, @Param("maxSortNumber")String maxSortNumber);

    default ProcessRecordItem selectRecordItemByStandardItemId(Long recordId,Long standardItemId,String sortNumber){
        LambdaQueryWrapperX<ProcessRecordItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ProcessRecordItem::getRecordId,recordId);
        queryWrapperX.eq(ProcessRecordItem::getStandardItemId,standardItemId);
        queryWrapperX.eq(ProcessRecordItem::getSortNumber,sortNumber);
        return selectOne(queryWrapperX);
    }

}
