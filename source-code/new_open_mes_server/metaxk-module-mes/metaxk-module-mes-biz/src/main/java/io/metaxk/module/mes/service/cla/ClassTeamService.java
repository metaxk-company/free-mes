package io.metaxk.module.mes.service.cla;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.cla.vo.ClaTeamQueryVo;
import io.metaxk.module.mes.dal.dataobject.cla.ClassTeam;
import io.metaxk.module.mes.controller.admin.cla.vo.ClaTeamExcelVo;
import java.util.List;

/**
 * 班组设置 Service
 * @author 万界星空
 * @time 2023/6/26 9:42
 */
public interface ClassTeamService {


    /**
     * 新增班组设置
     * @param calTeam
     * @return Integer
     */
    Integer saveCalTeam(ClassTeam calTeam);


    /**
     * 删除班组设置
     * @param ids
     * @return Integer
     */
    Integer removeCalTeamByIds(List<Long> ids);

    /**
     * 修改班组设置
     * @param calTeam
     * @return Integer
     */
    Integer updateCalTeam(ClassTeam calTeam);

    /**
     * 班组设置条件分页查询
     * @param calTeamPageReqVO
     * @return PageResult<CalTeam>
     */
    PageResult<ClassTeam> getCalTeamPage(ClaTeamQueryVo calTeamPageReqVO);

    /**
     * 班组设置详情
     * @param id
     * @return CalTeam
     */
    ClassTeam findCalTeamById(Long id);

    /**
     * 班组设置导出
     * @return List<CalTeamExcelVO>
     */
    List<ClaTeamExcelVo> exportData();


    /**
     *  根据班组名称查询班组
     * @param teamName
     * @return CalTeam
     */
    ClassTeam findCalTeamByName(String teamName);

    /**
     * 根据编号查询班组信息
     * @param code
     * @return CalTeam
     */
    ClassTeam findCalTeamByCode(String code);

    List<ClassTeam> findCalTeamByids(List<Long> ids);
}
