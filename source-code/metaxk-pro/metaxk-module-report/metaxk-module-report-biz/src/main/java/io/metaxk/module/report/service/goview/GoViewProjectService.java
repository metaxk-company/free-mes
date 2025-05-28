package io.metaxk.module.report.service.goview;

import io.metaxk.framework.common.pojo.PageParam;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.report.controller.admin.goview.vo.project.GoViewProjectCreateReqVO;
import io.metaxk.module.report.controller.admin.goview.vo.project.GoViewProjectUpdateReqVO;
import io.metaxk.module.report.dal.dataobject.goview.GoViewProjectDO;

import javax.validation.Valid;

/**
 * GoView 项目 Service 接口
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
public interface GoViewProjectService {

    /**
     * 创建项目
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProject(@Valid GoViewProjectCreateReqVO createReqVO);

    /**
     * 更新项目
     *
     * @param updateReqVO 更新信息
     */
    void updateProject(@Valid GoViewProjectUpdateReqVO updateReqVO);

    /**
     * 删除项目
     *
     * @param id 编号
     */
    void deleteProject(Long id);

    /**
     * 获得项目
     *
     * @param id 编号
     * @return 项目
     */
    GoViewProjectDO getProject(Long id);

    /**
     * 获得我的项目分页
     *
     * @param pageReqVO 分页查询
     * @param userId 用户编号
     * @return GoView 项目分页
     */
    PageResult<GoViewProjectDO> getMyProjectPage(PageParam pageReqVO, Long userId);

}
