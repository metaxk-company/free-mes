package io.metaxk.module.mes.service.impl.md;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.md.vo.WorkShopQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.WorkShop;
import io.metaxk.module.mes.dal.mysql.md.WorkShopMapper;
import io.metaxk.module.mes.service.md.WorkShopService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;



/**
 * 车间 Service 实现类
 * @author 万界星空MES
 */
@Service
public class WorkShopServiceImpl implements WorkShopService {

    @Resource
    private WorkShopMapper workshopMapper;


    @Override
    public Integer insertWorkshopDO(WorkShop workshop) {
        workshop.setCreateTime(new Date());
        return workshopMapper.insert(workshop);
    }

    @Override
    public Integer removeWorkshop(List<Long> ids) {
       return  workshopMapper.deleteBatchIds(ids);
    }


    @Override
    public Integer updateWorkshops(WorkShop Workshop) {
        Workshop.setUpdateTime(new Date());
        Workshop.setId(Workshop.getId());
        return workshopMapper.updateById(Workshop);
    }



    @Override
    public WorkShop selectMdWorkshopByWorkshopId(Long workshopId) {
        LambdaQueryWrapperX<WorkShop> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(WorkShop::getId,workshopId);
        return workshopMapper.selectOne(queryWrapper);
    }


    @Override
    public PageResult<WorkShop> listWorkshopPage(WorkShopQueryVo workshopPage) {
        return workshopMapper.listWorkshopPage(workshopPage);
    }

    @Override
    public WorkShop findWorkshopById(Long id) {
        return workshopMapper.selectById(id);
    }

    @Override
    public WorkShop findByCodeAndName(String workshopCode, String workshopName) {
        LambdaQueryWrapperX<WorkShop> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(WorkShop::getWorkshopCode,workshopCode);
        queryWrapperX.eq(WorkShop::getWorkshopName,workshopName);
        return workshopMapper.selectOne(queryWrapperX);
    }

    @Override
    public WorkShop findWorkShopByCode(String workshopCode) {
        LambdaQueryWrapperX<WorkShop> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(WorkShop::getWorkshopCode,workshopCode);
        return workshopMapper.selectOne(queryWrapperX);
    }

    @Override
    public WorkShop findWorkShopByName(String workshopName) {
        LambdaQueryWrapperX<WorkShop> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(WorkShop::getWorkshopName,workshopName);
        return workshopMapper.selectOne(queryWrapperX);
    }

    @Override
    public List<WorkShop> findavailableList() {
        LambdaQueryWrapperX<WorkShop> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(WorkShop::getEnableFlag,"Y");
        return workshopMapper.selectList(queryWrapperX);
    }

    @Override
    public List<WorkShop> selectMdWorkshopList(WorkShop mdWorkshop) {
        return workshopMapper.selectList();
    }

}
