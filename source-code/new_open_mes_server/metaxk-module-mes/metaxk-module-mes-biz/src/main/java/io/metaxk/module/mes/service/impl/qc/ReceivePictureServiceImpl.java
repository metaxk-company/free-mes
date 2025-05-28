package io.metaxk.module.mes.service.impl.qc;

import io.metaxk.module.mes.dal.dataobject.qc.ReceivePicture;
import io.metaxk.module.mes.dal.mysql.qc.ReceivePictureMapper;
import io.metaxk.module.mes.service.qc.ReceivePictureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 万界星空
 * @time 2023/7/26 14:59
 */
@Service
public class ReceivePictureServiceImpl implements ReceivePictureService {


    @Resource
    private ReceivePictureMapper receivePictureMapper;

    @Override
    public Integer insertProcessPicture(ReceivePicture receivePicture) {
        return receivePictureMapper.insert(receivePicture);
    }
}
