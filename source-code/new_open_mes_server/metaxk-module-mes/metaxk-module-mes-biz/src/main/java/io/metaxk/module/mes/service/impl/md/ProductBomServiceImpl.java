package io.metaxk.module.mes.service.impl.md;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.md.vo.ProductBomQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.ProductBom;
import io.metaxk.module.mes.dal.mysql.md.ProductBomMapper;
import io.metaxk.module.mes.service.md.ProductBomService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.PRODUCT_BOM_NOT_EXISTS;

/**
 * 产品BOM关系 Service 实现类
 *
 * @author 万界星空MES
 */
@Service
@Validated
public class ProductBomServiceImpl implements ProductBomService {

    @Resource
    private ProductBomMapper productBomMapper;

    @Override
    public Integer createProductBom(ProductBom productBomDO) {
     productBomDO.setCreateTime(new Date());
     return    productBomMapper.insert(productBomDO);
    }

    @Override
    public Integer updateProductBom(ProductBom productBomDO) {
        productBomDO.setUpdateTime(new Date());
        return productBomMapper.updateById(productBomDO);
    }

    @Override
    public Integer deleteProductBom(List<Long> ids) {

     return  productBomMapper.deleteBatchIds(ids);
    }




    private void validateProductBomExists(Long id) {
        if (productBomMapper.selectById(id) == null) {
            throw exception(PRODUCT_BOM_NOT_EXISTS);
        }
    }

    @Override
    public ProductBom getProductBom(Long id) {
        return productBomMapper.selectById(id);
    }



    @Override
    public PageResult<ProductBom> getProductBomPage(ProductBomQueryVo pageReqVO) {
        return productBomMapper.selectPage(pageReqVO);
    }



    @Override
    public List<ProductBom> selectMdProductBomList(ProductBom param) {
        QueryWrapper<ProductBom> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("bom_item_id");
        return productBomMapper.selectList(queryWrapper);
    }


    @Override
    public List<ProductBom> selectMdProductBomByItemId(Long itemId) {
        QueryWrapper<ProductBom> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("item_id",itemId);
        queryWrapper.orderByAsc("bom_item_id");
        return productBomMapper.selectList(queryWrapper);
    }

    @Override
    public void deleteByItemId(List<Long> ids) {
        LambdaQueryWrapperX<ProductBom> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.in(ProductBom::getItemId, ids);
        productBomMapper.delete(queryWrapperX);
    }



    @Override
    public List<ProductBom> selectByItemId(Long productId) {
        LambdaQueryWrapperX<ProductBom> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ProductBom::getBomItemId,productId);
        return productBomMapper.selectList(queryWrapperX);
    }

    @Override
    public List<ProductBom> selectMdProductBomListByProductId(Long productId) {
        LambdaQueryWrapperX<ProductBom> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ProductBom::getItemId,productId);
        return productBomMapper.selectList(queryWrapperX);
    }

}
