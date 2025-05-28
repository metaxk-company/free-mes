package io.metaxk.module.mes.service.impl.qc;

import com.alibaba.excel.EasyExcel;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.qc.vo.ProjectClassifyExcelVo;
import io.metaxk.module.mes.controller.admin.qc.vo.ProjectClassifyQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.ProjectClassify;
import io.metaxk.module.mes.dal.mysql.qc.ProjectClassifyMapper;
import io.metaxk.module.mes.service.qc.ProjectClassifyService;
import org.springframework.stereotype.Service;
import io.metaxk.module.mes.controller.admin.qc.vo.ProjectClassifyListener;
import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/6 11:40
 */
@Service
public class ProjectClassifyServiceImpl implements ProjectClassifyService {

    @Resource
    private ProjectClassifyMapper projectClassifyMapper;

    @Override
    public Integer saveInspectProjectClassify(ProjectClassify inspectProjectClassify) {
        return projectClassifyMapper.insert(inspectProjectClassify);
    }

    @Override
    public Integer removeInspectProjectClassify(List<Long> ids) {
        return projectClassifyMapper.deleteBatchIds(ids);
    }

    @Override
    public ProjectClassify findInspectProjectClassify(Long id) {
        return projectClassifyMapper.selectById(id);
    }

    @Override
    public Integer updateInspectProjectClassify(ProjectClassify inspectProjectClassify) {
        return projectClassifyMapper.updateById(inspectProjectClassify);
    }

    @Override
    public PageResult<ProjectClassify> findPage(ProjectClassifyQueryVo inspectProjectClassifyPageVO) {
        return projectClassifyMapper.findPage(inspectProjectClassifyPageVO);
    }

    @Override
    public void importExcelData(InputStream inputStream) {
        EasyExcel.read(inputStream, ProjectClassifyExcelVo.class,new ProjectClassifyListener(projectClassifyMapper)).sheet().doRead();
    }

    @Override
    public ProjectClassify findProjectClassifyByName(String projectName) {
        LambdaQueryWrapperX<ProjectClassify> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ProjectClassify::getProjectName,projectName);
        return projectClassifyMapper.selectOne(queryWrapperX);
    }


}
