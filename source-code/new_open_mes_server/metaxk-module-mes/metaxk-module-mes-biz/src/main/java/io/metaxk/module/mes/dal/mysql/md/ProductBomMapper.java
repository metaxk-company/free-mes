package io.metaxk.module.mes.dal.mysql.md;


import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.module.mes.controller.admin.md.vo.ProductBomQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.ProductBom;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * 产品BOM关系 Mapper
 * @author 万界星空
 */
@Mapper
public interface ProductBomMapper extends BaseMapperX<ProductBom> {

    /**
     * 产品BOM条件分页查询
     * @param reqVO
     * @return PageResult<ProductBom>
     */
    default PageResult<ProductBom> selectPage(ProductBomQueryVo reqVO) {
        LambdaQueryWrapperX<ProductBom> queryWrapper = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotNull(reqVO.getItemId())){
            queryWrapper.eq(ProductBom::getItemId,reqVO.getItemId());
        }
        return selectPage(reqVO,queryWrapper);
    }


}
