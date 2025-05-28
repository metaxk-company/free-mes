package io.metaxk.module.mes.service.md;

import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.md.vo.ProductBomQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.ProductBom;

/**
 * 产品BOM Service 接口
 * @author 万界星空
 */
public interface ProductBomService {


    /**
     * 新增产品BOM
     * @param productBomDO
     * @return Integer
     */
    Integer createProductBom( ProductBom productBomDO);


    /**
     * 修改产品BOM
     * @param productBomDO
     * @return Integer
     */
    Integer updateProductBom( ProductBom productBomDO);

    /**
     * 删除产品BOM
     * @param ids
     * @return Integer
     */
    Integer deleteProductBom(List<Long> ids);


    /**
     * 根据id查询产品BOM
     * @param id
     * @return ProductBom
     */
    ProductBom getProductBom(Long id);


    /**
     * 产品BOM条件分页查询
     * @param pageReqVO
     * @return PageResult<ProductBom>
     */
    PageResult<ProductBom> getProductBomPage(ProductBomQueryVo pageReqVO);


    /**
     * 产品BOM列表
     * @param param
     * @return List<ProductBom>
     */
    List<ProductBom> selectMdProductBomList(ProductBom param);

    /**
     * 根据id查询产品BOM列表
     * @param itemId
     * @return List<ProductBom>
     */
    List<ProductBom> selectMdProductBomByItemId(Long itemId);

    /**
     * 删除产品BOM
     * @param ids
     */
    void deleteByItemId(List<Long> ids);

    /**
     * 根据产品id查询产品BOM列表
     * @param productId
     * @return  List<ProductBom>
     */
    List<ProductBom> selectByItemId(Long productId);

    /**
     * 根据产品id查询产品BOM列表
     * @param productId
     * @return List<ProductBom>
     */
    List<ProductBom> selectMdProductBomListByProductId(Long productId);
}
