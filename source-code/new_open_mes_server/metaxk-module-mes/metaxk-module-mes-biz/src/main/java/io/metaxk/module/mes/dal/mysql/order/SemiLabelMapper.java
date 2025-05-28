package io.metaxk.module.mes.dal.mysql.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.order.vo.SemiLabelAllExportVo;
import io.metaxk.module.mes.controller.admin.order.vo.SemiLabelQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.SemiLabel;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/8/9 17:06
 */
@Mapper
public interface SemiLabelMapper extends BaseMapperX<SemiLabel> {

    default PageResult<SemiLabel> findPage(SemiLabelQueryVo queryVo){
        LambdaQueryWrapperX<SemiLabel> queryWrapperX = new LambdaQueryWrapperX<>();

        if(queryVo != null) {
            if (StringUtils.isNotBlank(queryVo.getNumber())) {
                queryWrapperX.eq(SemiLabel::getNumber, queryVo.getNumber());
            }
            if (queryVo.getIds() != null) {
                queryWrapperX.notIn(SemiLabel::getId, queryVo.getIds());
            }
            if (queryVo.getIds() == null && StringUtils.isBlank(queryVo.getNumber())) {
                queryWrapperX.isNotNull(SemiLabel::getId);
            }
        }
        return  selectPage(queryVo,queryWrapperX);
    }

    @Select("<script>" +
            "SELECT wsl.number,wsl.create_time,wsli.model,wsli.spec,wsli.color,wsli.line_type,wsli.reel_number,wsli.quantity,wsli.unit_of_measure,wsli.remark\n" +
            "FROM wh_semi_label AS wsl\n" +
            "LEFT JOIN wh_semi_label_item AS wsli ON wsl.number = wsli.semi_number\n" +
            "<if test='ids.size() != 0'>" +
            "WHERE wsl.id IN\n" +
            "<foreach collection='ids' index='index' item='id' open='(' separator=',' close=')'>#{id}</foreach>\n" +
            "</if>" +
            "</script>")
    List<SemiLabelAllExportVo> exportAllByIds(@Param("ids") List<Integer> ids);
}
