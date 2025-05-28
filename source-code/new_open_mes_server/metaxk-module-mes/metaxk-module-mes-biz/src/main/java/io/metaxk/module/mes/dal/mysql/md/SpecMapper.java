package io.metaxk.module.mes.dal.mysql.md;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.md.vo.NormQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.Spec;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * 规格Mapper
 *
 * @author 万界星空MES
 */
@Mapper
public interface SpecMapper extends BaseMapperX<Spec> {

    default PageResult<Spec> findPage(NormQueryVo normQueryVo) {
        LambdaQueryWrapperX<Spec> queryWrapperX = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotBlank(normQueryVo.getModel())){
            queryWrapperX.like(Spec::getModel, normQueryVo.getModel());
        }
        if(StringUtils.isNotBlank(normQueryVo.getFuzzyModel())){
            queryWrapperX.like(Spec::getModel, normQueryVo.getFuzzyModel());
        }
        if(StringUtils.isNotBlank(normQueryVo.getSerial())){
            queryWrapperX.like(Spec::getSerial, normQueryVo.getSerial());
        }
        if(StringUtils.isNotBlank(normQueryVo.getName())){
            queryWrapperX.like(Spec::getName, normQueryVo.getName());
        }
        if(StringUtils.isBlank(normQueryVo.getModel())
                && StringUtils.isBlank(normQueryVo.getFuzzyModel())
                && StringUtils.isBlank(normQueryVo.getSerial())
                && StringUtils.isBlank(normQueryVo.getName())
                && StringUtils.isBlank(normQueryVo.getIsProcure())){
            queryWrapperX.isNotNull(Spec::getId);
        }
        return selectPage(normQueryVo, queryWrapperX);
        
    }

}
