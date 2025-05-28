package io.metaxk.module.mes.service.pro;

import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.pro.vo.RouteProcessQueryVo;
import io.metaxk.module.mes.dal.dataobject.pro.RouteProcess;

/**
 * 工艺组成 工艺工序 Service 接口
 * @author 万界星空
 */
public interface RouteProcessService {


    /**
     * 根据id查询工艺共工序
     * @param id
     * @return Integer
     */
    Integer deleteRouteProcess(List<Long> id);

    /**
     * 根据id查询工艺共工序
     * @param id
     * @return RouteProcess
     */
    RouteProcess getRouteProcess(Long id);



    /**
     * 删除工艺工序
     * @param routeId
     */
    void deleteByRouteId(Long routeId);

    /**
     * 校验工艺工序
     * @param routeProcessDO
     * @return String
     */
    String checkOrderNumExists(RouteProcess routeProcessDO);

    /**
     * 校验工艺工序
     * @param routeProcessDO
     * @return String
     */
    String checkProcessExists(RouteProcess routeProcessDO);

    /**
     * 校验工艺工序
     * @param createReqVO
     * @return String
     */
    String checkUpdateFlagUnique(RouteProcess createReqVO);

    /**
     * 查询工艺工序
     * @param createReqVO
     * @return RouteProcess
     */
    RouteProcess findPreProcess(RouteProcess createReqVO);

    /**
     * 修改工艺工序
     * @param preProcess
     * @return Integer
     */
    Integer updateProRouteProcess(RouteProcess preProcess);

    /**
     * 查询下一道工序
     * @param createReqVO
     * @return RouteProcess
     */
    RouteProcess findNextProcess(RouteProcess createReqVO);

    /**
     * 新增工艺工序
     * @param createReqVO
     * @return Integer
     */
    Integer insertProRouteProcess(RouteProcess createReqVO);

    /**
     * 工艺工序列表
     * @param param
     * @return List<RouteProcess>
     */
    List<RouteProcess> selectProRouteProcessList(RouteProcess param);

    /**
     * 工艺工序条件分页查询
     * @param pageVO
     * @return PageResult<RouteProcess>
     */
    PageResult<RouteProcess> getRouteProcessPage(RouteProcessQueryVo pageVO);

    /**
     * 根据工艺id查询工艺工序
     * @param routeId
     * @return List<RouteProcess>
     */
    List<RouteProcess> findByRouteId(Long routeId);
}
