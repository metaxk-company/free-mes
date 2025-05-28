package io.metaxk.module.mes.service.qc;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.qc.vo.ProjectQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.Project;
import java.io.InputStream;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/6 13:21
 */
public interface ProjectService {

    Integer saveInspectProject(Project inspectProject);

    Integer removeInspectProject(List<Long> ids);

    Project findInspectProjectById(Long id);

    Integer updateInspectProject(Project inspectProject);

    PageResult<Project> findPage(ProjectQueryVo inspectProjectPageReqVO);

    void imporExceltData(InputStream inputStream);

    Project findInspectProjectByName(String projectName);
}
