package io.metaxk.module.mes.controller.admin.qc;

import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.file.core.client.FileClientFactory;
import io.metaxk.module.mes.controller.admin.qc.vo.OtherRecordExcelVo;
import io.metaxk.module.mes.controller.admin.qc.vo.OtherRecordQueryVo;
import io.metaxk.module.mes.controller.admin.qc.vo.OtherStandardResultVo;
import io.metaxk.module.mes.dal.dataobject.md.Item;
import io.metaxk.module.mes.dal.dataobject.pro.WorkOrder;
import io.metaxk.module.mes.dal.dataobject.qc.*;
import io.metaxk.module.mes.service.md.ItemService;
import io.metaxk.module.mes.service.pro.WorkOrderService;
import io.metaxk.module.mes.service.qc.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.EXPORT_DATA_ERROR;

/**
 * @author 万界星空
 * @time 2023/8/19 14:28
 */
@Tag(name = "管理后台 - 其他检验单")
@RestController
@RequestMapping("/mes/qc/other/record")
public class OtherRecordController {

    @Resource
    private OtherRecordService otherRecordService;
    @Resource
    private OtherRecordItemService otherRecordItemService;
    @Resource
    private OtherRecordResultService otherRecordResultService;
    @Resource
    private OtherPictureService otherPictureService;
    @Resource
    private WorkOrderService workOrderService;
    @Resource
    private ItemService itemService;
    @Resource
    private OtherStandardService otherStandardService;
    @Resource
    private OtherStandardItemService otherStandardItemService;

    /**
     * 文件上传服务
     */
    @Resource
    private FileClientFactory fileClientFactory;

    @GetMapping("/list")
    public CommonResult list(OtherRecordQueryVo otherRecordQueryVo){
        PageResult<OtherRecord> pageResult = otherRecordService.findPage(otherRecordQueryVo);
        return success(pageResult);
    }


    @GetMapping("/exportExcel")
    @Operation(summary = "其他检验单导出")
    public void exportExcel(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("其他检验单", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), OtherRecordExcelVo.class).registerWriteHandler(styleStrategy).sheet("其他检验单").doWrite(otherRecordService.exportDate());
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }


    @PostMapping("/save")
    @Operation(summary = "质检-添加")
    public CommonResult<Integer> save(@RequestBody OtherRecord otherRecord) throws ParseException {
        return success(otherRecordService.saveOtherRecord(otherRecord)).setMsg("添加成功");
    }


    @PostMapping("/update")
    @Operation(summary = "质检-修改")
    public CommonResult<Integer> update(@RequestBody OtherRecord otherRecord) throws ParseException {
        return success(otherRecordService.updateOtherRecord(otherRecord)).setMsg("修改成功");
    }

    @GetMapping("/show")
    @Operation(summary = "质检-检验标准回显")
    public CommonResult show(String workOrderCode) throws ParseException {
        //根据生产订单编号查询生产订单信息
        WorkOrder workOrder = workOrderService.findWorkOrderCode(workOrderCode);
        //根据产品编号查询产品信息
        Item item = itemService.selectMdItemByItemCode(workOrder.getProductCode());
        OtherRecord otherRecord = new OtherRecord();
        otherRecord.setWorkOrderCode(workOrder.getWorkorderCode());//生产订单编号
        otherRecord.setItemCode(item.getItemCode());//产品编号
        otherRecord.setItemName(item.getItemName());//产品名称
        otherRecord.setClientCode(workOrder.getClientCode());//客户编码
        otherRecord.setClientName(workOrder.getClientName());//客户名称
        otherRecord.setModel(item.getModel());//型号
        otherRecord.setSpec(item.getSpec());//规格
        otherRecord.setLineType(item.getLineType());//线别
        otherRecord.setUnitOfMeasure(item.getUnitOfMeasure());//单位
        otherRecord.setKind(item.getKind());//品类
        //根据型号，规格，线别查询检验标准信息
        OtherStandard otherStandard = otherStandardService.findOtherStandard(item.getModel(),item.getSpec(),item.getLineType());
        //根据检验标准编号查询检验明细信息
        List<OtherStandardItem> otherStandardItemList = otherStandardItemService.findOtherStandardItemByCode(otherStandard.getNumber());

        List<OtherRecordItem> otherRecordItemList = new ArrayList<>();
        for (OtherStandardItem otherStandardItem : otherStandardItemList){
            OtherRecordItem otherRecordItem = new OtherRecordItem();
            otherRecordItem.setStandardItemId(otherStandardItem.getId());//检测项目id
            otherRecordItem.setItemName(otherStandardItem.getName());//检测项目名称
            otherRecordItem.setItemStandard(otherStandardItem.getStandard());//检测项目标准
            otherRecordItem.setItemDevice(otherStandardItem.getDevice());//检测项目器具
            otherRecordItem.setItemValue("");//检测项目实际信息
            otherRecordItemList.add(otherRecordItem);
        }
        otherRecord.setOtherStandardNumber(otherStandard.getNumber());//检验标准编号
        otherRecord.setOtherRecordItemList(otherRecordItemList);
        return success(otherRecord);
    }


    @GetMapping("/find/{id}")
    @Operation(summary = "详情")
    public CommonResult<OtherRecord> findOtherRecord(@PathVariable Long id){
        OtherRecord otherRecord = otherRecordService.findOtherRecordById(id);
        List<OtherRecordItem> otherRecordItemList = otherRecordItemService.findOtherRecordItem(otherRecord.getNumber());
        otherRecord.setOtherRecordItemList(otherRecordItemList);
        return success(otherRecord);
    }


    /*public CommonResult getResult(OtherStandardResultVo otherStandardResultVo){
        List<OtherStandardResultVo> list = new ArrayList<>();
        otherStandardResultVo.setStatus("0");
        OtherStandardResultVo v1 = otherRecordResultService.getAcount(otherStandardResultVo);
        v1.setUnqualifiedNum(v1.getNum());
        list.add(v1);
        otherStandardResultVo.setStatus("1");
        OtherStandardResultVo v1 = otherRecordResultService.getAcount(otherStandardResultVo);
        v1.setUnqualifiedNum(v1.getNum());
        list.add(v1);
        return success(otherRecord);
    }*/


    @PostMapping("/uploadPicture")
    @Operation(summary = "图片上传")
    public CommonResult uploadPicture(@RequestParam("mfs") MultipartFile[] mfs, @RequestParam("number") String number, @RequestParam("num") String num) throws IOException{

        try{
            System.out.println("=============mfs==========");
            String fileName = "";//文件路径

            if(mfs.length > 0){
                for (MultipartFile file : mfs) {
                    //文件扩展名
                    String originalFilename = file.getOriginalFilename();
                    System.out.println("=============originalFilename:"+originalFilename);
                    //新修改的文件名
                    String newFileName = IdUtil.fastSimpleUUID() + originalFilename;
                    System.out.println("=============newFileName:"+newFileName);
                    //调用平台自带的文件上传服务，new Long(18) 是文件配置ID
                    fileName = fileClientFactory.getFileClient(new Long(18)).upload(file.getBytes(), newFileName, "image/jpeg");
                    System.out.println("=============fileName:"+fileName);
                    //将图片路径和图片名信息加入数据库
                    OtherPicture otherPicture = new OtherPicture();
                    otherPicture.setRecordNumber(number);
                    otherPicture.setSortNumber(num);//序列号
                    otherPicture.setFileUrl(fileName);//文件路径
                    otherPicture.setFileName(newFileName);
                    otherPictureService.saveOtherPicture(otherPicture);

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
}
