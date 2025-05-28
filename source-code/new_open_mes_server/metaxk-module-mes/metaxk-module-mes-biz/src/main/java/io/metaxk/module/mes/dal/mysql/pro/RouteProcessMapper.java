package io.metaxk.module.mes.dal.mysql.pro;

import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.module.mes.controller.admin.pro.vo.RouteProcessQueryVo;
import io.metaxk.module.mes.dal.dataobject.pro.RouteProcess;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;


/**
 * 工艺工序 Mapper
 * @author 万界星空
 */
@Mapper
public interface RouteProcessMapper extends BaseMapperX<RouteProcess> {


    /**
     * 工艺工序条件分页查询
     * @param reqVO
     * @return PageResult<RouteProcess>
     */
    default PageResult<RouteProcess> selectPage(RouteProcessQueryVo reqVO) {
        LambdaQueryWrapperX<RouteProcess> queryWrapper = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotNull(reqVO.getRouteId())){
            queryWrapper.eq(RouteProcess::getRouteId,reqVO.getRouteId());
        }
        return selectPage(reqVO,queryWrapper);
    }


    /**
     * 根据工艺id和orderNum查询工艺工序
     * @param routeProcessDO
     * @return RouteProcess
     */
    default RouteProcess checkOrderNumExists(RouteProcess routeProcessDO) {
        return selectOne(new LambdaQueryWrapperX<RouteProcess>()
                .eqIfPresent(RouteProcess::getRouteId, routeProcessDO.getRouteId())
                .eqIfPresent(RouteProcess::getOrderNum, routeProcessDO.getOrderNum())
                .last("LIMIT 1"));

    }


    /**
     * 校验工艺工序是否存在
     * @param routeProcessDO
     * @return RouteProcess
     */
    default RouteProcess checkProcessExists(RouteProcess routeProcessDO) {
        return selectOne(new LambdaQueryWrapperX<RouteProcess>()
                .eqIfPresent(RouteProcess::getRouteId, routeProcessDO.getRouteId())
                .eqIfPresent(RouteProcess::getProcessId, routeProcessDO.getProcessId())
                .last("LIMIT 1"));

    }


    /**
     * 根据工艺id和状态查询工艺工序
     * @param createReqVO
     * @return RouteProcess
     */
    default RouteProcess checkUpdateFlagUnique(RouteProcess createReqVO) {
        return selectOne(new LambdaQueryWrapperX<RouteProcess>()
                .eqIfPresent(RouteProcess::getRouteId, createReqVO.getRouteId())
                .eqIfPresent(RouteProcess::getKeyFlag, "Y")
                .last("LIMIT 1"));

    }

    /**
     * 查询工艺工序
     * @param createReqVO
     * @return RouteProcess
     */
    RouteProcess findPreProcess(RouteProcess createReqVO);

    /**
     * 查询下一道工序
     * @param createReqVO
     * @return RouteProcess
     */
    RouteProcess findNextProcess(RouteProcess createReqVO);

    /**
     * 工艺工序集合
     * @param param
     * @return  List<RouteProcess>
     */
    default   List<RouteProcess> selectProRouteProcessList(RouteProcess param){
      LambdaQueryWrapperX<RouteProcess> queryWrapper = new LambdaQueryWrapperX<>();
      if(StringUtils.isNotNull(param.getRouteId())){
          queryWrapper.eq(RouteProcess::getRouteId,param.getRouteId());
      }
      if(StringUtils.isNotNull(param.getProcessId())){
          queryWrapper.eq(RouteProcess::getProcessId,param.getProcessId());
      }
      if(StringUtils.isNotBlank(param.getProcessCode())){
          queryWrapper.eq(RouteProcess::getProcessCode,param.getProcessCode());
      }
      if(StringUtils.isNotBlank(param.getProcessName())){
          queryWrapper.like(RouteProcess::getProcessName,param.getProcessName());
      }
      if(StringUtils.isNotNull(param.getOrderNum())){
          queryWrapper.eq(RouteProcess::getOrderNum,param.getOrderNum());
      }

      if(StringUtils.isNotNull(param.getNextProcessId())){
          queryWrapper.eq(RouteProcess::getOrderNum,param.getOrderNum());
      }
      if(StringUtils.isNotNull(param.getOrderNum())){
          queryWrapper.eq(RouteProcess::getOrderNum,param.getOrderNum());
      }
    return  selectList(queryWrapper);
  }


    /**
     * 工艺工序条件分页查询
     * @param reqVO
     * @return PageResult<RouteProcess>
     */
  default   PageResult<RouteProcess> getRouteProcessPage(RouteProcessQueryVo reqVO){
      LambdaQueryWrapperX<RouteProcess> queryWrapper = new LambdaQueryWrapperX<>();
      if(StringUtils.isNotNull(reqVO.getRouteId())){
          queryWrapper.eq(RouteProcess::getRouteId,reqVO.getRouteId());
      }
      return selectPage(reqVO,queryWrapper);
  }

   default List<RouteProcess> findByRouteId(Long id){
      return  selectList(new LambdaQueryWrapperX<RouteProcess>().eq(RouteProcess::getRouteId,id));
   }
}
