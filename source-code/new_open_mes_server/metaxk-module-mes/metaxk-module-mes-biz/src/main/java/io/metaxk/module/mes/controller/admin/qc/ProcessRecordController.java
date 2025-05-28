package io.metaxk.module.mes.controller.admin.qc;

import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.file.core.client.FileClientFactory;
import io.metaxk.module.mes.controller.admin.qc.vo.*;
import io.metaxk.module.mes.dal.dataobject.qc.*;
import io.metaxk.module.mes.service.qc.*;
import io.metaxk.module.mes.utils.AutoCodeUtil;
import io.metaxk.module.mes.utils.DateUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.EXPORT_DATA_ERROR;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static io.metaxk.framework.common.pojo.CommonResult.success;

/**
 * @author 万界星空
 * @time 2023/7/10 13:34
 */
@Tag(name = "管理后台 - 工序检验单")
@RestController
@RequestMapping("/mes/qc/process/form")
public class ProcessRecordController {


    @Resource
    private ProcessRecordService processFormService;

    @Resource
    private ProcessRecordItemService processFormCompareService;

    @Resource
    private ProcessRecordResultService processFormResultService;

    @Resource
    private ProcessPictureService processPictureService;

    @Resource
    private StandardService standardService;

    @Resource
    private StandardDetailService standardDetailService;

    @Resource
    private DeviceService deviceService;

    @Resource
    private AutoCodeUtil autoCodeUtil;

    /**
     * 文件上传服务
     */
    @Resource
    private FileClientFactory fileClientFactory;

    @GetMapping("/list")
    public CommonResult list(ProcessRecordQueryVo processFormQueryVo){
       PageResult<ProcessRecord> pageResult = processFormService.findPage(processFormQueryVo);
       return success(pageResult);
    }



    @GetMapping("/exportProcessForm")
    @Operation(summary = "工序检验单导出")
    public void exportProcessForm(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("工序检验单", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), ProcessRecordExcelVo.class).registerWriteHandler(styleStrategy).sheet("工序检验单").doWrite(processFormService.exportProcessFormDate());
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }

    @GetMapping("/qualityInspectionShow1")
    @Operation(summary = "质检页面-有检验标准-回显")
    public CommonResult qualityInspectionShow1(ProcessRecordVo processRecordVo) throws ParseException {

        //添加质检员
        ProcessRecord pr = new ProcessRecord();
        pr.setId(processRecordVo.getId());
        pr.setInspectUser(processRecordVo.getInspectUser());
        processFormService.updateProcessForm(pr);

        //根据工序检验单id，工序编码，工序名称查询工序检验单信息
        ProcessRecord processRecord =  processFormService.selectByIdAndCodeAndName(processRecordVo.getId(),processRecordVo.getProcessCode());

        //自动生成工序条码
       // String processBarCode = autoCodeUtil.genSerialCode(UserConstants.PROCESS_FORM_CODE, null);

        //查询工序检验对比表，获取工序检验单下已经质检了多少个。
        int count = processFormCompareService.getCount(processRecordVo.getId());

        String maxSortNumber = processFormCompareService.selectMaxDetectionOrderNumber(processRecordVo.getId());//最新检测到第几个
        //根据工序编号查询已启用的工序检验标准
        List<ProcessRecordItemsVo> list = processFormCompareService.getProcessFormCompares(processRecordVo.getId(),processRecordVo.getProcessCode(),maxSortNumber);


        Map map = new HashMap<>();
        map.put("processRecord",processRecord);
        map.put("processRecordItemList",list);
        map.put("number",String.valueOf(count+1));

        return success(map);
    }



    @GetMapping("/qualityInspectionShow2")
    @Operation(summary = "质检页面-无检验标准-回显")
    public CommonResult qualityInspectionShow2(ProcessRecordVo processRecordVo){

        //添加质检员
        ProcessRecord pr = new ProcessRecord();
        pr.setId(processRecordVo.getId());
        pr.setInspectUser(processRecordVo.getInspectUser());
        processFormService.updateProcessForm(pr);

        //查询工序检验对比表，获取工序检验单下已经质检了多少个。
        int count = processFormCompareService.getCount(processRecordVo.getId());
        //根据工序检验单id，工序编码，工序名称查询工序检验单信息
        ProcessRecord processRecord =  processFormService.selectByIdAndCodeAndName(processRecordVo.getId(),processRecordVo.getProcessCode());
        Map map = new HashMap<>();
        map.put("processRecord",processRecord);
        map.put("number",String.valueOf(count+1));
        return success(map);
    }




    @GetMapping("/qualityInspection")
    @Operation(summary = "质检-有检验标准")
    public CommonResult qualityInspection(ProcessRecordItem processRecordItem) throws Exception{

        //通过检验项目id查询对应序列号的工序检验单对比信息
        ProcessRecordItem processRecordItem1  = processFormCompareService.selectRecordItemByStandardItemId(processRecordItem.getRecordId(),processRecordItem.getStandardItemId(),processRecordItem.getSortNumber());

        //前端需要传序列号sortNumber，是前端的当前进度数。
        if (processRecordItem1 == null){
            //添加工序检验单对比信息
            processRecordItem.setFlag("0");//是否检验,默认0，点击下一个后再修改成1.
            processFormCompareService.insertProcessFormCompare(processRecordItem);
        }else {
            //修改
            processRecordItem1.setItemValue(processRecordItem.getItemValue());
            processRecordItem1.setStatus(processRecordItem.getStatus());
            processRecordItem1.setFlag("0");
            processFormCompareService.updateProcessRecordItem(processRecordItem1);
        }

        return success(200).setMsg("质检成功");
    }

    @GetMapping("/qualityInspectionNext")
    @Operation(summary = "质检-有检验标准-下一个")
    public CommonResult qualityInspectionNext(ProcessRecordVo processFormVo) throws ParseException {

        ProcessRecord p = processFormService.findProcessFormById(processFormVo.getId());

        if(processFormVo.getInspectTime() != null && !"".equals(processFormVo.getInspectTime())){
            Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(processFormVo.getInspectTime());
            p.setInspectTime(d1);
        }
        if(processFormVo.getInspectStartTime() != null && !"".equals(processFormVo.getInspectStartTime())){
            Date d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(processFormVo.getInspectStartTime());
            p.setInspectStartTime(d2);
        }

        //更新质检时间，工序检验开始时间。
        processFormService.updateProcessForm(p);

        //添加上一个工序检验单结果信息
        addCompare(p,processFormVo.getNum(),processFormVo.getProductBarcode(),"1");

        //根据工序检验单id，工序编码，工序名称查询工序检验单信息
        ProcessRecord processRecord =  processFormService.selectByIdAndCodeAndName(p.getId(),p.getProcessCode());

        //查询工序检验对比表，获取工序检验单下已经质检了多少个。
        int count = processFormCompareService.getCount(p.getId());

        //检测标准（查询是否存在已启用的工序检验标准对应工序的检测项目）
        //根据工序编号查询已启用的工序检验标准
        String enableFlag = "true";
        List<StandardDetail> standardDetailList = standardDetailService.findStandardByProcessCodeAndEnableFlag(p.getProcessCode(),enableFlag);
        for (StandardDetail standardDetail:standardDetailList){
            if (standardDetail != null){
                Device d  = deviceService.findDeviceByCode(standardDetail.getDetectionDevice());
                if (d != null){
                    String name = d.getDeviceName();
                    standardDetail.setDetectionDevice(name);
                }else{
                    standardDetail.setDetectionDevice("");
                }

            }else{
                standardDetail.setDetectionDevice("");
            }

        }

        Map map = new HashMap<>();
        map.put("processRecord",processRecord);
        List<ProcessRecordItem> processRecordItemList = new ArrayList<>();
        for (StandardDetail sdl:standardDetailList){
            ProcessRecordItem pri2 = new ProcessRecordItem();
            pri2.setStandardItemId(sdl.getId());
            pri2.setItemName(sdl.getDetectionName());
            pri2.setItemStandard(sdl.getDetectionStandard());
            pri2.setItemDevice(sdl.getDetectionDevice());
            pri2.setItemValue("");
            processRecordItemList.add(pri2);
        }

        map.put("processRecordItemList",processRecordItemList);
        map.put("number",String.valueOf(count+1));
        return success(map);
    }


    @GetMapping("/qualityInspectionFinish")
    @Operation(summary = "质检-有检验标准-完成")
    public CommonResult qualityInspectionFinish(ProcessRecordVo processRecordVo) throws Exception{

        ProcessRecord p = processFormService.findProcessFormById(processRecordVo.getId());

        if(processRecordVo.getInspectEndTime() != null && !"".equals(processRecordVo.getInspectEndTime())){
            Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(processRecordVo.getInspectEndTime());
            p.setInspectEndTime(d);
        }

        //更新工序检验结束时间。
        processFormService.updateProcessForm(p);

        //添加工序检验单结果信息
        addCompare(p,processRecordVo.getNum(),processRecordVo.getProductBarcode(),"1");

        //更新状态，为已质检
        processFormService.updateStatus(p.getId(),"1");

        //工序检验单结果明细,数据回显。
        List<ProcessRecordResult> processFormResultList1 = processFormResultService.selectByResultStatus(processRecordVo.getId(),"0");
        List<ProcessRecordResult> processFormResultList2 = processFormResultService.selectByResultStatus(processRecordVo.getId(),"1");
        //不合格数
        int unqualifiedNum = processFormResultList1.size();
        //合格数
        int qualifiedNum = processFormResultList2.size();
        //实际检验数量
        int inspectNum = unqualifiedNum + qualifiedNum;

        List<Map<String,String>> list = new ArrayList<>();
        Map map1 = new HashMap<>();
        map1.put("label","检验数量");
        map1.put("value",inspectNum);
        Map map2 = new HashMap<>();
        map2.put("label","合格数");
        map2.put("value",qualifiedNum);
        Map map3 = new HashMap<>();
        map3.put("label","不合格数");
        map3.put("value",unqualifiedNum);
        list.add(map1);
        list.add(map2);
        list.add(map3);
        return success(list);
    }



    @GetMapping("/qualityInspectionNoStandard")
    @Operation(summary = "质检-无检验标准")
    public CommonResult qualityInspectionNoStandard(ProcessRecordVo processFormVo) throws Exception{

        ProcessRecord p = processFormService.findProcessFormById(processFormVo.getId());

        if(processFormVo.getInspectTime() != null && !"".equals(processFormVo.getInspectTime())){
            Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(processFormVo.getInspectTime());
            p.setInspectTime(d1);
        }
        if(processFormVo.getInspectStartTime() != null && !"".equals(processFormVo.getInspectStartTime())){
            Date d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(processFormVo.getInspectStartTime());
            p.setInspectStartTime(d2);
        }

        //更新质检时间，工序检验开始时间。
        processFormService.updateProcessForm(p);

        //添加工序检验单对比信息
        ProcessRecordItem processRecordItem = new ProcessRecordItem();
        processRecordItem.setRecordId(processFormVo.getId());
        processRecordItem.setProductCode(processFormVo.getProductCode());//物料编码
        processRecordItem.setProductBarcode(processFormVo.getProductBarcode());//物料条码
        processRecordItem.setSortNumber(processFormVo.getNum());//序列号
        processRecordItem.setStatus(processFormVo.getStatus());
        processRecordItem.setFlag("1");//是否检验0：否1：是
        processFormCompareService.insertProcessFormCompare(processRecordItem);

        //修改上一个的的物料条码
        processFormCompareService.updateProcessRecordItem(p.getId(),processFormVo.getProductBarcode(),"1",processFormVo.getNum());

        ProcessRecordResult processRecordResult = new ProcessRecordResult();
        processRecordResult.setRecordId(p.getId());
        processRecordResult.setSortNumber(processFormVo.getNum());//序列号
        processRecordResult.setProductCode(p.getProductCode());//物料编号
        processRecordResult.setProcessCode(p.getProcessCode());//工序编号
        processRecordResult.setProcessName(p.getProcessName());
        processRecordResult.setDetectionNumber("1");//检测数量，默认为1.
        //首先查工序对应的检验标准是否有启用的版本，是启用则查询是否存在已启用的工序检验标准对应工序的检测项目，不存在inspectFlag赋值为0,存在inspectFlag赋值为1。
        //是禁用则直接inspectFlag赋值为0。
        processRecordResult.setInspectFlag(p.getInspectFlag());//是否有检测标准0：否 1：是
        processRecordResult.setResultStatus(processFormVo.getStatus());//工序检验结果，状态是否合格0：不合格 1：合格
        //添加上一个的工序检验单结果信息
        processFormResultService.insertProcessFormResult(processRecordResult);

        //查询工序检验对比表，获取工序检验单下已经质检了多少个。
        int count = processFormCompareService.getCount(p.getId());

        Map map = new HashMap<>();
        map.put("number",String.valueOf(count+1));
        return success(map);
    }


    @GetMapping("/qualityInspectionNoStandardFinish")
    @Operation(summary = "质检-无检验标准-完成")
    public CommonResult qualityInspectionNoStandardFinish(ProcessRecordVo processFormVo) throws Exception{

        ProcessRecord p = processFormService.findProcessFormById(processFormVo.getId());
        if(processFormVo.getInspectEndTime() != null && !"".equals(processFormVo.getInspectEndTime())){
            Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(processFormVo.getInspectEndTime());
            p.setInspectEndTime(d);
        }
        //更新工序检验结束时间。
        processFormService.updateProcessForm(p);

        //添加工序检验单对比信息
        ProcessRecordItem processRecordItem = new ProcessRecordItem();
        processRecordItem.setRecordId(processFormVo.getId());
        processRecordItem.setProductCode(processFormVo.getProductCode());//物料编码
        processRecordItem.setProductBarcode(processFormVo.getProductBarcode());//物料条码
        processRecordItem.setSortNumber(processFormVo.getNum());//序列号
        processRecordItem.setStatus(processFormVo.getStatus());
        processRecordItem.setFlag("1");//是否检验0：否1：是
        processFormCompareService.insertProcessFormCompare(processRecordItem);

        //修改上一个的的物料条码
        processFormCompareService.updateProcessRecordItem(p.getId(),processFormVo.getProductBarcode(),"1",processFormVo.getNum());

        ProcessRecordResult processRecordResult = new ProcessRecordResult();
        processRecordResult.setRecordId(p.getId());
        processRecordResult.setSortNumber(processFormVo.getNum());//序列号
        processRecordResult.setProductCode(p.getProductCode());//物料编号
        processRecordResult.setProcessCode(p.getProcessCode());//工序编号
        processRecordResult.setProcessName(p.getProcessName());
        processRecordResult.setDetectionNumber("1");//检测数量，默认为1.
        //首先查工序对应的检验标准是否有启用的版本，是启用则查询是否存在已启用的工序检验标准对应工序的检测项目，不存在inspectFlag赋值为0,存在inspectFlag赋值为1。
        //是禁用则直接inspectFlag赋值为0。
        processRecordResult.setInspectFlag(p.getInspectFlag());//是否有检测标准0：否 1：是
        processRecordResult.setResultStatus(processFormVo.getStatus());//工序检验结果，状态是否合格0：不合格 1：合格
        //添加上一个的工序检验单结果信息
        processFormResultService.insertProcessFormResult(processRecordResult);

        //更新状态，为已质检
        processFormService.updateStatus(p.getId(),"1");

        List<ProcessRecordResult> processFormResultList1 = processFormResultService.selectByResultStatus(processFormVo.getId(),"0");
        List<ProcessRecordResult> processFormResultList2 = processFormResultService.selectByResultStatus(processFormVo.getId(),"1");
        //不合格数
        int unqualifiedNum = processFormResultList1.size();
        //合格数
        int qualifiedNum = processFormResultList2.size();
        //实际检验数量
        int inspectNum = unqualifiedNum + qualifiedNum;

        List<Map<String,String>> list = new ArrayList<>();
        Map map1 = new HashMap<>();
        map1.put("label","检验数量");
        map1.put("value",inspectNum);
        Map map2 = new HashMap<>();
        map2.put("label","合格数");
        map2.put("value",qualifiedNum);
        Map map3 = new HashMap<>();
        map3.put("label","不合格数");
        map3.put("value",unqualifiedNum);
        list.add(map1);
        list.add(map2);
        list.add(map3);
        return success(list);
    }



    @PostMapping("/uploadPicture")
    @Operation(summary = "图片上传")
    public CommonResult uploadPicture(@RequestParam("mfs") MultipartFile[] mfs,@RequestParam("id") Long id,@RequestParam("num") String num) throws IOException{

        try{
            String fileName = "";//文件路径

            if(mfs.length > 0){
                for (MultipartFile file : mfs) {
                    //文件扩展名
                    String originalFilename = file.getOriginalFilename();
                    //新修改的文件名
                    String newFileName = IdUtil.fastSimpleUUID() + originalFilename;

                    //调用平台自带的文件上传服务，new Long(18) 是文件配置ID
                    fileName = fileClientFactory.getFileClient(new Long(18)).upload(file.getBytes(), newFileName, "image/jpeg");

                    //将图片路径和图片名信息加入数据库
                    ProcessPicture pic = new ProcessPicture();
                    pic.setRecordId(id);
                    pic.setSortNumber(num);//序列号
                    pic.setFileUrl(fileName);//文件路径
                    pic.setFileName(newFileName);
                    processPictureService.insertProcessPicture(pic);

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return  success("上传图片成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  success("上传图片失败");
    }


    @GetMapping("/qualityInspectionResultShow")
    @Operation(summary = "工序检验结果统计")
    public CommonResult qualityInspectionResultShow(ProcessRecordVo processRecordVo){

        List<ProcessRecordResult> processFormResultList1 = processFormResultService.selectByResultStatus(processRecordVo.getId(),"0");
        List<ProcessRecordResult> processFormResultList2 = processFormResultService.selectByResultStatus(processRecordVo.getId(),"1");
        //不合格数
        int unqualifiedNum = processFormResultList1.size();
        //合格数
        int qualifiedNum = processFormResultList2.size();
        //实际检验数量
        int inspectNum = unqualifiedNum + qualifiedNum;

        ProcessRecord p = processFormService.findProcessFormById(processRecordVo.getId());
        //检验数量
        String quantity = p.getQuantity();
        //合格率
        BigDecimal passRate = (new BigDecimal(qualifiedNum).divide(new BigDecimal(inspectNum),2, RoundingMode.HALF_UP)).multiply(new BigDecimal(100));
        //不合格率
        BigDecimal failureRate = (new BigDecimal(unqualifiedNum).divide(new BigDecimal(inspectNum),2, RoundingMode.HALF_UP)).multiply(new BigDecimal(100));


        Map map1 = new HashMap<>();
        map1.put("recordCode",p.getRecordCode());
        map1.put("inspectWay",p.getInspectWay());
        map1.put("inspectUser",p.getInspectUser());
        String inspectStartTime = DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss",p.getInspectStartTime());
        map1.put("inspectStartTime",inspectStartTime);
        String inspectEndTime = DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss",p.getInspectEndTime());
        map1.put("inspectEndTime",inspectEndTime);


        ProcessRecordResultVo processRecordResultVo = new ProcessRecordResultVo();
        processRecordResultVo.setProcessCode(p.getProcessCode());
        processRecordResultVo.setProcessName(p.getProcessName());
        processRecordResultVo.setUnqualifiedNum(String.valueOf(unqualifiedNum));
        processRecordResultVo.setQualifiedNum(String.valueOf(qualifiedNum));
        processRecordResultVo.setInspectNum(String.valueOf(inspectNum));
        processRecordResultVo.setQuantity(quantity);
        processRecordResultVo.setPassRate(String.valueOf(passRate));
        processRecordResultVo.setFailureRate(String.valueOf(failureRate));

        List list = new ArrayList();
        list.add(processRecordResultVo);
        Map map = new HashMap<>();
        map.put("obj",map1);
        map.put("list",list);
        return success(map);
    }

    public void addCompare(ProcessRecord p, String num, String itemBarcode,String flag){

        //修改上一个的的物料条码
        processFormCompareService.updateProcessRecordItem(p.getId(),itemBarcode,flag,num);

        //质检完成一个后，质检结果是否合格
        //根据序列号查询不合格的工序检验对比表数据
        //num当前质检数===序列号
        List<ProcessRecordItem> processFormCompareList = processFormCompareService.selectCompareByNumber(num,"0");
        int i = processFormCompareList.size();

        ProcessRecordResult processRecordResult = new ProcessRecordResult();
        processRecordResult.setRecordId(p.getId());
        processRecordResult.setSortNumber(num);//序列号
        processRecordResult.setProductCode(p.getProductCode());//物料编号
        processRecordResult.setProcessCode(p.getProcessCode());//工序编号
        processRecordResult.setProcessName(p.getProcessName());
        processRecordResult.setDetectionNumber("1");//检测数量，默认为1.
        //首先查工序对应的检验标准是否有启用的版本，是启用则查询是否存在已启用的工序检验标准对应工序的检测项目，不存在inspectFlag赋值为0,存在inspectFlag赋值为1。
        //是禁用则直接inspectFlag赋值为0。
        processRecordResult.setInspectFlag(p.getInspectFlag());//是否有检测标准0：否 1：是
        if(i>0){
            //不合格的数超过1，视为不合格
            processRecordResult.setResultStatus("0");//工序检验结果，状态是否合格0：不合格 1：合格
        }else {
            processRecordResult.setResultStatus("1");//工序检验结果，状态是否合格0：不合格 1：合格
        }
        //添加上一个的工序检验单结果信息
        processFormResultService.insertProcessFormResult(processRecordResult);

    }

}
