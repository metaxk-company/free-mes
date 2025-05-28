package io.metaxk.module.mes.service.impl.qc;

import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.dal.dataobject.qc.ProjectDevice;
import io.metaxk.module.mes.dal.mysql.qc.ProjectDeviceMapper;
import io.metaxk.module.mes.service.qc.ProjectDeviceService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;


/**
 * @author 万界星空
 * @time 2023/7/11 21:26
 */
@Service
public class ProjectDeviceServiceImpl implements ProjectDeviceService {

    @Resource
    private ProjectDeviceMapper projectDeviceMapper;

    @Override
    public Integer saveProjectDevice(ProjectDevice projectDevice) {
        return projectDeviceMapper.insert(projectDevice);
    }

    @Override
    public List<ProjectDevice> findProjectDeviceByCode(String projectCode) {
        LambdaQueryWrapperX<ProjectDevice> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ProjectDevice::getProjectCode,projectCode);
        return projectDeviceMapper.selectList(queryWrapperX);
    }

    @Override
    public Integer removeProjectDeviceByCode(String projectCode) {
        LambdaQueryWrapperX<ProjectDevice> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ProjectDevice::getProjectCode,projectCode);
        return projectDeviceMapper.delete(queryWrapperX);
    }

    @Override
    public Integer removeDeviceByDeviceCode(String code,String projectCode) {
        LambdaQueryWrapperX<ProjectDevice> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ProjectDevice::getDeviceCode,code);
        queryWrapperX.eq(ProjectDevice::getProjectCode,projectCode);
        return projectDeviceMapper.delete(queryWrapperX);
    }
}
