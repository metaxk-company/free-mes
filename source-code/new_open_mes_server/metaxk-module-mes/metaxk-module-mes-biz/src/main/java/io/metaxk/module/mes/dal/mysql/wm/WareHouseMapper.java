package io.metaxk.module.mes.dal.mysql.wm;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.wm.vo.WareHouseQueryVo;
import io.metaxk.module.mes.dal.dataobject.wm.WareHouse;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * 仓库 Mapper
 * @author 万界星空MES
 */
@Mapper
public interface WareHouseMapper extends BaseMapperX<WareHouse> {


   default PageResult<WareHouse> findPage(WareHouseQueryVo wareHouseQueryVo){
       LambdaQueryWrapperX<WareHouse> queryWrapperX = new LambdaQueryWrapperX<>();
       if(StringUtils.isNotBlank(wareHouseQueryVo.getWarehouseNumber())){
           queryWrapperX.eq(WareHouse::getWarehouseNumber,wareHouseQueryVo.getWarehouseNumber());
       }
       if(StringUtils.isNotBlank(wareHouseQueryVo.getWareHouseName())){
           queryWrapperX.eq(WareHouse::getWarehouseName,wareHouseQueryVo.getWareHouseName());
       }
       if(StringUtils.isBlank(wareHouseQueryVo.getWarehouseNumber()) && StringUtils.isBlank(wareHouseQueryVo.getWareHouseName())){
           queryWrapperX.isNotNull(WareHouse::getId);
       }
       return  selectPage(wareHouseQueryVo,queryWrapperX);
   }


}
