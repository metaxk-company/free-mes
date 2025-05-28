package io.metaxk.module.mes.service.qc;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.qc.vo.ProcessRecordExcelVo;
import io.metaxk.module.mes.controller.admin.qc.vo.ProcessRecordQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.ProcessRecord;
import io.metaxk.module.mes.dal.dataobject.qc.ReceiveRecord;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/10 13:33
 */
public interface ProcessRecordService {

    /**
     *  工序质检单条件分页查询
     * @param processRecordQueryVo
     * @return PageResult<ProcessRecord>
     */
    PageResult<ProcessRecord> findPage(ProcessRecordQueryVo processRecordQueryVo);


    /**
     * 工序质检单导出
     * @return List<ProcessRecordExcelVo>
     */
    List<ProcessRecordExcelVo> exportProcessFormDate();


    ProcessRecord selectByIdAndCodeAndName(Long id, String processCode);

    void updateProcessForm(ProcessRecord processRecord);

    void updateStatus(Long id,String status);

    ProcessRecord findProcessFormById(Long id);

    List<ProcessRecord> findProcessRecordByProcessCode(String  processCode);

    Integer updateProcessRecords(ProcessRecord processRecord);

    /**
     * 图片上传
     * @param mfs
     */
    void uploadPicture(MultipartFile[] mfs, Long id);

}
