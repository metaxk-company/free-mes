package io.metaxk.module.mes.dal.mysql.pro;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.module.mes.controller.admin.pro.vo.RoutePageReqVo;
import io.metaxk.module.mes.dal.dataobject.pro.Route;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 工艺路线 Mapper
 * @author 万界星空
 */
@Mapper
public interface RouteMapper extends BaseMapperX<Route> {


    /**
     * 工艺路线条件分页查询
     * @param reqVO
     * @return PageResult<Route>
     */
    default PageResult<Route> selectPage(RoutePageReqVo reqVO) {
        LambdaQueryWrapperX<Route> queryWrapper = new LambdaQueryWrapperX<>();
        if (StringUtils.isNotBlank(reqVO.getRouteName())) {
            queryWrapper.like(Route::getRouteName, reqVO.getRouteName());
        }
        if (StringUtils.isNotBlank(reqVO.getRouteCode())) {
            queryWrapper.eq(Route::getRouteCode, reqVO.getRouteCode());
        }
        if(StringUtils.isNotBlank(reqVO.getEnableFlag())){
            queryWrapper.eq(Route::getEnableFlag, reqVO.getEnableFlag());
        }
        if (StringUtils.isBlank(reqVO.getRouteName()) && StringUtils.isBlank(reqVO.getRouteCode()) && StringUtils.isBlank(reqVO.getEnableFlag())) {
            queryWrapper.isNotNull(Route::getId);
        }
        return selectPage(reqVO, queryWrapper);
    }


    /**
     * 根据工艺编号和名称查询工艺信息
     * @param routeCode
     * @param routeName
     * @return Route
     */
    default Route selectByCodeAndName(String routeCode, String routeName){
      LambdaQueryWrapperX<Route> queryWrapperX = new LambdaQueryWrapperX<>();
      queryWrapperX.eq(Route::getRouteCode,routeCode);
      queryWrapperX.eq(Route::getRouteName,routeName);
      return  selectOne(queryWrapperX);
    }

   default List<Route> findRouteById(Long routeId){
        return  selectList(new LambdaQueryWrapperX<Route>().eq(Route::getId,routeId));
   }

}
