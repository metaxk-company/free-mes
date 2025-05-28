package io.metaxk.module.mes.service.impl.qc;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.qc.vo.ReceiveStandardQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.ReceiveStandard;
import io.metaxk.module.mes.dal.dataobject.qc.Standard;
import io.metaxk.module.mes.dal.mysql.qc.ReceiveStandardMapper;
import io.metaxk.module.mes.service.qc.ReceiveStandardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/26 11:01
 */
@Service
public class ReceiveStandardServiceImpl implements ReceiveStandardService {

    @Resource
    private ReceiveStandardMapper receiveStandardMapper;

    @Override
    public PageResult<ReceiveStandard> findPage(ReceiveStandardQueryVo receiveStandardQueryVo) {
        return receiveStandardMapper.findPage(receiveStandardQueryVo);
    }

    @Override
    public List<ReceiveStandard> findReceiveStandardList() {
        return receiveStandardMapper.selectList();
    }

    @Override
    public List<ReceiveStandard> findReceiveStandardByItemCode(String itemCode) {
        return receiveStandardMapper.findReceiveStandardByItemCode(itemCode);
    }

    @Override
    public Integer saveReceiveStandard(ReceiveStandard receiveStandard) {
        return receiveStandardMapper.insert(receiveStandard);
    }

    @Override
    public ReceiveStandard findReceiveStandardById(Long id) {
        return receiveStandardMapper.selectById(id);
    }

    @Override
    public Integer updateReceiveStandard(ReceiveStandard receiveStandard) {
        return receiveStandardMapper.updateById(receiveStandard);
    }

    @Override
    public Integer removeReceiveStandard(List<Long> ids) {
        return receiveStandardMapper.deleteBatchIds(ids);
    }

    @Override
    public ReceiveStandard findReceiveStandardByItemCode(String itemCode, String enableFlag) {
        LambdaQueryWrapperX<ReceiveStandard> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.like(ReceiveStandard::getItemCode, itemCode);
        queryWrapperX.eq(ReceiveStandard::getEnableFlag, enableFlag);
        return receiveStandardMapper.selectOne(queryWrapperX);
    }
}
