package io.metaxk.module.mes.dal.mysql.dv;

import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.module.mes.controller.admin.dv.vo.MachineryTypeQueryVo;
import io.metaxk.module.mes.dal.dataobject.dv.MachineryType;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;



/**
 * 设备类型 Mapper
 * @author 万界星空
 */
@Mapper
public interface MachineryTypeMapper extends BaseMapperX<MachineryType> {

    /**
     * 设备类型条件分页查询
     * @param reqVO
     * @return PageResult<MachineryType>
     */
    default PageResult<MachineryType> selectPage(MachineryTypeQueryVo reqVO) {
        LambdaQueryWrapperX<MachineryType> queryWrapper = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotBlank(reqVO.getMachineryTypeName())){
            queryWrapper.like(MachineryType::getMachineryTypeName,reqVO.getMachineryTypeName());
        }
        if(StringUtils.isNotBlank(reqVO.getEnableFlag())){
            queryWrapper.eq(MachineryType::getEnableFlag,reqVO.getEnableFlag());
        }
        if(StringUtils.isBlank(reqVO.getMachineryTypeName()) && StringUtils.isBlank(reqVO.getEnableFlag())){
            queryWrapper.isNotNull(MachineryType::getId);
        }
        return selectPage(reqVO,queryWrapper);

    }




    /**
     * 设备类型集合
     * @param machineryTypeDO
     * @return List<MachineryType>
     */
    default   List<MachineryType> findItemTypeList(MachineryType machineryTypeDO) {
        return selectList(new LambdaQueryWrapperX<MachineryType>()
                .likeIfPresent(MachineryType::getMachineryTypeName, machineryTypeDO.getMachineryTypeName())
                .eqIfPresent(MachineryType::getEnableFlag, machineryTypeDO.getEnableFlag()));

    }


    /**
     * 根据id查询设备类型
     * @param id
     * @return  MachineryType
     */
    default MachineryType selectMachineryTypeByMachineryTypeId(Long id) {
        return selectOne(new LambdaQueryWrapperX<MachineryType>()
                .eqIfPresent(MachineryType::getId, id));

    }


    /**
     * 通过设备类型id查询设备类型信息
     * @param machineryTypeId
     * @return MachineryType
     */
   default MachineryType selectByTypeId(Long machineryTypeId){
       LambdaQueryWrapperX<MachineryType> queryWrapperX = new LambdaQueryWrapperX<>();
       queryWrapperX.eq(MachineryType::getId,machineryTypeId);
       return  selectOne(queryWrapperX);
   }
}
