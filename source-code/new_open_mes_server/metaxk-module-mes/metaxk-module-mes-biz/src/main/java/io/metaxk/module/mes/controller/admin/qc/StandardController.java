package io.metaxk.module.mes.controller.admin.qc;

import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.qc.vo.StandardQueryVo;
import io.metaxk.module.mes.controller.admin.qc.vo.StandardVo;
import io.metaxk.module.mes.dal.dataobject.pro.Process;
import io.metaxk.module.mes.dal.dataobject.qc.ProcessRecord;
import io.metaxk.module.mes.dal.dataobject.qc.ReceiveRecord;
import io.metaxk.module.mes.dal.dataobject.qc.Standard;
import io.metaxk.module.mes.dal.dataobject.qc.StandardDetail;
import io.metaxk.module.mes.service.pro.ProcessService;
import io.metaxk.module.mes.service.qc.ProcessRecordService;
import io.metaxk.module.mes.service.qc.StandardDetailService;
import io.metaxk.module.mes.service.qc.StandardService;
import io.metaxk.module.mes.utils.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.PROCESS_STANDARD_ENABLED;




/**
 * @author 万界星空
 * @time 2023/7/6 13:44
 */
@Tag(name = "管理后台 - 工序检验标准")
@RestController
@RequestMapping("/qc/inspect/standard")
public class StandardController {

    @Resource
    private StandardService standardService;

    @Resource
    private StandardDetailService standardDetailService;

    @Resource
    private ProcessService processService;
    @Resource
    private ProcessRecordService processRecordService;





    @GetMapping("/list")
    @Operation(summary = "工序检验标准列表")
    public CommonResult<PageResult<Standard>> list(StandardQueryVo inspectStandardPageReqVO){
       PageResult<Standard> pageResult = standardService.findPage(inspectStandardPageReqVO);
       return success(pageResult);
    }







    @PostMapping("/save")
    @Operation(summary = "新增工序检验标准")
    public CommonResult<Integer> save(@RequestBody Standard standard){
      List<Standard> standardList =   standardService.findStandardList();
      if(!standardList.isEmpty()){
          for(Standard standard1:standardList){
              String processCode = standard1.getProcessCode();
              String[] processArray = processCode.split(",");
              for (String code : processArray) {
                  List<String> processCodeList1 = standard.getProcessCodeList();
                  for(String processCodes:processCodeList1){
                      List<Standard> standardList1 = standardService.findStandardByProcessCode(code);
                      for(Standard standard2:standardList1) {
                          System.out.println("===============输出flag============" + standard2.getEnableFlag());
                          if (code.equals(processCodes) && "true".equals(standard2.getEnableFlag())) {
                              throw exception(PROCESS_STANDARD_ENABLED);
                          }
                      }
                  }
                  }
          }
      }
        StringBuilder processCodes = new StringBuilder();
        StringBuilder itemTypeCodes = new StringBuilder();
        StringBuilder processNames = new StringBuilder();
        List<String> processCodeList = standard.getProcessCodeList();

        for (String processCode : processCodeList) {
            processCodes.append(processCode).append(",");
            Process process = processService.findProcessByCode(processCode);
            if(process != null) {
                processNames.append(process.getProcessName()).append(",");
            }
        }
        if (processNames.length() > 0) {
            processNames.deleteCharAt(processNames.length() - 1);
        }
        standard.setProcessName(processNames.toString());
        //将最后一个逗号去除
        if (processCodes.length() > 0) {
            processCodes.deleteCharAt(processCodes.length() - 1);
        }
        standard.setProcessCode(processCodes.toString());
        /*List<String> itemTypeList = standard.getItemTypeList();
        for (String itemTypeCode : itemTypeList) {
            itemTypeCodes.append(itemTypeCode).append(",");
        }
        //将最后一个逗号去除
        if (itemTypeCodes.length() > 0) {
            itemTypeCodes.deleteCharAt(itemTypeCodes.length() - 1);
        }
        standard.setItemType(itemTypeCodes.toString());*/
        List<StandardVo> inspectDeviceList = standard.getInspectDeviceList();
            for (StandardVo standardVo : inspectDeviceList) {
                if (StringUtils.isNotBlank(standardVo.getItemDevice()) && StringUtils.isNotBlank(standardVo.getItemName()) && StringUtils.isNotBlank(standardVo.getItemStandard()) && StringUtils.isNotBlank(standardVo.getRemark())) {
                    StandardDetail standardDetail = new StandardDetail();
                    standardDetail
                            .setEnableFlag(standard.getEnableFlag())
                            .setProcessCode(standard.getProcessCode())
                            .setProcessStandardCode(standard.getInspectCode())
                            .setDetectionName(standardVo.getItemName())
                            .setDetectionStandard(standardVo.getItemStandard())
                            .setDetectionDevice(standardVo.getItemDevice())
                            .setRemark(standardVo.getRemark());
                    standardDetailService.saveStandardDetail(standardDetail);
                }
            }

        standardService.saveInspectStandard(standard);

        //更新未质检的工序编号对应的工序检验单信息。
        for (String processCode : processCodeList) {
            //查询工序编号对应的来料检验单信息
            List<ProcessRecord> processRecordList = processRecordService.findProcessRecordByProcessCode(processCode);
            System.out.println("==========bbbbb=====processRecordList:"+processRecordList);
            for (ProcessRecord processRecord : processRecordList){
                if(processRecord != null && "0".equals(processRecord.getStatus())){//未质检
                    processRecordService.updateProcessRecords(processRecord);
                }
            }
        }

        return success(200).setMsg("新增成功");
    }





    @DeleteMapping("/batch")
    @Operation(summary = "删除工序检验标准")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids){
        for(Long id:ids){
            Standard stand = standardService.findStandardById(id);
            standardDetailService.removeCode(stand.getInspectCode());
        }
        return success( standardService.removeInspectStandard(ids)).setMsg("删除成功");
    }







    @GetMapping("/find/{id}")
    @Operation(summary = "查询工序检验标准详情")
    public CommonResult<Standard> findInspectStandard(@PathVariable Long id){
        Standard stand = standardService.findStandardById(id);
        String processCode = stand.getProcessCode();
        String[] processArray = processCode.split(",");
        stand.setProcessCodeList(Arrays.asList(processArray));

        /*String itemType = stand.getItemType();
        String[] itemTypeArray = itemType.split(",");
        stand.setItemTypeList(Arrays.asList(itemTypeArray));*/

        String inspectCode = stand.getInspectCode();
        List<StandardDetail> standardDetailList =   standardDetailService.findStandardDetailByCode(inspectCode);
        ArrayList<StandardVo> arrayList = new ArrayList<>();
        for(StandardDetail standardDetail:standardDetailList){
            StandardVo standardVo = new StandardVo();
            standardVo.setItemName(standardDetail.getDetectionName());
            standardVo.setItemStandard(standardDetail.getDetectionStandard());
            standardVo.setItemDevice(standardDetail.getDetectionDevice());
            standardVo.setRemark(standardDetail.getRemark());
            arrayList.add(standardVo);
        }
        stand.setInspectDeviceList(arrayList);
        return success(stand);
    }





    @PutMapping("/update")
    @Operation(summary = "修改工序检验标准")
    public CommonResult<Integer> update(@RequestBody Standard standard){
        List<Standard> standardList =  standardService.findStandardList();
        if(!standardList.isEmpty()){
            for(Standard standDo:standardList){
                String[] processArray = standDo.getProcessCode().split(",");
                for (String code : processArray) {
                    //遍历时取出传递过来的工序集合
                    List<String> processCodeList = standard.getProcessCodeList();
                    for(String processCodes:processCodeList){
                        List<Standard> standardList1 = standardService.findStandardByProcessCode(code);
                        for(Standard standard2:standardList1) {
                            //获取当前信息的id，判断当前这条信息是否为true，如果为true不需要执行异常抛出语句
                            Standard stand = standardService.findStandardById(standard.getId());
                            if(!"true".equals(stand.getEnableFlag())) {
                                if (code.equals(processCodes) && "true".equals(standard2.getEnableFlag())) {
                                    throw exception(PROCESS_STANDARD_ENABLED);
                                }
                            }
                        }
                    }
                }
            }
        }

        List<String> processCodeList = standard.getProcessCodeList();
        if (processCodeList != null){
            StringBuilder processCodes = new StringBuilder();
            StringBuilder processNames = new StringBuilder();
            for (String processCode : processCodeList) {
                processCodes.append(processCode).append(",");
                Process process = processService.findProcessByCode(processCode);
                if(process != null) {
                    processNames.append(process.getProcessName()).append(",");
                }
            }
            if (processNames.length() > 0) {
                processNames.deleteCharAt(processNames.length() - 1);
            }
            standard.setProcessName(processNames.toString());
            //将最后一个逗号去除
            if (processCodes.length() > 0) {
                processCodes.deleteCharAt(processCodes.length() - 1);
            }
            standard.setProcessCode(processCodes.toString());
        }

        /*List<String> itemTypeList = standard.getItemTypeList();
        if (itemTypeList != null){
            StringBuilder itemTypeCodes = new StringBuilder();
            for (String itemTypeCode : itemTypeList) {
                itemTypeCodes.append(itemTypeCode).append(",");
            }
            //将最后一个逗号去除
            if (itemTypeCodes.length() > 0) {
                itemTypeCodes.deleteCharAt(itemTypeCodes.length() - 1);
            }
            standard.setItemType(itemTypeCodes.toString());
        }*/

        //先删除，再添加
        standardDetailService.removeCode(standard.getInspectCode());
        List<StandardVo> inspectDeviceList = standard.getInspectDeviceList();
        for(StandardVo standardVo:inspectDeviceList){
            //if( standardDetailService.findStandardDetailByCondition(standardVo.getItemName(),standardVo.getItemStandard(),standardVo.getItemDevice(),standard.getInspectCode()) == null){
                StandardDetail detail = new StandardDetail();
                   detail
                        .setProcessStandardCode(standard.getInspectCode())
                        .setDetectionName(standardVo.getItemName())
                        .setDetectionStandard(standardVo.getItemStandard())
                        .setDetectionDevice(standardVo.getItemDevice())
                        .setRemark(standardVo.getRemark())
                        .setProcessCode(standard.getProcessCode());
                standardDetailService.saveStandardDetail(detail);
           //}
        }

        standardService.updateInspectStandard(standard);

        if (processCodeList != null){
            //更新未质检的工序编号对应的工序检验单信息。
            for (String processCode : processCodeList) {
                //查询工序编号对应的来料检验单信息
                List<ProcessRecord> processRecordList = processRecordService.findProcessRecordByProcessCode(processCode);
                System.out.println("==========bbbbb=====processRecordList:"+processRecordList);
                for (ProcessRecord processRecord : processRecordList){
                    if(processRecord != null && "0".equals(processRecord.getStatus())){//未质检
                        processRecordService.updateProcessRecords(processRecord);
                    }
                }
            }
        }

        return success(200).setMsg("修改成功");
    }






    @DeleteMapping("/remove/{deviceCode}/{standardCode}")
    @Operation(summary = "根据器具编号与工序检验标准编号删除检测标准中的检测项")
    public CommonResult<Integer> removeStandDevice(@PathVariable String deviceCode,@PathVariable String standardCode){
        return success( standardDetailService.removeDetailByCode(deviceCode,standardCode)).setMsg("移除成功");
    }


}
