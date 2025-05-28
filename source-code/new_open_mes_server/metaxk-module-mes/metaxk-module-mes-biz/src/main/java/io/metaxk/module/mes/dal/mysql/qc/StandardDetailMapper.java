package io.metaxk.module.mes.dal.mysql.qc;

import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.dal.dataobject.qc.StandardDetail;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;


/**
 * @author 万界星空
 * @time 2023/7/11 17:19
 */

@Mapper
public interface StandardDetailMapper extends BaseMapperX<StandardDetail> {

    /**
     * 根据工序编号查询工序标准中的检测项
     * @param processCode
     * @return List<StandardDetail>
     */
   default List<StandardDetail> findDetailByProcessCode(String processCode){
       LambdaQueryWrapperX<StandardDetail> queryWrapperX = new LambdaQueryWrapperX<>();
       queryWrapperX.like(StandardDetail::getProcessCode,processCode);
       return  selectList(queryWrapperX);
   }



}
