package io.metaxk.module.mes.service.impl.qc;

import io.metaxk.module.mes.dal.dataobject.qc.OtherPicture;
import io.metaxk.module.mes.dal.mysql.qc.OtherPictureMapper;
import io.metaxk.module.mes.service.qc.OtherPictureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 万界星空
 * @time 2023/8/19 10:28
 */
@Service
public class OtherPictureServiceImpl implements OtherPictureService {

    @Resource
    private OtherPictureMapper otherPictureMapper;


    @Override
    public Integer saveOtherPicture(OtherPicture otherPicture) {
        return otherPictureMapper.insert(otherPicture);
    }
}
