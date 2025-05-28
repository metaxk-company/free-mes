package io.metaxk.module.mes.dal.mysql.qc;

import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.dal.dataobject.qc.ReceiveStandardItem;
import io.metaxk.module.mes.dal.dataobject.qc.Standard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/26 11:43
 */
@Mapper
public interface ReceiveStandardItemMapper extends BaseMapperX<ReceiveStandardItem> {

    /*default List<ReceiveStandardItem> findReceiveStandardItemByRecNumber(String recNumber){
        LambdaQueryWrapperX<ReceiveStandardItem> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.like(ReceiveStandardItem::getRecNumber, recNumber);
        return selectList(queryWrapperX);
    }*/

    List<ReceiveStandardItem> findReceiveStandardItemByItemCode(@Param("itemCode") String itemCode);

}
