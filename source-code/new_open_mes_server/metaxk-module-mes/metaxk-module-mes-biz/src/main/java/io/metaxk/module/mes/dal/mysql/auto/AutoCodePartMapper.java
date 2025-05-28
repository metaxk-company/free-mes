package io.metaxk.module.mes.dal.mysql.auto;

import java.util.*;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.module.mes.dal.dataobject.auto.AutoCodePart;
import org.apache.ibatis.annotations.Mapper;

/**
 * 编码生成规则组成 Mapper
 * @author 万界星空
 */
@Mapper
public interface AutoCodePartMapper extends BaseMapperX<AutoCodePart> {



    /**
     *  编码生成规则集合
     * @param sysAutoCodePart
     * @return List<AutoCodePart>
     */
    default   List<AutoCodePart> selectSysAutoCodePartList(AutoCodePart sysAutoCodePart) {
        return selectList(new LambdaQueryWrapperX<AutoCodePart>()
                .eqIfPresent(AutoCodePart::getPartId, sysAutoCodePart.getPartId())
                .eqIfPresent(AutoCodePart::getRuleId, sysAutoCodePart.getRuleId())
                .eqIfPresent(AutoCodePart::getPartIndex, sysAutoCodePart.getPartIndex())
                .eqIfPresent(AutoCodePart::getPartType, sysAutoCodePart.getPartType())
                .eqIfPresent(AutoCodePart::getPartCode, sysAutoCodePart.getPartCode())
                .likeIfPresent(AutoCodePart::getPartName, sysAutoCodePart.getPartName())
                .orderByAsc(AutoCodePart::getPartIndex));

    }


    /**
     *  根据partId查询编码生成规则
     * @param partId
     * @return AutoCodePart
     */
    default AutoCodePart findById(Long partId) {
        return selectOne(new LambdaQueryWrapperX<AutoCodePart>()
                .eqIfPresent(AutoCodePart::getPartId, partId)
                .orderByAsc(AutoCodePart::getPartIndex));
    }


    /**
     * 查询编码生成规则
     * @param sysAutoCodePart
     * @return AutoCodePart
     */
    default AutoCodePart checkPartUnique(AutoCodePart sysAutoCodePart) {
        return selectOne(new LambdaQueryWrapperX<AutoCodePart>()
                .eqIfPresent(AutoCodePart::getRuleId, sysAutoCodePart.getRuleId())
                .or().eq(AutoCodePart::getPartName, sysAutoCodePart.getPartName())
                .or().eq(AutoCodePart::getPartIndex, sysAutoCodePart.getPartIndex()));
    }
}
