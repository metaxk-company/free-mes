package io.metaxk.module.mes.service.impl.auto;
import io.metaxk.module.mes.common.UserConstants;
import io.metaxk.module.mes.dal.dataobject.auto.AutoCodePart;
import io.metaxk.module.mes.dal.mysql.auto.AutoCodePartMapper;
import io.metaxk.module.mes.service.auto.AutoCodePartService;
import io.metaxk.module.mes.utils.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class AutoCodePartServiceImpl implements AutoCodePartService {

    @Resource
    private AutoCodePartMapper sysAutoCodePartMapper;

    @Override
    public List<AutoCodePart> listPart(AutoCodePart sysAutoCodePart) {
        return sysAutoCodePartMapper.selectSysAutoCodePartList(sysAutoCodePart);
    }

    @Override
    public AutoCodePart findById(Long partId) {
        return sysAutoCodePartMapper.findById(partId);
    }

    @Override
    public String checkPartUnique(AutoCodePart sysAutoCodePart) {
        AutoCodePart part = sysAutoCodePartMapper.checkPartUnique(sysAutoCodePart);
        Long partId = StringUtils.isNull(sysAutoCodePart.getPartId())?-1L:sysAutoCodePart.getPartId();
        if(StringUtils.isNotNull(part) && partId.longValue() != part.getPartId().longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public Integer insertPart(AutoCodePart sysAutoCodePart) {
        return sysAutoCodePartMapper.insert(sysAutoCodePart);
    }






}
