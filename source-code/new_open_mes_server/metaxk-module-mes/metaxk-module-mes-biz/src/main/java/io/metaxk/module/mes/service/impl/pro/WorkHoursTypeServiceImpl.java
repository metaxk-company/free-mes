package io.metaxk.module.mes.service.impl.pro;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.pro.vo.WorkHoursTypeVo;
import io.metaxk.module.mes.dal.dataobject.pro.WorkHoursType;
import io.metaxk.module.mes.dal.mysql.pro.WorkHoursTypeMapper;
import io.metaxk.module.mes.service.pro.WorkHoursTypeService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;



@Service
public class WorkHoursTypeServiceImpl implements WorkHoursTypeService {

    @Resource
    private WorkHoursTypeMapper workHoursTypeMapper;


    @Override
    public Integer save(WorkHoursType workHoursType) {

        workHoursType.setCreateTime(new Date());
        return workHoursTypeMapper.insert(workHoursType);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return workHoursTypeMapper.deleteBatchIds(ids);
    }

    @Override
    public WorkHoursType findById(Long id) {
        return workHoursTypeMapper.selectById(id);
    }

    @Override
    public Integer update(WorkHoursType workHoursType) {
        WorkHoursType workHoursType1 = workHoursTypeMapper.selectById(workHoursType.getId());
        workHoursType.setCreateTime(workHoursType1.getCreateTime());
        workHoursType.setUpdateTime(new Date());
        return workHoursTypeMapper.updateById(workHoursType);
    }

    @Override
    public PageResult<WorkHoursType> list(WorkHoursTypeVo workHoursTypeVO) {
        return workHoursTypeMapper.list(workHoursTypeVO);
    }

    @Override
    public WorkHoursType selectByWorkhoursType(String workhoursType) {
        LambdaQueryWrapperX<WorkHoursType> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(WorkHoursType::getWorkhoursType,workhoursType);
        return workHoursTypeMapper.selectOne(queryWrapper);
    }
}
