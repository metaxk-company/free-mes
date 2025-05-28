package io.metaxk.module.mes.service.impl.qc;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.qc.vo.StandardQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.Standard;
import io.metaxk.module.mes.dal.mysql.qc.StandardMapper;
import io.metaxk.module.mes.service.qc.StandardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/6 13:43
 */
@Service
public class StandardServiceImpl implements StandardService {

    @Resource
    private StandardMapper standardMapper;

    @Override
    public Integer saveInspectStandard(Standard inspectStandard) {
        return standardMapper.insert(inspectStandard);
    }

    @Override
    public Integer removeInspectStandard(List<Long> ids) {
        return standardMapper.deleteBatchIds(ids);
    }

    @Override
    public Standard findStandardById(Long id) {
        return standardMapper.selectById(id);
    }

    @Override
    public Integer updateInspectStandard(Standard inspectStandard) {
        return standardMapper.updateById(inspectStandard);
    }

    @Override
    public PageResult<Standard> findPage(StandardQueryVo standardQueryVo) {
        return standardMapper.findPage(standardQueryVo);
    }

    @Override
    public List<Standard> findStandardList() {
        return standardMapper.selectList();
    }

    @Override
    public List<Standard> findStandardByProcessCode(String code) {
        LambdaQueryWrapperX<Standard> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.like(Standard::getProcessCode, code);
        return standardMapper.selectList(queryWrapperX);
    }
}
