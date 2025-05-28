package io.metaxk.module.mes.dal.mysql.qc;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.qc.vo.OtherStandardQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.OtherStandard;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 万界星空
 * @time 2023/8/19 10:43
 */
@Mapper
public interface OtherStandardMapper extends BaseMapperX<OtherStandard> {

    default PageResult<OtherStandard> findPage(OtherStandardQueryVo reqVO){

        LambdaQueryWrapperX<OtherStandard> queryWrapperX = new LambdaQueryWrapperX<>();

        if(StringUtils.isNotBlank(reqVO.getNumber())) {
            queryWrapperX.eq(OtherStandard::getNumber, reqVO.getNumber());
        }
        if(StringUtils.isNotBlank(reqVO.getModel())) {
            queryWrapperX.eq(OtherStandard::getModel, reqVO.getModel());
        }
        if(StringUtils.isNotBlank(reqVO.getSpec())) {
            queryWrapperX.eq(OtherStandard::getSpec, reqVO.getSpec());
        }
        if(StringUtils.isNotBlank(reqVO.getLineType())) {
            queryWrapperX.eq(OtherStandard::getLineType, reqVO.getLineType());
        }
        if(StringUtils.isBlank(reqVO.getNumber()) &&  StringUtils.isBlank(reqVO.getModel())
                && StringUtils.isBlank(reqVO.getSpec()) && StringUtils.isBlank(reqVO.getLineType())){
            queryWrapperX.isNotNull(OtherStandard::getId);
        }
        return  selectPage(reqVO,queryWrapperX);
    }

    /*default OtherStandard findOtherStandard(String model, String spec, String lineType){
        //LambdaQueryWrapper<OtherStandard> lambdaQueryWrapper = Wrappers.lambdaQuery();
        LambdaQueryWrapperX<OtherStandard> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OtherStandard::getModel,model);
        return  selectOne(queryWrapperX);
    }*/

    public OtherStandard findOtherStandard(@Param("model")String model, @Param("spec")String spec, @Param("lineType")String lineType);
}
