package io.metaxk.module.mes.dal.mysql.md;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.md.vo.ColorQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.Color;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * 颜色Mapper
 *
 * @author 万界星空MES
 */
@Mapper
public interface ColorMapper extends BaseMapperX<Color> {

    default PageResult<Color> findPage(ColorQueryVo colorQueryVo) {
        LambdaQueryWrapperX<Color> queryWrapperX = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotBlank(colorQueryVo.getName())){
            queryWrapperX.like(Color::getName,colorQueryVo.getName());
        }
        if(StringUtils.isBlank(colorQueryVo.getName())){
            queryWrapperX.isNotNull(Color::getId);
        }
        return selectPage(colorQueryVo, queryWrapperX);
    };

}
