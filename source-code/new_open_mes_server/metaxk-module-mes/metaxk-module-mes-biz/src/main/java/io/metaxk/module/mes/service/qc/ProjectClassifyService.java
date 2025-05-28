package io.metaxk.module.mes.service.qc;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.qc.vo.ProjectClassifyQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.ProjectClassify;

import java.io.InputStream;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/6 11:40
 */
public interface ProjectClassifyService {

    Integer saveInspectProjectClassify(ProjectClassify inspectProjectClassify);


    Integer removeInspectProjectClassify(List<Long> ids);

    ProjectClassify findInspectProjectClassify(Long id);

    Integer updateInspectProjectClassify(ProjectClassify inspectProjectClassify);

    PageResult<ProjectClassify> findPage(ProjectClassifyQueryVo inspectProjectClassifyPageVO);

    void importExcelData(InputStream inputStream);

    ProjectClassify findProjectClassifyByName(String projectName);
}
