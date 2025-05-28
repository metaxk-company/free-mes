package io.metaxk.module.mes.dal.mysql.md;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.md.vo.PanHaoQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.PanHao;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * 盘号Mapper
 *
 * @author 万界星空MES
 */
@Mapper
public interface PanHaoMapper extends BaseMapperX<PanHao> {

    default PageResult<PanHao> findPage(PanHaoQueryVo panHaoQueryVo) {
        LambdaQueryWrapperX<PanHao> queryWrapperX = new LambdaQueryWrapperX<>();

        if(StringUtils.isNotBlank(panHaoQueryVo.getNumber())){
            queryWrapperX.like(PanHao::getNumber,panHaoQueryVo.getNumber());
        }
        if(StringUtils.isBlank(panHaoQueryVo.getNumber())){
            queryWrapperX.isNotNull(PanHao::getId);
        }
        return selectPage(panHaoQueryVo, queryWrapperX);
    };

}
