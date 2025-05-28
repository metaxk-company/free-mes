package io.metaxk.module.mes.dal.mysql.qc;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.qc.vo.ReceiveStandardQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.ReceiveStandard;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/26 11:43
 */
@Mapper
public interface ReceiveStandardMapper extends BaseMapperX<ReceiveStandard> {

    default PageResult<ReceiveStandard> findPage(ReceiveStandardQueryVo receiveStandardQueryVo){

        LambdaQueryWrapperX<ReceiveStandard> queryWrapperX = new LambdaQueryWrapperX<>();

        if(StringUtils.isNotBlank(receiveStandardQueryVo.getNumber())) {
            queryWrapperX.eq(ReceiveStandard::getNumber, receiveStandardQueryVo.getNumber());
        }
        if(StringUtils.isNotBlank(receiveStandardQueryVo.getMethod())) {
            queryWrapperX.eq(ReceiveStandard::getMethod, receiveStandardQueryVo.getMethod());
        }
        if(StringUtils.isNotBlank(receiveStandardQueryVo.getItemCode())) {
            queryWrapperX.eq(ReceiveStandard::getItemCode, receiveStandardQueryVo.getItemCode());
        }

        if(StringUtils.isBlank(receiveStandardQueryVo.getNumber()) &&  StringUtils.isBlank(receiveStandardQueryVo.getMethod())
                && StringUtils.isBlank(receiveStandardQueryVo.getItemCode())){
            queryWrapperX.isNotNull(ReceiveStandard::getId);
        }
        return  selectPage(receiveStandardQueryVo,queryWrapperX);
    }

    /*default List<ReceiveStandard> findReceiveStandardByRecNumber(String number){
        LambdaQueryWrapperX<ReceiveStandard> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.like(ReceiveStandard::getRecNumber, number);
        return selectList(queryWrapperX);
    }*/
    List<ReceiveStandard> findReceiveStandardByItemCode(@Param("itemCode") String itemCode);
}
