package io.metaxk.module.mes.service.impl.pro;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.pro.vo.*;
import io.metaxk.module.mes.dal.dataobject.pro.Route;
import io.metaxk.module.mes.dal.mysql.pro.RouteMapper;
import io.metaxk.module.mes.service.pro.RouteService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;




/**
 * 工艺路线 Service 实现类
 * @author 万界星空MES
 */
@Service
public class RouteServiceImpl implements RouteService {

    @Resource
    private RouteMapper routeMapper;


    @Override
    public Route getRoute(Long id) {
        return routeMapper.selectById(id);
    }

//    @Override
//    public List<Route> getRouteList(Collection<Long> ids) {
//        return routeMapper.selectBatchIds(ids);
//    }

    @Override
    public PageResult<Route> getRoutePage(RoutePageReqVo pageReqVO) {
        return routeMapper.selectPage(pageReqVO);
    }


    @Override
    public Integer insertRoute(Route routeDO) {
        routeDO.setCreateTime(new Date());
        return routeMapper.insert(routeDO);
    }

    @Override
    public Integer updateRoutes(Route routeDO) {
        routeDO.setUpdateTime(new Date());
        return routeMapper.updateById(routeDO);
    }

    @Override
    public Integer deleteRoutes( List<Long> routeIds) {
        return routeMapper.deleteBatchIds(routeIds);
    }



    @Override
    public List<RouteExcelVo> listData() {
        List<Route> dictList = routeMapper.selectList();
        ArrayList<RouteExcelVo> excelDictDTOList = new ArrayList<>(dictList.size());
        dictList.forEach(dict -> {
            RouteExcelVo excelDictDTO = new RouteExcelVo();
            BeanUtils.copyProperties(dict, excelDictDTO);
            excelDictDTOList.add(excelDictDTO);
        });
        return excelDictDTOList;
    }



    @Override
    public Route findRouteByCode(String routeCode) {
        LambdaQueryWrapperX<Route> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Route::getRouteCode,routeCode);
        return routeMapper.selectOne(queryWrapperX);
    }



}
