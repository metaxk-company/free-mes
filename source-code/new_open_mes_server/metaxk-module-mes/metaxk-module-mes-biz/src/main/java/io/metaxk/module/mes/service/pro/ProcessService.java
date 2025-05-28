package io.metaxk.module.mes.service.pro;

import java.util.*;
import io.metaxk.module.mes.controller.admin.pro.vo.*;
import io.metaxk.module.mes.dal.dataobject.pro.Process;
import io.metaxk.framework.common.pojo.PageResult;

/**
 * 生产工序 Service 接口
 * @author 万界星空
 */
public interface ProcessService {

    /**
     * 根据id查询工序
     * @param id
     * @return Process
     */
    Process findProcessById(Long id);


    /**
     * 工序条件分页查询
     * @param pageReqVO
     * @return  PageResult<Process>
     */
    PageResult<Process> getProcessPage(ProcessQueryVo pageReqVO);

    /**
     * 新增工序
     * @param createReqVO
     * @return Integer
     */
    Integer insertProcess(Process createReqVO);

    /**
     * 修改工序
     * @param updateReqVO
     * @return Integer
     */
    Integer updateProcess(Process updateReqVO);

    /**
     * 根据id删除工序
     * @param ids
     * @return Integer
     */
    Integer removesProcess(List<Long> ids);

    /**
     * 根据工序id查询工序
     * @param processId
     * @return Process
     */
    Process selectProProcessByProcessId(Long processId);

    /**
     * 导出工序
     * @return List<ProcessExcelVO>
     */
    List<ProcessExcelVo> listData();

    /**
     * 工序列表
     * @param process
     * @return  List<Process>
     */
   // List<Process> selectProProcessList(Process process);

    /**
     * 根据工序编号查询工序
     * @param processCode
     * @return Process
     */
    Process findProcessByCode(String processCode);

    /**
     * 根据工序名称查询工序
     * @param processName
     * @return Process
     */
    Process findProcessByName(String processName);

    List<Process> findAvailableList( );
}
