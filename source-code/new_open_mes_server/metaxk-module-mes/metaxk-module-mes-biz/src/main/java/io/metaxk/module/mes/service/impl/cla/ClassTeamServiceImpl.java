package io.metaxk.module.mes.service.impl.cla;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.cla.vo.ClaTeamExcelVo;
import io.metaxk.module.mes.controller.admin.cla.vo.ClaTeamQueryVo;
import io.metaxk.module.mes.dal.dataobject.cla.ClassTeam;
import io.metaxk.module.mes.dal.mysql.cla.ClassTeamMapper;
import io.metaxk.module.mes.service.cla.ClassTeamService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 班组设置实现类
 * @author 万界星空
 * @time 2023/6/26 9:43
 */
@Service
public class ClassTeamServiceImpl implements ClassTeamService {

    @Resource
    private ClassTeamMapper calTeamMapper;



    @Override
    public Integer saveCalTeam(ClassTeam calTeam) {
        return calTeamMapper.insert(calTeam);
    }

    @Override
    public Integer removeCalTeamByIds(List<Long> ids) {
        return calTeamMapper.deleteBatchIds(ids);
    }

    @Override
    public Integer updateCalTeam(ClassTeam calTeam) {
        return calTeamMapper.updateById(calTeam);
    }

    @Override
    public PageResult<ClassTeam> getCalTeamPage(ClaTeamQueryVo calTeamPageReqVO) {
        return calTeamMapper.getCalTeamPage(calTeamPageReqVO);
    }

    @Override
    public ClassTeam findCalTeamById(Long id) {
        return calTeamMapper.selectById(id);
    }

    @Override
    public List<ClaTeamExcelVo> exportData() {
        List<ClassTeam> list = calTeamMapper.selectList();
        ArrayList<ClaTeamExcelVo> excelList = new ArrayList<>(list.size());
        list.forEach(dict -> {
            ClaTeamExcelVo excelDTO = new ClaTeamExcelVo();
            BeanUtils.copyProperties(dict, excelDTO);
            excelList.add(excelDTO);
        });
        return excelList;
    }

    @Override
    public ClassTeam findCalTeamByName(String teamName) {
        LambdaQueryWrapperX<ClassTeam> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ClassTeam::getTeamName,teamName);
        return calTeamMapper.selectOne(queryWrapperX);
    }

    @Override
    public ClassTeam findCalTeamByCode(String code) {
        LambdaQueryWrapperX<ClassTeam> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ClassTeam::getTeamCode,code);
        return calTeamMapper.selectOne(queryWrapperX);
    }

    @Override
    public List<ClassTeam> findCalTeamByids(List<Long> ids) {
        return calTeamMapper.selectBatchIds(ids);
    }
}
