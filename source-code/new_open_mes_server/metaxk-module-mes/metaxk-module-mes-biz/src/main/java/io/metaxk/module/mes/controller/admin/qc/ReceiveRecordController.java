package io.metaxk.module.mes.controller.admin.qc;

import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.file.core.client.FileClientFactory;
import io.metaxk.module.mes.common.UserConstants;
import io.metaxk.module.mes.controller.admin.qc.vo.*;
import io.metaxk.module.mes.dal.dataobject.order.OtherInbound;
import io.metaxk.module.mes.dal.dataobject.order.OtherInboundItem;
import io.metaxk.module.mes.dal.dataobject.order.PurchaseOrder;
import io.metaxk.module.mes.dal.dataobject.order.PurchaseOrderItem;
import io.metaxk.module.mes.dal.dataobject.qc.*;
import io.metaxk.module.mes.dal.dataobject.wh.InboundRecBill;
import io.metaxk.module.mes.service.order.OtherInboundService;
import io.metaxk.module.mes.service.order.PurchaseOrderItemService;
import io.metaxk.module.mes.service.order.PurchaseOrderService;
import io.metaxk.module.mes.service.qc.*;
import io.metaxk.module.mes.service.wh.InboundRecBillItemService;
import io.metaxk.module.mes.service.wh.InboundRecBillService;
import io.metaxk.module.mes.utils.AutoCodeUtil;
import io.metaxk.module.mes.utils.DateUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.EXPORT_DATA_ERROR;

/**
 * @author 万界星空
 * @time 2023/7/26 13:34
 */
@Tag(name = "管理后台 - 来料检验单")
@RestController
@RequestMapping("/mes/qc/receive/record")
public class ReceiveRecordController {


    @Resource
    private ReceiveRecordService receiveRecordService;
    @Resource
    private ReceiveRecordItemService receiveRecordItemService;
    @Resource
    private ReceiveRecordResultService receiveRecordResultService;
    @Resource
    private ReceiveStandardItemService receiveStandardItemService;
    @Resource
    private DeviceService deviceService;
    @Resource
    private ReceivePictureService receivePictureService;
    @Resource
    private ReceiveStandardService receiveStandardService;
    @Resource
    private InboundRecBillService inboundRecBillService;
    @Resource
    private InboundRecBillItemService inboundRecBillItemService;
    @Resource
    private PurchaseOrderService purchaseOrderService;
    @Resource
    private PurchaseOrderItemService purchaseOrderItemService;
    @Resource
    private OtherInboundService otherInboundService;
    @Resource
    private AutoCodeUtil autoCodeUtil;

    /**
     * 文件上传服务
     */
    @Resource
    private FileClientFactory fileClientFactory;


    @GetMapping("/list")
    public CommonResult list(ReceiveRecordQueryVo receiveRecordQueryVo){
        PageResult<ReceiveRecord> pageResult = receiveRecordService.findPage(receiveRecordQueryVo);
        return success(pageResult);
    }


    @GetMapping("/export")
    @Operation(summary = "来料检验单导出")
    public void export(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("来料检验单", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), ReceiveRecordExcelVo.class).registerWriteHandler(styleStrategy).sheet("来料检验单").doWrite(receiveRecordService.exportData());
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }



    @GetMapping("/qualityInspectionShow1")
    @Operation(summary = "质检页面-有检验标准-回显")
    public CommonResult qualityInspectionShow1(ReceiveRecordVo receiveRecordVo) throws ParseException {

        //根据产品编号，查询是否已添加来料检验标准
        List<ReceiveStandard> receiveStandardList = receiveStandardService.findReceiveStandardByItemCode(receiveRecordVo.getItemCode());
        if (receiveStandardList != null){
            //添加质检员
            ReceiveRecord pr = new ReceiveRecord();
            pr.setId(receiveRecordVo.getId());
            pr.setInspectUser(receiveRecordVo.getInspectUser());
            receiveRecordService.updateReceiveRecord(pr);

            //根据来料检验单id，查询来料检验单信息
            ReceiveRecord receiveRecord =  receiveRecordService.findReceiveRecordById(receiveRecordVo.getId());

            //查询来料检验对比表，获取来料检验单下已经质检了多少个。
            int count = receiveRecordItemService.getCount(receiveRecordVo.getId());

            String maxSortNumber = receiveRecordItemService.selectMaxDetectionOrderNumber(receiveRecordVo.getId());//最新检测到第几个
            //查询已启用的来料检验标准
            //List<ReceiveRecordItemsVo> list = receiveRecordItemService.getReceiveRecordItems(receiveRecordVo.getId(),receiveRecordVo.getItemCode(),maxSortNumber);
            List<ReceiveRecordItemsVo> list = receiveRecordItemService.getReceiveRecordItems(receiveRecordVo.getId(),receiveRecordVo.getItemCode(),maxSortNumber);

            Map map = new HashMap<>();
            map.put("receiveRecord",receiveRecord);
            map.put("list",list);
            map.put("number",String.valueOf(count+1));

            return success(map);
        }else{
            return success("此产品编号还未添加来料检验标准");
        }
    }


    @GetMapping("/qualityInspectionShow2")
    @Operation(summary = "质检页面-无检验标准-回显")
    public CommonResult qualityInspectionShow2(ReceiveRecordVo receiveRecordVo){

        //根据产品编号，查询是否已添加来料检验标准
        List<ReceiveStandard> receiveStandardList = receiveStandardService.findReceiveStandardByItemCode(receiveRecordVo.getItemCode());
        if (receiveStandardList != null){
            //添加质检员
            ReceiveRecord pr = new ReceiveRecord();
            pr.setId(receiveRecordVo.getId());
            pr.setInspectUser(receiveRecordVo.getInspectUser());
            receiveRecordService.updateReceiveRecord(pr);

            //查询来料检验对比表，获取来料检验单下已经质检了多少个。
            int count = receiveRecordItemService.getCount(receiveRecordVo.getId());
            //根据来料检验单id，查询来料检验单信息
            ReceiveRecord receiveRecord =  receiveRecordService.findReceiveRecordById(receiveRecordVo.getId());

            Map map = new HashMap<>();
            map.put("receiveRecord",receiveRecord);
            map.put("number",String.valueOf(count+1));
            return success(map);
        }else{
            return success("此产品编号还未添加来料检验标准");
        }
    }


    @GetMapping("/qualityInspection")
    @Operation(summary = "质检-有检验标准")
    public CommonResult qualityInspection(ReceiveRecordItem receiveRecordItem) throws Exception{

        //前端需要传序列号sortNumber，是前端的当前进度数。
        //通过检验项目id查询对应序列号的来料检验单对比信息
        ReceiveRecordItem receiveRecordItem1  = receiveRecordItemService.selectReceiveRecordItem(receiveRecordItem.getRecordId(),receiveRecordItem.getStandardItemId(),receiveRecordItem.getSortNumber());
        if (receiveRecordItem1 == null){
            //添加来料检验单对比信息
            receiveRecordItem.setFlag("0");//是否检验,默认0，点击下一个后再修改成1.
            receiveRecordItemService.insertReceiveRecordItem(receiveRecordItem);
        }else {
            //修改
            receiveRecordItem1.setItemValue(receiveRecordItem.getItemValue());
            receiveRecordItem1.setStatus(receiveRecordItem.getStatus());
            receiveRecordItem1.setFlag("0");
            receiveRecordItemService.updateReceiveRecordItem(receiveRecordItem1);
        }

        return success(200).setMsg("质检成功");
    }


    @GetMapping("/qualityInspectionNext")
    @Operation(summary = "质检-有检验标准-下一个")
    public CommonResult qualityInspectionNext(ReceiveRecordVo receiveRecordVo) throws ParseException {

        ReceiveRecord r = receiveRecordService.findReceiveRecordById(receiveRecordVo.getId());

        if(receiveRecordVo.getInspectStartTime() != null && !"".equals(receiveRecordVo.getInspectStartTime())){
            Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(receiveRecordVo.getInspectStartTime());
            r.setInspectStartTime(d);
        }

        //更新质检时间，来料序检验开始时间。
        receiveRecordService.updateReceiveRecord(r);

        //添加上一个来料检验单结果信息
        addCompare(r,receiveRecordVo.getNum(),receiveRecordVo.getItemBarcode(),"1");

        //根据来料检验单id，查询来料检验单信息
        ReceiveRecord receiveRecord =  receiveRecordService.findReceiveRecordById(receiveRecordVo.getId());

        //查询来料检验对比表，获取来料检验单下已经质检了多少个。
        int count = receiveRecordItemService.getCount(r.getId());
        System.out.println("======下一个====r.getId()=====r.getId():"+r.getId());
        System.out.println("======下一个====count=====count:"+count);
        //检测标准（查询是否存在已启用的来料检验标准对应来料的检测项目）
        //根据到货通知单编号查询已启用的来料检验标准
        String enableFlag = "true";
        List<ReceiveStandardItem> receiveStandardItemList = receiveStandardItemService.findReceiveStandardItem(r.getRecNumber(),enableFlag);
        System.out.println("======下一个====receiveStandardItemList=====receiveStandardItemList:"+receiveStandardItemList);
        for (ReceiveStandardItem receiveStandardItem:receiveStandardItemList){
            if (receiveStandardItem != null){
                Device d  = deviceService.findDeviceByCode(receiveStandardItem.getDevice());
                if (d != null){
                    String name = d.getDeviceName();
                    receiveStandardItem.setDevice(name);
                }else{
                    receiveStandardItem.setDevice("");
                }

            }else{
                receiveStandardItem.setDevice("");
            }

        }

        Map map = new HashMap<>();
        map.put("receiveRecord",receiveRecord);
        List<ReceiveRecordItem> receiveStandardItemList1 = new ArrayList<>();
        for (ReceiveStandardItem rs:receiveStandardItemList){
            ReceiveRecordItem rsi = new ReceiveRecordItem();
            rsi.setStandardItemId(rs.getId());
            rsi.setItemName(rs.getName());
            rsi.setItemStandard(rs.getStandard());
            rsi.setItemDevice(rs.getDevice());
            rsi.setItemValue("");
            receiveStandardItemList1.add(rsi);
        }

        map.put("list",receiveStandardItemList1);
        map.put("number",String.valueOf(count+1));
        return success(map);
    }


    @GetMapping("/qualityInspectionFinish")
    @Operation(summary = "质检-有检验标准-完成")
    public CommonResult qualityInspectionFinish(ReceiveRecordVo receiveRecordVo) throws Exception{

        ReceiveRecord r = receiveRecordService.findReceiveRecordById(receiveRecordVo.getId());

        if(receiveRecordVo.getInspectEndTime() != null && !"".equals(receiveRecordVo.getInspectEndTime())){
            Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(receiveRecordVo.getInspectEndTime());
            r.setInspectEndTime(d);
        }

        //更新来料检验结束时间。
        receiveRecordService.updateReceiveRecord(r);

        //添加来料检验单结果信息
        addCompare(r,receiveRecordVo.getNum(),receiveRecordVo.getItemBarcode(),"1");

        //更新状态，为已质检
        receiveRecordService.updateStatus(r.getId(),"1");

        //更新到货通知明细信息的质检状态，为已质检
        inboundRecBillItemService.updateState(r.getItemCode(),r.getRecNumber(),"1");

        //来料检验单结果明细,数据回显。
        List<ReceiveRecordResult> receiveRecordResultList1 = receiveRecordResultService.selectByResultStatus(receiveRecordVo.getId(),"0");
        List<ReceiveRecordResult> receiveRecordResultList2 = receiveRecordResultService.selectByResultStatus(receiveRecordVo.getId(),"1");
        //不合格数
        int unqualifiedNum = receiveRecordResultList1.size();
        //合格数
        int qualifiedNum = receiveRecordResultList2.size();
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
    public CommonResult qualityInspectionNoStandard(ReceiveRecordVo receiveRecordVo) throws Exception{

        ReceiveRecord r = receiveRecordService.findReceiveRecordById(receiveRecordVo.getId());

        if(receiveRecordVo.getInspectStartTime() != null && !"".equals(receiveRecordVo.getInspectStartTime())){
            Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(receiveRecordVo.getInspectStartTime());
            r.setInspectStartTime(d);
        }

        //更新来料检验开始时间。
        receiveRecordService.updateReceiveRecord(r);

        //添加来料检验单对比信息
        ReceiveRecordItem receiveRecordItem = new ReceiveRecordItem();
        receiveRecordItem.setRecordId(receiveRecordVo.getId());
        receiveRecordItem.setItemCode(receiveRecordVo.getItemCode());//产品编码
        receiveRecordItem.setItemBarcode(receiveRecordVo.getItemBarcode());//产品条码
        receiveRecordItem.setSortNumber(receiveRecordVo.getNum());//序列号
        receiveRecordItem.setStatus(receiveRecordVo.getStatus());
        receiveRecordItem.setFlag("1");//是否检验0：否1：是
        receiveRecordItemService.insertReceiveRecordItem(receiveRecordItem);

        //修改上一个的的产品条码
        receiveRecordItemService.updateReceiveRecordItem(r.getId(),receiveRecordVo.getItemBarcode(),"1",receiveRecordVo.getNum());


        ReceiveRecordResult receiveRecordResult = new ReceiveRecordResult();
        receiveRecordResult.setRecordId(r.getId());
        receiveRecordResult.setSortNumber(receiveRecordVo.getNum());//序列号
        receiveRecordResult.setItemCode(r.getItemCode());//产品编号
        receiveRecordResult.setRecNumber(r.getRecNumber());//到货通知单编号
        receiveRecordResult.setDetectionNumber("1");//检测数量，默认为1.

        //首先查来料对应的检验标准是否有启用的版本，是启用则查询是否存在已启用的来料检验标准对应来料的检测项目，不存在inspectFlag赋值为0,存在inspectFlag赋值为1。
        //是禁用则直接inspectFlag赋值为0。
        receiveRecordResult.setInspectFlag(r.getInspectFlag());//是否有检测标准0：否 1：是
        receiveRecordResult.setResultStatus(receiveRecordVo.getStatus());//来料检验结果，状态是否合格0：不合格 1：合格
        //添加上一个的来料检验单结果信息
        receiveRecordResultService.insertReceiveRecordResult(receiveRecordResult);

        //查询来料检验对比表，获取来料检验单下已经质检了多少个。
        int count = receiveRecordItemService.getCount(r.getId());

        Map map = new HashMap<>();
        map.put("number",String.valueOf(count+1));
        return success(map);
    }



    @GetMapping("/qualityInspectionNoStandardFinish")
    @Operation(summary = "质检-无检验标准-完成")
    public CommonResult qualityInspectionNoStandardFinish(ReceiveRecordVo receiveRecordVo) throws Exception{

        ReceiveRecord r = receiveRecordService.findReceiveRecordById(receiveRecordVo.getId());
        if(receiveRecordVo.getInspectEndTime() != null && !"".equals(receiveRecordVo.getInspectEndTime())){
            Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(receiveRecordVo.getInspectEndTime());
            r.setInspectEndTime(d);
        }
        //更新来料检验结束时间。
        receiveRecordService.updateReceiveRecord(r);

        //添加来料检验单对比信息
        ReceiveRecordItem receiveRecordItem = new ReceiveRecordItem();
        receiveRecordItem.setRecordId(receiveRecordVo.getId());
        receiveRecordItem.setItemCode(receiveRecordVo.getItemCode());//产品编码
        receiveRecordItem.setItemBarcode(receiveRecordVo.getItemBarcode());//产品条码
        receiveRecordItem.setSortNumber(receiveRecordVo.getNum());//序列号
        receiveRecordItem.setStatus(receiveRecordVo.getStatus());
        receiveRecordItem.setFlag("1");//是否检验0：否1：是
        receiveRecordItemService.insertReceiveRecordItem(receiveRecordItem);

        //修改上一个的的产品条码
        receiveRecordItemService.updateReceiveRecordItem(r.getId(),receiveRecordVo.getItemBarcode(),"1",receiveRecordVo.getNum());

        ReceiveRecordResult receiveRecordResult = new ReceiveRecordResult();
        receiveRecordResult.setRecordId(r.getId());
        receiveRecordResult.setSortNumber(receiveRecordVo.getNum());//序列号
        receiveRecordResult.setItemCode(r.getItemCode());//产品编号
        receiveRecordResult.setRecNumber(r.getRecNumber());//到货通知单编号
        receiveRecordResult.setDetectionNumber("1");//检测数量，默认为1.
        //首先查来料对应的检验标准是否有启用的版本，是启用则查询是否存在已启用的来料检验标准对应来料的检测项目，不存在inspectFlag赋值为0,存在inspectFlag赋值为1。
        //是禁用则直接inspectFlag赋值为0。
        receiveRecordResult.setInspectFlag(r.getInspectFlag());//是否有检测标准0：否 1：是
        receiveRecordResult.setResultStatus(receiveRecordVo.getStatus());//来料检验结果，状态是否合格0：不合格 1：合格

        //添加上一个的来料检验单结果信息
        receiveRecordResultService.insertReceiveRecordResult(receiveRecordResult);

        //更新状态，为已质检
        receiveRecordService.updateStatus(r.getId(),"1");

        //更新到货通知明细信息的质检状态，为已质检
        inboundRecBillItemService.updateState(r.getItemCode(),r.getRecNumber(),"1");

        //来料检验单结果明细,数据回显。
        List<ReceiveRecordResult> receiveRecordResultList1 = receiveRecordResultService.selectByResultStatus(receiveRecordVo.getId(),"0");
        List<ReceiveRecordResult> receiveRecordResultList2 = receiveRecordResultService.selectByResultStatus(receiveRecordVo.getId(),"1");
        //不合格数
        int unqualifiedNum = receiveRecordResultList1.size();
        //合格数
        int qualifiedNum = receiveRecordResultList2.size();
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



    @GetMapping("/qualityInspectionResultShow")
    @Operation(summary = "来料检验结果统计")
    public CommonResult qualityInspectionResultShow(ReceiveRecordVo receiveRecordVo){

        List<ReceiveRecordResult> receiveRecordResultList1 = receiveRecordResultService.selectByResultStatus(receiveRecordVo.getId(),"0");
        List<ReceiveRecordResult> receiveRecordResultList2 = receiveRecordResultService.selectByResultStatus(receiveRecordVo.getId(),"1");
        //不合格数
        int unqualifiedNum = receiveRecordResultList1.size();
        //合格数
        int qualifiedNum = receiveRecordResultList2.size();
        //实际检验数量
        int inspectNum = unqualifiedNum + qualifiedNum;

        ReceiveRecord r = receiveRecordService.findReceiveRecordById(receiveRecordVo.getId());
        //数量(到货数)
        BigDecimal quantity = r.getQuantity();
        //合格率
        BigDecimal passRate = (new BigDecimal(qualifiedNum).divide(new BigDecimal(inspectNum),2, RoundingMode.HALF_UP)).multiply(new BigDecimal(100));
        //不合格率
        BigDecimal failureRate = (new BigDecimal(unqualifiedNum).divide(new BigDecimal(inspectNum),2, RoundingMode.HALF_UP)).multiply(new BigDecimal(100));
        System.out.println("======合格率:"+passRate);
        System.out.println("======不合格率:"+failureRate);


        Map map1 = new HashMap<>();
        map1.put("recordCode",r.getRecordCode());
        map1.put("inspectWay",r.getInspectWay());
        map1.put("inspectUser",r.getInspectUser());
        String inspectStartTime = DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss",r.getInspectStartTime());
        map1.put("inspectStartTime",inspectStartTime);
        String inspectEndTime = DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss",r.getInspectEndTime());
        map1.put("inspectEndTime",inspectEndTime);


        ReceiveRecordResultVo receiveRecordResultVo = new ReceiveRecordResultVo();
        receiveRecordResultVo.setItemCode(r.getItemCode());
        receiveRecordResultVo.setRecNumber(r.getRecNumber());
        receiveRecordResultVo.setUnqualifiedNum(String.valueOf(unqualifiedNum));
        receiveRecordResultVo.setQualifiedNum(String.valueOf(qualifiedNum));
        receiveRecordResultVo.setInspectNum(String.valueOf(inspectNum));
        receiveRecordResultVo.setQuantity(quantity);
        receiveRecordResultVo.setPassRate(String.valueOf(passRate));
        receiveRecordResultVo.setFailureRate(String.valueOf(failureRate));

        List list = new ArrayList();
        list.add(receiveRecordResultVo);
        Map map = new HashMap<>();
        map.put("obj",map1);
        map.put("list",list);
        return success(map);
    }



    @PostMapping("/uploadPicture")
    @Operation(summary = "图片上传")
    public CommonResult uploadPicture(@RequestParam("mfs") MultipartFile[] mfs, @RequestParam("id") Long id, @RequestParam("num") String num) throws IOException{

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
                    ReceivePicture pic = new ReceivePicture();
                    pic.setRecordId(id);
                    pic.setSortNumber(num);//序列号
                    pic.setFileUrl(fileName);//文件路径
                    pic.setFileName(newFileName);
                    receivePictureService.insertProcessPicture(pic);

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


    public void addCompare(ReceiveRecord r, String num, String itemBarcode,String flag){

        //修改上一个的的产品条码
        receiveRecordItemService.updateReceiveRecordItem(r.getId(),itemBarcode,flag,num);

        //质检完成一个后，质检结果是否合格
        //根据序列号查询不合格的来料检验对比表数据
        //num当前质检数===序列号
        List<ReceiveRecordItem> processFormCompareList = receiveRecordItemService.selectReceiveRecordItem(num,"0");
        int i = processFormCompareList.size();

        ReceiveRecordResult receiveRecordResult = new ReceiveRecordResult();
        receiveRecordResult.setRecordId(r.getId());
        receiveRecordResult.setSortNumber(num);//序列号
        receiveRecordResult.setItemCode(r.getItemCode());//产品编号
        receiveRecordResult.setRecNumber(r.getRecNumber());//到货通知单编号
        receiveRecordResult.setDetectionNumber("1");//检测数量，默认为1.
        //首先查来料对应的检验标准是否有启用的版本，是启用则查询是否存在已启用的来料检验标准对应来料的检测项目，不存在inspectFlag赋值为0,存在inspectFlag赋值为1。
        //是禁用则直接inspectFlag赋值为0。
        receiveRecordResult.setInspectFlag(r.getInspectFlag());//是否有检测标准0：否 1：是
        if(i>0){
            //不合格的数超过1，视为不合格
            receiveRecordResult.setResultStatus("0");//来料检验结果，状态是否合格0：不合格 1：合格
        }else {
            receiveRecordResult.setResultStatus("1");//来料检验结果，状态是否合格0：不合格 1：合格
        }
        //添加上一个的来料检验单结果信息
        receiveRecordResultService.insertReceiveRecordResult(receiveRecordResult);

    }


    /*public void addInbound(String recNumber){

        //通过到货通知单编号，查询质检结果
        List<ReceiveRecordResult> list1 = receiveRecordResultService.selectByStatus(recNumber,"1");//合格的数据
        //查询到货通知
        InboundRecBill inboundRecBill = inboundRecBillService.selectByNumber(recNumber);
        //根据采购单编号，查询采购单信息
        PurchaseOrder purchaseOrder =  purchaseOrderService.selectByNumber(inboundRecBill.getReceiptNumber());
        List<PurchaseOrderItem> purchaseOrderItemList =  purchaseOrderItemService.findReceiptItemByNum(inboundRecBill.getReceiptNumber());
        //添加采购入库
        OtherInbound otherInbound = new OtherInbound();
        //自动生成采购入库编号
        String number = autoCodeUtil.genSerialCode(UserConstants.INBOUND_CODE, null);
        otherInbound.setInNumber(number);
        otherInbound.setReceiptNumber(purchaseOrder.getNumber());//采购单号
        otherInbound.setRecNumber(recNumber);//到货通知单编号
        otherInbound.setDeliveryDate(purchaseOrder.getDeliveryDate());
        otherInbound.setSource("采购");
        otherInbound.setVendor(purchaseOrder.getVendorName());
        otherInbound.setStatus("待入库");
        otherInboundService.saveOtherInbound(otherInbound);

        for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItemList){
            OtherInboundItem otherInboundItem = new OtherInboundItem();
            otherInboundItem.setInNumber(number);
            otherInboundItem.setReceiptNumber(purchaseOrderItem.getReceiptNumber());
            otherInboundItem.setItemCode(purchaseOrderItem.getItemCode());
            otherInboundItem.setItemName(purchaseOrderItem.getItemName());
            otherInboundItem.setModel(purchaseOrderItem.getModel());
            otherInboundItem.setSpec(purchaseOrderItem.getSpec());
            otherInboundItem.setVendor(purchaseOrderItem.getVendor());
            otherInboundItem.setKind(purchaseOrderItem.getKind());
            otherInboundItem.setUnitOfMeasure(purchaseOrderItem.getUnitOfMeasure());
            otherInboundItem.setPurchasePrice(purchaseOrderItem.getPurchasePrice());
        }
    }*/


}
