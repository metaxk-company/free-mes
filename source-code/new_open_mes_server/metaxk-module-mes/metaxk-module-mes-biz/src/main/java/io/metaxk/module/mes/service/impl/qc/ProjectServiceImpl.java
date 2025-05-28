package io.metaxk.module.mes.service.impl.qc;

import com.alibaba.excel.EasyExcel;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.qc.vo.ProjectExcelVo;
import io.metaxk.module.mes.controller.admin.qc.vo.ProjectListener;
import io.metaxk.module.mes.controller.admin.qc.vo.ProjectQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.Project;
import io.metaxk.module.mes.dal.mysql.qc.ProjectMapper;
import io.metaxk.module.mes.service.qc.ProjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/6 13:21
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Resource
    private ProjectMapper projectMapper;

    @Override
    public Integer saveInspectProject(Project inspectProject) {
        return projectMapper.insert(inspectProject);
    }

    @Override
    public Integer removeInspectProject(List<Long> ids) {
        return projectMapper.deleteBatchIds(ids);
    }

    @Override
    public Project findInspectProjectById(Long id) {
        return projectMapper.selectById(id);
    }

    @Override
    public Integer updateInspectProject(Project inspectProject) {
        return projectMapper.updateById(inspectProject);
    }

    @Override
    public PageResult<Project> findPage(ProjectQueryVo inspectProjectPageReqVO) {
        return projectMapper.findPage(inspectProjectPageReqVO);
    }

    @Override
    public void imporExceltData(InputStream inputStream) {
        EasyExcel.read(inputStream, ProjectExcelVo.class,new ProjectListener(projectMapper)).sheet().doRead();
    }

    @Override
    public Project findInspectProjectByName(String projectName) {
        LambdaQueryWrapperX<Project> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Project::getProjectName,projectName);
        return projectMapper.selectOne(queryWrapperX);
    }
}
