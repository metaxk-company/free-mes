package io.metaxk.module.mes.dal.mysql.wm;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.wm.vo.WareHouseLocationQueryVo;
import io.metaxk.module.mes.dal.dataobject.wm.WareHouseLocation;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;


/**
 * 库位 Mapper
 * @author 万界星空MES
 */
@Mapper
public interface WareHouseLocationMapper extends BaseMapperX<WareHouseLocation> {


   default PageResult<WareHouseLocation> findPage(WareHouseLocationQueryVo wareHouseLocationQueryVo){
       LambdaQueryWrapperX<WareHouseLocation> queryWrapperX = new LambdaQueryWrapperX<>();
       if(StringUtils.isNotBlank(wareHouseLocationQueryVo.getLocationNumber())){
           queryWrapperX.eq(WareHouseLocation::getLocationNumber,wareHouseLocationQueryVo.getLocationNumber());
       }
       if(StringUtils.isNotBlank(wareHouseLocationQueryVo.getLocationName())){
           queryWrapperX.eq(WareHouseLocation::getLocationName,wareHouseLocationQueryVo.getLocationName());
       }
       if(StringUtils.isBlank(wareHouseLocationQueryVo.getLocationNumber()) && StringUtils.isBlank(wareHouseLocationQueryVo.getLocationName())){
           queryWrapperX.isNotNull(WareHouseLocation::getId);
       }
       return  selectPage(wareHouseLocationQueryVo,queryWrapperX);
   }


}
