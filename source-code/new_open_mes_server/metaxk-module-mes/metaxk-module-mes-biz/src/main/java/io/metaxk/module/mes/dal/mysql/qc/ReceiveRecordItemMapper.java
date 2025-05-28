package io.metaxk.module.mes.dal.mysql.qc;

import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.qc.vo.ReceiveRecordItemsVo;
import io.metaxk.module.mes.dal.dataobject.qc.ProcessRecordItem;
import io.metaxk.module.mes.dal.dataobject.qc.ReceiveRecordItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/26 14:59
 */
@Mapper
public interface ReceiveRecordItemMapper extends BaseMapperX<ReceiveRecordItem> {

    int getCount(@Param("recordId")Long recordId);

    String selectMaxDetectionOrderNumber(@Param("recordId")Long recordId);

    List<ReceiveRecordItemsVo> getReceiveRecordItems(@Param("recordId") Long recordId,@Param("itemCode") String itemCode,@Param("maxSortNumber") String maxSortNumber);

    default ReceiveRecordItem selectReceiveRecordItem(Long recordId,Long standardItemId, String sortNumber){
        LambdaQueryWrapperX<ReceiveRecordItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ReceiveRecordItem::getRecordId,recordId);
        queryWrapperX.eq(ReceiveRecordItem::getStandardItemId,standardItemId);
        queryWrapperX.eq(ReceiveRecordItem::getSortNumber,sortNumber);
        return selectOne(queryWrapperX);
    }

    Integer updateReceiveRecordItem(@Param("recordId")Long recordId,@Param("itemBarCode") String itemBarCode,@Param("flag") String flag,@Param("sortNumber") String sortNumber);

    default List<ReceiveRecordItem> selectReceiveRecordItem(String sortNumber, String status){
        LambdaQueryWrapperX<ReceiveRecordItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ReceiveRecordItem::getSortNumber,sortNumber);
        if(status != null && !"".equals(status)){
            queryWrapperX.eq(ReceiveRecordItem::getStatus,status);
        }
        return selectList(queryWrapperX);
    }
}
