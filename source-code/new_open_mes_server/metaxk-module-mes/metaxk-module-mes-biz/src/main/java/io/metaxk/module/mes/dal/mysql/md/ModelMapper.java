package io.metaxk.module.mes.dal.mysql.md;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.md.vo.ModelQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.Model;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * 型号Mapper
 *
 * @author 万界星空MES
 */
@Mapper
public interface ModelMapper extends BaseMapperX<Model> {

    default PageResult<Model> findPage(ModelQueryVo modelQueryVo) {
        LambdaQueryWrapperX<Model> queryWrapperX = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotBlank(modelQueryVo.getName())){
            queryWrapperX.like(Model::getName, modelQueryVo.getName());
        }
        if(StringUtils.isNotBlank(modelQueryVo.getNumber())){
            queryWrapperX.like(Model::getNumber, modelQueryVo.getNumber());
        }

        if(StringUtils.isBlank(modelQueryVo.getName()) && StringUtils.isBlank(modelQueryVo.getNumber()) && StringUtils.isBlank(modelQueryVo.getIsProcure())){
            queryWrapperX.isNotNull(Model::getId);
        }
        return selectPage(modelQueryVo, queryWrapperX);
    }

}
