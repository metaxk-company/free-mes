package io.metaxk.module.mes.service.impl.pro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.metaxk.module.mes.controller.admin.pro.vo.WorkorderBomQueryVo;
import io.metaxk.module.mes.dal.dataobject.pro.WorkOrderBom;
import io.metaxk.module.mes.dal.mysql.pro.WorkOrderBomMapper;
import io.metaxk.module.mes.service.pro.WorkOrderBomService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;




/**
 * 生产工单BOM组成 Service 实现类
 * @author 万界星空MES
 */
@Service
public class WorkOrderBomServiceImpl implements WorkOrderBomService {

    @Resource
    private WorkOrderBomMapper workorderBomMapper;

    @Override
    public void removeWorkOrderBomByWorkOrderId(Long workorderId) {
        QueryWrapper<WorkOrderBom> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("workorder_id",workorderId);
        workorderBomMapper.delete(queryWrapper);
    }

    @Override
    public Integer createWorkorderBom(WorkOrderBom createReqVO) {
        // 返回
        return   workorderBomMapper.insert(createReqVO);
    }

    @Override
    public void updateWorkorderBom(WorkOrderBom updateReqVO) {
        workorderBomMapper.updateById(updateReqVO);
    }

    @Override
    public void deleteWorkorderBom(Long id) {
        workorderBomMapper.deleteById(id);
    }




    @Override
    public PageResult<WorkOrderBom> getWorkorderBomPage(WorkorderBomQueryVo pageReqVO) {
        return workorderBomMapper.selectPage(pageReqVO);
    }



    @Override
    public void insertProWorkorderBom(WorkOrderBom workorderBom) {
        workorderBom.setCreateTime(new Date());
        workorderBomMapper.insert(workorderBom);
    }

    @Override
    public List<WorkOrderBom> selectProWorkorderBomList(WorkOrderBom param) {
        return workorderBomMapper.selectProWorkorderBomList(param);
    }

}
