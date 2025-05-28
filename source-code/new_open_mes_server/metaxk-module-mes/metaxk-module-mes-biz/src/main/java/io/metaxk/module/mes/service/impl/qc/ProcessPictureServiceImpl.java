package io.metaxk.module.mes.service.impl.qc;

import io.metaxk.module.mes.dal.dataobject.qc.ProcessPicture;
import io.metaxk.module.mes.dal.mysql.qc.ProcessPictureMapper;
import io.metaxk.module.mes.service.qc.ProcessPictureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 万界星空
 * @time 2023/7/11 17:34
 */
@Service
public class ProcessPictureServiceImpl implements ProcessPictureService {

    @Resource
    private ProcessPictureMapper processPictureMapper;


    @Override
    public void insertProcessPicture(ProcessPicture processPicture) {
        processPictureMapper.insert(processPicture);
    }
}
