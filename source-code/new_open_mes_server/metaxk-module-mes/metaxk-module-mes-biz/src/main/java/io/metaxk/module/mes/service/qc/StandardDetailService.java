package io.metaxk.module.mes.service.qc;

import io.metaxk.module.mes.dal.dataobject.qc.StandardDetail;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/11 17:19
 */
public interface StandardDetailService {

    Integer saveStandardDetail(StandardDetail standardDetail);

    Integer removeCode(String inspectCode);

    List<StandardDetail> findStandardDetailByCode(String inspectCode);


     Integer removeDetailByCode(String deviceCode,String standardCode);


    StandardDetail findStandardDetailByCondition(String inspectName, String inspectStand, String inspectDevice, String inspectCode);

    List<StandardDetail> findStandardByProcessCodeAndEnableFlag(String code,String enableFlag);
}
