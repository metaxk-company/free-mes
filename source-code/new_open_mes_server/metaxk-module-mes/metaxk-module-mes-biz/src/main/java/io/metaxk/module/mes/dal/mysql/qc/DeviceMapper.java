package io.metaxk.module.mes.dal.mysql.qc;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.qc.vo.DeviceQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.Device;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 万界星空
 * @time 2023/7/6 11:16
 */
@Mapper
public interface DeviceMapper extends BaseMapperX<Device> {

    /**
     * 检验设备
     * @param queryVo
     * @return  PageResult<Device>
     */
   default PageResult<Device> findPage(DeviceQueryVo queryVo){
       LambdaQueryWrapperX<Device> queryWrapperX = new LambdaQueryWrapperX<>();
       if(StringUtils.isNotBlank(queryVo.getDeviceCode())){
           queryWrapperX.eq(Device::getDeviceCode,queryVo.getDeviceCode());
       }
       if(StringUtils.isNotBlank(queryVo.getDeviceName())){
           queryWrapperX.eq(Device::getDeviceName,queryVo.getDeviceName());
       }
       if(StringUtils.isBlank(queryVo.getDeviceCode()) && StringUtils.isBlank(queryVo.getDeviceName())){
           queryWrapperX.isNotNull(Device::getId);
       }
       return  selectPage(queryVo,queryWrapperX);
   }



}
