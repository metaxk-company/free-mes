package io.metaxk.module.mes.service.pro;

import java.util.*;
import io.metaxk.module.mes.controller.admin.pro.vo.*;
import io.metaxk.module.mes.dal.dataobject.pro.Route;
import io.metaxk.framework.common.pojo.PageResult;

/**
 * 工艺 Service 接口
 * @author 万界星空
 */
public interface RouteService {

    /**
     * 根据id查询工艺
     * @param id
     * @return Route
     */
    Route getRoute(Long id);

    /**
     * 工艺条件分页查询
     * @param pageReqVO
     * @return PageResult<Route>
     */
    PageResult<Route> getRoutePage(RoutePageReqVo pageReqVO);

    /**
     * 新增工艺
     * @param createReqVO
     * @return Integer
     */
    Integer insertRoute(Route createReqVO);

    /**
     * 修改工艺
     * @param updateReqVO
     * @return Integer
     */
    Integer updateRoutes(Route updateReqVO);
    /**
     * 删除艺
     * @param routeIds
     * @return Integer
     */
    Integer deleteRoutes(List<Long> routeIds);

    /**
     * 导出工艺
     * @return
     */
    List<RouteExcelVo> listData();

    /**
     * 根据共工艺编号查询工艺
     * @param routeCode
     * @return Route
     */
    Route findRouteByCode(String routeCode);


}
