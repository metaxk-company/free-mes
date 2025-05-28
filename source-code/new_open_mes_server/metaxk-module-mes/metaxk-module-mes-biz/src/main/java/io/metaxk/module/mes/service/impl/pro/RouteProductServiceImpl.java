package io.metaxk.module.mes.service.impl.pro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.common.UserConstants;
import io.metaxk.module.mes.controller.admin.pro.vo.RouteProductQueryVo;
import io.metaxk.module.mes.dal.dataobject.pro.Route;
import io.metaxk.module.mes.dal.dataobject.pro.RouteProduct;
import io.metaxk.module.mes.dal.mysql.pro.RouteProductMapper;
import io.metaxk.module.mes.service.pro.RouteProductService;
import io.metaxk.module.mes.utils.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;


/**
 * 产品制程 Service 实现类
 * @author 万界星空MES
 */
@Service
public class RouteProductServiceImpl implements RouteProductService {

    @Resource
    private RouteProductMapper routeProductMapper;

    @Override
    public Integer createRouteProduct(RouteProduct createReqVO) {
       createReqVO.setCreateTime(new Date());
      return   routeProductMapper.insert(createReqVO);
    }

    @Override
    public Integer updateRouteProduct(RouteProduct updateReqVO) {
      updateReqVO.setUpdateTime(new Date());
      return   routeProductMapper.updateById(updateReqVO);
    }

    @Override
    public Integer deleteRouteProduct(List<Long> id) {
       return routeProductMapper.deleteBatchIds(id);
    }



    @Override
    public RouteProduct getRouteProduct(Long id) {
        return routeProductMapper.selectById(id);
    }



    @Override
    public PageResult<RouteProduct> getRouteProductPage(RouteProductQueryVo pageReqVO) {
        return routeProductMapper.selectPage(pageReqVO);
    }



    @Override
    public void deleteByRouteId(Long routeId) {
        QueryWrapper<RouteProduct> wrapper = new QueryWrapper<>();
        wrapper.eq("route_id",routeId);
        routeProductMapper.delete(wrapper);
    }



    @Override
    public String checkItemUnique(RouteProduct createReqVO) {
        RouteProduct product = routeProductMapper.checkItemUnique(createReqVO);
        Long productId = createReqVO.getId()==null?-1L:createReqVO.getId();
        if(StringUtils.isNotNull(product) && product.getId().longValue() != productId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public RouteProduct selectProRouteProductByRecordId(Long recordId) {
        QueryWrapper<RouteProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",recordId);
        return routeProductMapper.selectOne(queryWrapper);
    }

    @Override
    public List<RouteProduct> selectProRouteProductList(RouteProduct param) {
        QueryWrapper<RouteProduct> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotNull(param.getRouteId())){
            queryWrapper.eq("route_id",param.getRouteId());
        }
        if(StringUtils.isNotNull(param.getItemId())){
            queryWrapper.eq("item_id",param.getItemId());
        }
        return routeProductMapper.selectList(queryWrapper);
    }

    @Override
    public RouteProduct findByItemId(Long productId) {
        LambdaQueryWrapperX<RouteProduct> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(RouteProduct::getItemId,productId);
        return routeProductMapper.selectOne(queryWrapperX);
    }



}
