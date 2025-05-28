package io.metaxk.module.mes.service.impl.qc;

import io.metaxk.module.mes.controller.admin.qc.vo.OtherStandardResultVo;
import io.metaxk.module.mes.dal.mysql.qc.OtherRecordResultMapper;
import io.metaxk.module.mes.service.qc.OtherRecordResultService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 万界星空
 * @time 2023/8/19 14:28
 */
@Service
public class OtherRecordResultServiceImpl implements OtherRecordResultService {

    @Resource
    private OtherRecordResultMapper otherRecordResultMapper;

    @Override
    public OtherStandardResultVo getAcount(OtherStandardResultVo otherStandardResultVo) {
        return otherRecordResultMapper.getAcount(otherStandardResultVo);
    }
}
