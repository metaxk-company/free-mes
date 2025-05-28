package io.metaxk.module.mes.service.impl.qc;

import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.dal.dataobject.qc.Standard;
import io.metaxk.module.mes.dal.dataobject.qc.StandardDetail;
import io.metaxk.module.mes.dal.mysql.qc.StandardDetailMapper;
import io.metaxk.module.mes.service.qc.StandardDetailService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;


/**
 * @author 万界星空
 * @time 2023/7/11 17:19
 */
@Service
public class StandardDetailImpl implements StandardDetailService {

    @Resource
    private StandardDetailMapper standardDetailMapper;

    @Override
    public Integer saveStandardDetail(StandardDetail standardDetail) {
        return standardDetailMapper.insert(standardDetail);
    }

    @Override
    public Integer removeCode(String inspectCode) {
        LambdaQueryWrapperX<StandardDetail> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(StandardDetail::getProcessStandardCode,inspectCode);
        return standardDetailMapper.delete(queryWrapperX);
    }

    @Override
    public List<StandardDetail> findStandardDetailByCode(String inspectCode) {
        LambdaQueryWrapperX<StandardDetail> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(StandardDetail::getProcessStandardCode,inspectCode);
        return standardDetailMapper.selectList(queryWrapperX);
    }

    @Override
    public Integer removeDetailByCode(String deviceCode,String standardCode) {
        LambdaQueryWrapperX<StandardDetail> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(StandardDetail::getDetectionDevice,deviceCode);
        queryWrapperX.eq(StandardDetail::getProcessStandardCode,standardCode);
        return standardDetailMapper.delete(queryWrapperX);
    }

    @Override
    public StandardDetail findStandardDetailByCondition(String inspectName, String inspectStand, String inspectDevice, String inspectCode) {
        LambdaQueryWrapperX<StandardDetail> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(StandardDetail::getProcessStandardCode,inspectCode);
        queryWrapperX.eq(StandardDetail::getDetectionName,inspectName);
        queryWrapperX.eq(StandardDetail::getDetectionStandard,inspectStand);
        queryWrapperX.eq(StandardDetail::getDetectionDevice,inspectDevice);
        return standardDetailMapper.selectOne(queryWrapperX);
    }


    @Override
    public List<StandardDetail> findStandardByProcessCodeAndEnableFlag(String code, String enableFlag) {
        LambdaQueryWrapperX<StandardDetail> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.like(StandardDetail::getProcessCode, code);
        queryWrapperX.eq(StandardDetail::getEnableFlag,enableFlag);
        return standardDetailMapper.selectList(queryWrapperX);
    }
}
