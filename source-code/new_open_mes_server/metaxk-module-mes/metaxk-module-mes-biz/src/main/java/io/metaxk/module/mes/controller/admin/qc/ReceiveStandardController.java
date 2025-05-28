package io.metaxk.module.mes.controller.admin.qc;

import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.qc.vo.ReceiveStandardQueryVo;
import io.metaxk.module.mes.controller.admin.qc.vo.ReceiveStandardVo;
import io.metaxk.module.mes.dal.dataobject.qc.ReceiveRecord;
import io.metaxk.module.mes.dal.dataobject.qc.ReceiveStandard;
import io.metaxk.module.mes.dal.dataobject.qc.ReceiveStandardItem;
import io.metaxk.module.mes.service.qc.ReceiveRecordService;
import io.metaxk.module.mes.service.qc.ReceiveStandardItemService;
import io.metaxk.module.mes.service.qc.ReceiveStandardService;
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
import static io.metaxk.module.mes.enums.ErrorCodeConstants.RECEIVE_STANDARD_ENABLED;


/**
 * @author 万界星空
 * @time 2023/7/26 10:51
 */
@Tag(name = "管理后台 - 来料检验标准")
@RestController
@RequestMapping("/mes/qc/receive/standard")
public class ReceiveStandardController {

    @Resource
    private ReceiveStandardService receiveStandardService;

    @Resource
    private ReceiveStandardItemService receiveStandardItemService;

    @Resource
    private ReceiveRecordService receiveRecordService;

    @GetMapping("/list")
    @Operation(summary = "来料检验标准列表")
    public CommonResult<PageResult<ReceiveStandard>> list(ReceiveStandardQueryVo receiveStandardQueryVo){
        PageResult<ReceiveStandard> pageResult = receiveStandardService.findPage(receiveStandardQueryVo);
        return success(pageResult);
    }



    @PostMapping("/save")
    @Operation(summary = "新增来料检验标准")
    public CommonResult<Integer> save(@RequestBody ReceiveStandard receiveStandard){
        System.out.println("==============receiveStandard"+receiveStandard);
        System.out.println("==============itemCode"+receiveStandard.getItemCode());
        System.out.println("==============itemCodeList"+receiveStandard.getItemCodeList());
        List<ReceiveStandard> receiveStandardList =  receiveStandardService.findReceiveStandardList();
        if(!receiveStandardList.isEmpty()){
            for(ReceiveStandard receiveStandard1:receiveStandardList){
                String itemCode = receiveStandard1.getItemCode();
                String[] itemCodeArray = itemCode.split(",");
                for (String code : itemCodeArray) {
                    List<String> recNumberList1 = receiveStandard.getItemCodeList();
                    for(String itemCodes:recNumberList1){
                        List<ReceiveStandard> standardList1 = receiveStandardService.findReceiveStandardByItemCode(code);
                        for(ReceiveStandard receiveStandard2:standardList1) {
                            System.out.println("===============输出flag============" + receiveStandard2.getEnableFlag());
                            if (code.equals(itemCodes) && "true".equals(receiveStandard2.getEnableFlag())) {
                                throw exception(RECEIVE_STANDARD_ENABLED);
                            }
                        }
                    }
                }
            }
        }
        StringBuilder itemCodes = new StringBuilder();
        StringBuilder productTypeCodes = new StringBuilder();
        List<String> itemCodeList = receiveStandard.getItemCodeList();
        for (String itemCode : itemCodeList) {
            itemCodes.append(itemCode).append(",");
        }
        //将最后一个逗号去除
        if (itemCodes.length() > 0) {
            itemCodes.deleteCharAt(itemCodes.length() - 1);
        }
        receiveStandard.setItemCode(itemCodes.toString());

        /*List<String> productTypeList = receiveStandard.getProductTypeList();
        for (String productTypeCode : productTypeList) {
            productTypeCodes.append(productTypeCode).append(",");
        }
        //将最后一个逗号去除
        if (productTypeCodes.length() > 0) {
            productTypeCodes.deleteCharAt(productTypeCodes.length() - 1);
        }
        receiveStandard.setProductType(productTypeCodes.toString());//产品分类*/
        List<ReceiveStandardVo> inspectDeviceList = receiveStandard.getInspectDeviceList();
        for (ReceiveStandardVo receiveStandardVo : inspectDeviceList) {
            if (StringUtils.isNotBlank(receiveStandardVo.getItemDevice()) && StringUtils.isNotBlank(receiveStandardVo.getItemName()) && StringUtils.isNotBlank(receiveStandardVo.getItemStandard()) && StringUtils.isNotBlank(receiveStandardVo.getRemark())) {
                ReceiveStandardItem receiveStandardItem = new ReceiveStandardItem();
                receiveStandardItem
                        .setEnableFlag(receiveStandard.getEnableFlag())
                        .setItemCode(receiveStandard.getItemCode())//产品编号
                        .setRecStandardNumber(receiveStandard.getNumber())//来料检验标准编号
                        .setName(receiveStandardVo.getItemName())
                        .setStandard(receiveStandardVo.getItemStandard())
                        .setDevice(receiveStandardVo.getItemDevice())
                        .setRemark(receiveStandardVo.getRemark());
                receiveStandardItemService.saveReceiveStandardItem(receiveStandardItem);
            }
        }
        receiveStandardService.saveReceiveStandard(receiveStandard);

        //更新未质检的产品编号对应的来料检验单信息。
        for (String itemCode : itemCodeList) {
            //查询产品编号对应的来料检验单信息
            List<ReceiveRecord> receiveRecordList = receiveRecordService.findReceiveRecordByItemCode(itemCode);
            System.out.println("==========bbbbb=====receiveRecordList:"+receiveRecordList);
            for (ReceiveRecord receiveRecord : receiveRecordList){
                if(receiveRecord != null && "0".equals(receiveRecord.getStatus())){//未质检
                    receiveRecordService.updateReceiveRecords(receiveRecord);
                }
            }
        }

        return success(200).setMsg("新增成功");
    }



    @PostMapping("/update")
    @Operation(summary = "修改来料检验标准")
    public CommonResult<Integer> update(@RequestBody ReceiveStandard receiveStandard){
        List<ReceiveStandard> receiveStandardList =  receiveStandardService.findReceiveStandardList();
        if(!receiveStandardList.isEmpty()){
            for(ReceiveStandard receiveStandardDo:receiveStandardList){
                String[] itemCodeArray = receiveStandardDo.getItemCode().split(",");
                for (String code : itemCodeArray) {
                    //遍历时取出传递过来的到货通知集合
                    List<String> itemCodeList1 = receiveStandard.getItemCodeList();
                    for(String itemCodes:itemCodeList1){
                        List<ReceiveStandard> standardList1 = receiveStandardService.findReceiveStandardByItemCode(code);
                        for(ReceiveStandard receiveStandard2:standardList1) {
                            System.out.println("===============输出flag============" + receiveStandard2.getEnableFlag());
                            //获取当前信息的id，判断当前这条信息是否为true，如果为true不需要执行异常抛出语句
                            ReceiveStandard rs = receiveStandardService.findReceiveStandardById(receiveStandard.getId());
                            if(!"true".equals(rs.getEnableFlag())) {
                                if (code.equals(itemCodes) && "true".equals(receiveStandard2.getEnableFlag())) {
                                    throw exception(RECEIVE_STANDARD_ENABLED);
                                }
                            }
                        }
                    }
                }
            }
        }


        List<String> itemCodeList = receiveStandard.getItemCodeList();
        if (itemCodeList != null){
            StringBuilder itemCodes = new StringBuilder();
            for (String itemCode : itemCodeList) {
                itemCodes.append(itemCode).append(",");
            }
            //将最后一个逗号去除
            if (itemCodes.length() > 0) {
                itemCodes.deleteCharAt(itemCodes.length() - 1);
            }
            receiveStandard.setItemCode(itemCodes.toString());
        }

        /*List<String> productTypeList = receiveStandard.getProductTypeList();
        if (productTypeList != null){
            StringBuilder productTypeCodes = new StringBuilder();
            for (String productTypeCode : productTypeList) {
                productTypeCodes.append(productTypeCode).append(",");
            }
            //将最后一个逗号去除
            if (productTypeCodes.length() > 0) {
                productTypeCodes.deleteCharAt(productTypeCodes.length() - 1);
            }
            receiveStandard.setProductType(productTypeCodes.toString());//产品分类
        }*/

        //先删除，再添加
        receiveStandardItemService.removeCode(receiveStandard.getNumber());
        List<ReceiveStandardVo> inspectDeviceList = receiveStandard.getInspectDeviceList();
        for (ReceiveStandardVo receiveStandardVo : inspectDeviceList) {
           // if (receiveStandardItemService.findReceiveStandardItemByCondition(receiveStandard.getNumber(),receiveStandardVo.getItemName(),receiveStandardVo.getItemStandard(),receiveStandardVo.getItemDevice()) == null) {
                ReceiveStandardItem receiveStandardItem = new ReceiveStandardItem();
                receiveStandardItem
                        .setEnableFlag(receiveStandard.getEnableFlag())
                        .setItemCode(receiveStandard.getItemCode())//产品编号
                        .setRecStandardNumber(receiveStandard.getNumber())//来料检验标准编号
                        .setName(receiveStandardVo.getItemName())
                        .setStandard(receiveStandardVo.getItemStandard())
                        .setDevice(receiveStandardVo.getItemDevice())
                        .setRemark(receiveStandardVo.getRemark());
                receiveStandardItemService.saveReceiveStandardItem(receiveStandardItem);
          //  }
        }
        receiveStandardService.updateReceiveStandard(receiveStandard);

        if (itemCodeList != null){
            //更新未质检的到货通知单对应的来料检验单信息。
            for (String itemCode : itemCodeList) {
                //查询产品编号对应的来料检验单信息
                List<ReceiveRecord> receiveRecordList = receiveRecordService.findReceiveRecordByItemCode(itemCode);
                System.out.println("==========bbbbb=====receiveRecordList:"+receiveRecordList);
                for (ReceiveRecord receiveRecord : receiveRecordList){
                    if(receiveRecord != null && "0".equals(receiveRecord.getStatus())){//未质检
                        receiveRecordService.updateReceiveRecords(receiveRecord);
                    }
                }
            }
        }

        return success(200).setMsg("修改成功");
    }


    @GetMapping("/find/{id}")
    @Operation(summary = "查询来料检验标准详情")
    public CommonResult<ReceiveStandard> findReceiveStandard(@PathVariable Long id){
        ReceiveStandard receiveStandard = receiveStandardService.findReceiveStandardById(id);
        String itemCode = receiveStandard.getItemCode();//产品编号
        String[] itemCodeArray = itemCode.split(",");
        receiveStandard.setItemCodeList(Arrays.asList(itemCodeArray));

        /*String productType = receiveStandard.getProductType();//产品分类
        String[] productTypeArray = productType.split(",");
        receiveStandard.setProductTypeList(Arrays.asList(productTypeArray));*/

        String number = receiveStandard.getNumber();//来料检验标准编号
        List<ReceiveStandardItem> receiveStandardItemList =   receiveStandardItemService.findReceiveStandardItemByCode(number);
        ArrayList<ReceiveStandardVo> arrayList = new ArrayList<>();
        for(ReceiveStandardItem receiveStandardItem:receiveStandardItemList){
            ReceiveStandardVo receiveStandardVo = new ReceiveStandardVo();
            receiveStandardVo.setItemName(receiveStandardItem.getName());
            receiveStandardVo.setItemStandard(receiveStandardItem.getStandard());
            receiveStandardVo.setItemDevice(receiveStandardItem.getDevice());
            receiveStandardVo.setRemark(receiveStandardItem.getRemark());
            arrayList.add(receiveStandardVo);
        }
        receiveStandard.setInspectDeviceList(arrayList);
        return success(receiveStandard);
    }


    @DeleteMapping("/batch")
    @Operation(summary = "删除来料检验标准")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids){
        for(Long id:ids){
            ReceiveStandard receiveStandard = receiveStandardService.findReceiveStandardById(id);
            receiveStandardItemService.removeCode(receiveStandard.getNumber());
        }
        return success( receiveStandardService.removeReceiveStandard(ids)).setMsg("删除成功");
    }


    @DeleteMapping("/remove/{deviceCode}/{number}")
    @Operation(summary = "修改的时候移除检测标准中的检测项")
    public CommonResult<Integer> removeStandDevice(@PathVariable String deviceCode,@PathVariable String number){
        //根据器具编号与来料检验标准编号，删除来料检测标准中的检测项
        return success( receiveStandardItemService.removeReceiveStandardItemByCode(deviceCode,number)).setMsg("移除成功");
    }

}
