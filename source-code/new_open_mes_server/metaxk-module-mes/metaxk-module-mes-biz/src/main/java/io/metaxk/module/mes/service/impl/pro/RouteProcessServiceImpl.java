package io.metaxk.module.mes.service.impl.pro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.common.UserConstants;
import io.metaxk.module.mes.controller.admin.pro.vo.RouteProcessQueryVo;
import io.metaxk.module.mes.dal.dataobject.pro.RouteProcess;
import io.metaxk.module.mes.dal.mysql.pro.RouteProcessMapper;
import io.metaxk.module.mes.service.pro.RouteProcessService;
import io.metaxk.module.mes.utils.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;


/**
 * 工艺组成 Service 实现类
 * @author 万界星空MES
 */
@Service
public class RouteProcessServiceImpl implements RouteProcessService {

    @Resource
    private RouteProcessMapper routeProcessMapper;

    @Override
    public Integer deleteRouteProcess(List<Long> id) {
      return   routeProcessMapper.deleteBatchIds(id);
    }

    @Override
    public RouteProcess getRouteProcess(Long id) {
        return routeProcessMapper.selectById(id);
    }



    @Override
    public void deleteByRouteId(Long routeId) {
        QueryWrapper<RouteProcess> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("route_id",routeId);
        routeProcessMapper.delete(queryWrapper);
    }

    /*
    * 校验
    * */
    @Override
    public String checkOrderNumExists(RouteProcess routeProcessDO) {
        RouteProcess process = routeProcessMapper.checkOrderNumExists(routeProcessDO);
        Long recordId = routeProcessDO.getId()==null?-1L:routeProcessDO.getId();
        if(StringUtils.isNotNull(process) && process.getId().longValue() != recordId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkProcessExists(RouteProcess routeProcessDO) {
        RouteProcess process = routeProcessMapper.checkProcessExists(routeProcessDO);
        Long recordId = routeProcessDO.getId()==null?-1L:routeProcessDO.getId();
        if(StringUtils.isNotNull(process) && process.getId().longValue() != recordId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkUpdateFlagUnique(RouteProcess createReqVO) {
        RouteProcess process = routeProcessMapper.checkUpdateFlagUnique(createReqVO);
        Long recordId = createReqVO.getId()==null?-1L:createReqVO.getId();
        if(StringUtils.isNotNull(process) && process.getId().longValue() != recordId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }



    @Override
    public RouteProcess findPreProcess(RouteProcess createReqVO) {
        return routeProcessMapper.findPreProcess(createReqVO);
    }


    @Override
    public Integer updateProRouteProcess(RouteProcess routeProcess) {
        routeProcess.setUpdateTime(new Date());
        routeProcess.setId(routeProcess.getId());
        return routeProcessMapper.updateById(routeProcess);
    }

    @Override
    public RouteProcess findNextProcess(RouteProcess createReqVO) {
        return routeProcessMapper.findNextProcess(createReqVO);
    }


    @Override
    public Integer insertProRouteProcess(RouteProcess routeProcess) {
        routeProcess.setCreateTime(new Date());
        return routeProcessMapper.insert(routeProcess);
    }

    @Override
    public   List<RouteProcess> selectProRouteProcessList(RouteProcess routeProcess) {
        return routeProcessMapper.selectProRouteProcessList(routeProcess);
    }

    @Override
    public PageResult<RouteProcess> getRouteProcessPage(RouteProcessQueryVo pageVO) {
        return routeProcessMapper.getRouteProcessPage(pageVO);
    }

    @Override
    public List<RouteProcess> findByRouteId(Long routeId) {
        LambdaQueryWrapperX<RouteProcess> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(RouteProcess::getRouteId,routeId);
        return routeProcessMapper.selectList(queryWrapperX);
    }

}
