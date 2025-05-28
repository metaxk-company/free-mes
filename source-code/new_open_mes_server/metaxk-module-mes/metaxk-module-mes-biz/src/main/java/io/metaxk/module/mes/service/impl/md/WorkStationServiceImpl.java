package io.metaxk.module.mes.service.impl.md;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.common.UserConstants;
import io.metaxk.module.mes.controller.admin.md.vo.*;
import io.metaxk.module.mes.dal.dataobject.md.WorkShop;
import io.metaxk.module.mes.dal.dataobject.md.WorkStation;
import io.metaxk.module.mes.dal.dataobject.pro.Process;
import io.metaxk.module.mes.dal.mysql.md.WorkShopMapper;
import io.metaxk.module.mes.dal.mysql.md.WorkStationMapper;
import io.metaxk.module.mes.dal.mysql.pro.ProcessMapper;
import io.metaxk.module.mes.service.md.WorkStationService;
import io.metaxk.module.mes.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 工作站 Service 实现类
 */
@Service
public class WorkStationServiceImpl extends ServiceImpl<WorkStationMapper, WorkStation> implements WorkStationService {

    @Resource
    private WorkStationMapper workstationMapper;

    @Resource
    private WorkShopMapper workShopMapper;


    @Resource
    private ProcessMapper processMapper;




    @Override
    public WorkStation getWorkstation(Long id) {
        return workstationMapper.selectById(id);
    }



    @Override
    public PageResult<WorkStation> getWorkstationPage(WorkstationQueryVo pageReqVO) {
        return workstationMapper.selectPage(pageReqVO);
    }



    @Override
    public String checkWorkStationCodeUnique(WorkStation createReqVO) {
        WorkStation workstation = workstationMapper.checkWorkStationCodeUnique(createReqVO);
        Long workstationId = createReqVO.getId()==null? -1L:createReqVO.getId();
        if(StringUtils.isNotNull(workstation) && workstation.getId().longValue() != workstationId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkWorkStationNameUnique(WorkStation createReqVO) {
        WorkStation workstation = workstationMapper.checkWorkStationNameUnique(createReqVO);
        Long workstationId = createReqVO.getId()==null? -1L:createReqVO.getId();
        if(StringUtils.isNotNull(workstation) && workstation.getId().longValue() != workstationId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public Integer insert(WorkStation workStation) {
        WorkShop workShop = workShopMapper.selectById(workStation.getWorkshopId());
        Process process = processMapper.selectById(workStation.getProcessId());
        workStation.setWorkshopCode(workShop.getWorkshopCode()).setWorkshopName(workShop.getWorkshopName());
        workStation.setProcessCode(process.getProcessCode()).setProcessName(process.getProcessName());
        return workstationMapper.insert(workStation);
    }

    @Override
    public Integer updateWorkstations(WorkStation createReqVO) {
        createReqVO.setUpdateTime(new Date());
        return workstationMapper.updateById(createReqVO);
    }

    @Override
    public WorkStation selectWorkstationById(Long workstationId) {
        return workstationMapper.selectById(workstationId);
    }

    @Override
    public List<WorkstationExcelVo> listData() {
        List<WorkStation> dictList = workstationMapper.selectList();
        ArrayList<WorkstationExcelVo> excelDictDTOList = new ArrayList<>(dictList.size());
        dictList.forEach(dict -> {
            WorkstationExcelVo excelDictDTO = new WorkstationExcelVo();
            BeanUtils.copyProperties(dict, excelDictDTO);
            excelDictDTOList.add(excelDictDTO);
        });
        return excelDictDTOList;
    }






    @Override
    public WorkStation selectMdWorkstationByWorkstationId(Long workstationId) {
        return workstationMapper.selectById(workstationId);
    }



    @Override
    public List<WorkStation> findWorkStationByProcessId(Long processId) {
        LambdaQueryWrapperX<WorkStation> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(WorkStation::getProcessId,processId);
        return workstationMapper.selectList(queryWrapperX);
    }


}
