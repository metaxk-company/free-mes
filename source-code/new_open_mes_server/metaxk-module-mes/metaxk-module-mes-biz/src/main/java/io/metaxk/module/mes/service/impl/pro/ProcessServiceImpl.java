package io.metaxk.module.mes.service.impl.pro;

import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.pro.vo.*;
import io.metaxk.module.mes.dal.dataobject.pro.Process;
import io.metaxk.module.mes.dal.mysql.pro.ProcessMapper;
import io.metaxk.module.mes.service.pro.ProcessService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;



/**
 * 生产工序 Service 实现类
 */
@Service
public class ProcessServiceImpl implements ProcessService {

    @Resource
    private ProcessMapper processMapper;


    @Override
    public Process findProcessById(Long id) {
        return processMapper.selectById(id);
    }



    @Override
    public PageResult<Process> getProcessPage(ProcessQueryVo pageReqVO) {
        return processMapper.selectPage(pageReqVO);
    }



    @Override
    public Integer insertProcess(Process processDO) {
        processDO.setCreateTime(new Date());
        return processMapper.insert(processDO);
    }

    @Override
    public Integer updateProcess(Process process) {
        Process processDO = processMapper.selectById(process.getId());
        process.setCreateTime(processDO.getCreateTime());
        process.setUpdateTime(new Date());
        return processMapper.updateById(process);
    }

    @Override
    public Integer removesProcess(List<Long> ids) {
      return   processMapper.deleteBatchIds(ids);
    }

    @Override
    public Process selectProProcessByProcessId(Long processId) {
        return processMapper.selectById(processId);
    }


    @Override
    public List<ProcessExcelVo> listData() {
        List<Process> dictList = processMapper.selectList();
        ArrayList<ProcessExcelVo> excelDictDTOList = new ArrayList<>(dictList.size());
        dictList.forEach(dict -> {
            ProcessExcelVo excelDictDTO = new ProcessExcelVo();
            BeanUtils.copyProperties(dict, excelDictDTO);
            excelDictDTOList.add(excelDictDTO);
        });
        return excelDictDTOList;
    }

//
//    @Override
//    public List<Process> selectProProcessList(Process process) {
//        return processMapper.selectList();
//    }

    @Override
    public Process findProcessByCode(String processCode) {
        LambdaQueryWrapperX<Process> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Process::getProcessCode,processCode);
        return processMapper.selectOne(queryWrapperX);
    }

    @Override
    public Process findProcessByName(String processName) {
        LambdaQueryWrapperX<Process> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Process::getProcessName,processName);
        return processMapper.selectOne(queryWrapperX);
    }

    @Override
    public List<Process> findAvailableList() {
        LambdaQueryWrapperX<Process> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Process::getEnableFlag,"Y");
        return processMapper.selectList(queryWrapperX);
    }

}
