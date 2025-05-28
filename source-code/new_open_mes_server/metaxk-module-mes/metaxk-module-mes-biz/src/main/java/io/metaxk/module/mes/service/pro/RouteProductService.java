package io.metaxk.module.mes.service.pro;

import java.util.*;
import io.metaxk.module.mes.controller.admin.pro.vo.RouteProductQueryVo;
import io.metaxk.module.mes.dal.dataobject.pro.Route;
import io.metaxk.module.mes.dal.dataobject.pro.RouteProduct;
import io.metaxk.framework.common.pojo.PageResult;

/**
 * 工艺产品 Service 接口
 * @author 万界星空
 */
public interface RouteProductService {

    /**
     * 新增工艺产品
     * @param createReqVO
     * @return Integer
     */
    Integer createRouteProduct(RouteProduct createReqVO);

    /**
     * 修改工艺产品
     * @param updateReqVO
     * @return Integer
     */
    Integer updateRouteProduct( RouteProduct updateReqVO);

    /**
     * 删除工艺产品
     * @param id
     * @return Integer
     */
    Integer deleteRouteProduct(List<Long>  id);

    /**
     * 根据id查询工艺产品
     * @param id
     * @return RouteProduct
     */
    RouteProduct getRouteProduct(Long id);


    /**
     * 工艺产品条件分页查询
     * @param pageReqVO
     * @return ageResult<RouteProduct>
     */
    PageResult<RouteProduct> getRouteProductPage(RouteProductQueryVo pageReqVO);

    /**
     *  删除工艺产品
     * @param routeId
     */
    void deleteByRouteId(Long routeId);

    /**
     * 校验工艺产品
     * @param createReqVO
     * @return String
     */
    String checkItemUnique(RouteProduct createReqVO);

    /**
     * 根据工艺id查询工艺产品
     * @param recordId
     * @return RouteProduct
     */
    RouteProduct selectProRouteProductByRecordId(Long recordId);

    /**
     * 工艺产品列表
     * @param param
     * @return List<RouteProduct>
     */
    List<RouteProduct> selectProRouteProductList(RouteProduct param);

    /**
     * 根据产品id查询工艺产品
     * @param productId
     * @return RouteProduct
     */
    RouteProduct findByItemId(Long productId);


}
