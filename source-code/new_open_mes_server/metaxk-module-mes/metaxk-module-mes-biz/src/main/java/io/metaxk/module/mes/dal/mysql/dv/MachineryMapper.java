package io.metaxk.module.mes.dal.mysql.dv;


import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.module.mes.controller.admin.dv.vo.MachineryQueryVo;
import io.metaxk.module.mes.dal.dataobject.dv.Machinery;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;



/**
 * 设备 Mapper
 * @author 万界星空
 */
@Mapper
public interface MachineryMapper extends BaseMapperX<Machinery> {


    /**
     * 设备条件分页查询
     * @param reqVO
     * @return
     */
    default PageResult<Machinery> selectPage(MachineryQueryVo reqVO) {
        LambdaQueryWrapperX<Machinery> queryWrapper = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotBlank(reqVO.getMachineryCode())){
            queryWrapper.eq(Machinery::getMachineryCode,reqVO.getMachineryCode());
        }
        if(StringUtils.isNotBlank(reqVO.getMachineryName())){
            queryWrapper.like(Machinery::getMachineryName,reqVO.getMachineryName());
        }
        if(StringUtils.isNotNull(reqVO.getMachineryTypeId())){
            queryWrapper.eq(Machinery::getMachineryTypeId,reqVO.getMachineryTypeId());
        }
        if(StringUtils.isNotBlank(reqVO.getStatus())){
            queryWrapper.eq(Machinery::getStatus,reqVO.getStatus());
        }
        if(StringUtils.isBlank(reqVO.getMachineryCode()) && StringUtils.isBlank(reqVO.getMachineryName()) && StringUtils.isNull(reqVO.getMachineryTypeId())
        && StringUtils.isBlank(reqVO.getStatus())){
            queryWrapper.isNotNull(Machinery::getId);
        }
        return selectPage(reqVO,queryWrapper);
    }





}
