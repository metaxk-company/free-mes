package io.metaxk.module.mes.service.impl.dv;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.dv.vo.MachineryStatusQueryVo;
import io.metaxk.module.mes.dal.dataobject.dv.MachineryStatus;
import io.metaxk.module.mes.dal.mysql.dv.MachineryStatusMapper;
import io.metaxk.module.mes.service.dv.MachineryStatusService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * @author 万界星空
 * @time 2023/6/20 15:33
 */
@Service
public class MachineryStatusServiceImpl implements MachineryStatusService {


    @Resource
    private MachineryStatusMapper  machineryStatusMapper;


    @Override
    public Integer save(MachineryStatus machineryStatus) {
      //  machineryStatus.setCreateTime(LocalDateTime.now());
         machineryStatus.setCreateTime(new Date());
        return machineryStatusMapper.insert(machineryStatus);
    }

    @Override
    public Integer batch(List<Long> ids) {
        return machineryStatusMapper.deleteBatchIds(ids);
    }

    @Override
    public MachineryStatus getMachineryStatusById(Long id) {
        return machineryStatusMapper.selectById(id);
    }

    @Override
    public Integer update(MachineryStatus machineryStatus) {
        MachineryStatus machineryStatusDO = machineryStatusMapper.selectById(machineryStatus.getId());
        machineryStatus.setCreateTime(machineryStatusDO.getCreateTime());
        machineryStatus.setUpdateTime(new Date());
        return machineryStatusMapper.updateById(machineryStatus);
    }


    @Override
    public PageResult<MachineryStatus> findList(MachineryStatusQueryVo machineryStatusPageVO) {
        return machineryStatusMapper.findList(machineryStatusPageVO);
    }

    @Override
    public MachineryStatus findByStatusName(String statusName) {
        LambdaQueryWrapperX<MachineryStatus> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(MachineryStatus::getStatusName,statusName);
        return machineryStatusMapper.selectOne(queryWrapperX);
    }


}
