package io.metaxk.module.mes.dal.mysql.wm;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.wm.vo.WareHouseAreaQueryVo;
import io.metaxk.module.mes.dal.dataobject.wm.WareHouseArea;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;


/**
 * 库区 Mapper
 * @author 万界星空MES
 */
@Mapper
public interface WareHouseAreaMapper extends BaseMapperX<WareHouseArea> {


   default PageResult<WareHouseArea> findPage(WareHouseAreaQueryVo wareHouseAreaQueryVo){
       LambdaQueryWrapperX<WareHouseArea> queryWrapperX = new LambdaQueryWrapperX<>();
       if(StringUtils.isNotBlank(wareHouseAreaQueryVo.getAreaNumber())){
           queryWrapperX.eq(WareHouseArea::getAreaNumber,wareHouseAreaQueryVo.getAreaNumber());
       }
       if(StringUtils.isNotBlank(wareHouseAreaQueryVo.getAreaName())){
           queryWrapperX.eq(WareHouseArea::getAreaName,wareHouseAreaQueryVo.getAreaName());
       }
       if(StringUtils.isBlank(wareHouseAreaQueryVo.getAreaNumber()) && StringUtils.isBlank(wareHouseAreaQueryVo.getAreaName())){
           queryWrapperX.isNotNull(WareHouseArea::getId);
       }
       return  selectPage(wareHouseAreaQueryVo,queryWrapperX);
   }

}
