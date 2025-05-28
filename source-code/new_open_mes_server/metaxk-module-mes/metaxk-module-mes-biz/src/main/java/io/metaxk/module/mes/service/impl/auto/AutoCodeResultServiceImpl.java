package io.metaxk.module.mes.service.impl.auto;

import io.metaxk.module.mes.dal.dataobject.auto.AutoCodeResult;
import io.metaxk.module.mes.dal.mysql.auto.AutoCodeResultMapper;
import io.metaxk.module.mes.service.auto.AutoCodeResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AutoCodeResultServiceImpl implements AutoCodeResultService {

    @Resource
    private AutoCodeResultMapper sysAutoCodeResultMapper;

    @Override
    public List<AutoCodeResult> list(AutoCodeResult sysAutoCodeResult) {
        return sysAutoCodeResultMapper.selectSysAutoCodeResultList(sysAutoCodeResult);
    }

    @Override
    public void saveAutoCodeResult(AutoCodeResult sysAutoCodeResult) {
        sysAutoCodeResultMapper.insert(sysAutoCodeResult);
    }

    @Override
    public void updateAutoCodeResult(AutoCodeResult sysAutoCodeResult) {
        sysAutoCodeResultMapper.updateById(sysAutoCodeResult);
    }
}
