package io.metaxk.module.mes.controller.admin.qc;

import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.qc.vo.OtherStandardQueryVo;
import io.metaxk.module.mes.controller.admin.qc.vo.OtherStandardVo;
import io.metaxk.module.mes.dal.dataobject.qc.OtherStandard;
import io.metaxk.module.mes.dal.dataobject.qc.OtherStandardItem;
import io.metaxk.module.mes.service.qc.OtherStandardItemService;
import io.metaxk.module.mes.service.qc.OtherStandardService;
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
import static io.metaxk.module.mes.enums.ErrorCodeConstants.*;

/**
 * @author 万界星空
 * @time 2023/8/19 10:44
 */
@Tag(name = "管理后台 - 其他检验标准")
@RestController
@RequestMapping("/qc/other/standard")
public class OtherStandardController {

    @Resource
    private OtherStandardService otherStandardService;
    @Resource
    private OtherStandardItemService otherStandardItemService;

    @GetMapping("/list")
    @Operation(summary = "其他检验标准列表")
    public CommonResult<PageResult<OtherStandard>> list(OtherStandardQueryVo otherStandardQueryVo){
        PageResult<OtherStandard> pageResult = otherStandardService.findPage(otherStandardQueryVo);
        return success(pageResult);
    }


    @PostMapping("/save")
    @Operation(summary = "新增其他检验标准")
    public CommonResult<Integer> save(@RequestBody OtherStandard otherStandard){
        List<OtherStandard> otherStandardList = otherStandardService.findOtherStandardList();
        if(!otherStandardList.isEmpty()){
            for(OtherStandard otherStandard1:otherStandardList){
                String model = otherStandard1.getModel();
                String[] modelArray = model.split(",");
                for (String m : modelArray) {
                    List<String> modelList = otherStandard.getModelList();
                    for(String models:modelList){
                        List<OtherStandard> otherStandardList1 = otherStandardService.findOtherStandardByModel(m);
                        for(OtherStandard otherStandardDo:otherStandardList1) {
                            System.out.println("===============输出flag============" + otherStandardDo.getEnableFlag());
                            if (m.equals(models) && "true".equals(otherStandardDo.getEnableFlag())) {
                                throw exception(MODEL_STANDARD_ENABLED);
                            }
                        }
                    }
                }

                String spec = otherStandard1.getSpec();
                String[] specArray = spec.split(",");
                for (String s : specArray) {
                    List<String> specList = otherStandard.getSpecList();
                    for(String specs:specList){
                        List<OtherStandard> otherStandardList1 = otherStandardService.findOtherStandardBySpec(s);
                        for(OtherStandard otherStandardDo:otherStandardList1) {
                            System.out.println("===============输出flag============" + otherStandardDo.getEnableFlag());
                            if (s.equals(specs) && "true".equals(otherStandardDo.getEnableFlag())) {
                                throw exception(SPC_STANDARD_ENABLED);
                            }
                        }
                    }
                }

                String lineType = otherStandard1.getLineType();
                String[] lineTypeArray = lineType.split(",");
                for (String l : lineTypeArray) {
                    List<String> lineTypeList = otherStandard.getLineTypeList();
                    for(String lineTypes:lineTypeList){
                        List<OtherStandard> otherStandardList1 = otherStandardService.findOtherStandardByLineType(l);
                        for(OtherStandard otherStandardDo:otherStandardList1) {
                            System.out.println("===============输出flag============" + otherStandardDo.getEnableFlag());
                            if (l.equals(lineTypes) && "true".equals(otherStandardDo.getEnableFlag())) {
                                throw exception(LINE_TYPE_STANDARD_ENABLED);
                            }
                        }
                    }
                }
            }
        }
        StringBuilder models = new StringBuilder();
        StringBuilder specs = new StringBuilder();
        StringBuilder lineTypes = new StringBuilder();
        StringBuilder reelNumbers = new StringBuilder();
        StringBuilder colors = new StringBuilder();
        StringBuilder productTypeCodes = new StringBuilder();

        List<String> modelList = otherStandard.getModelList();
        for (String model : modelList) {
            models.append(model).append(",");
        }
        //将最后一个逗号去除
        if (models.length() > 0) {
            models.deleteCharAt(models.length() - 1);
        }
        otherStandard.setModel(models.toString());

        List<String> speclList = otherStandard.getSpecList();
        for (String spec : speclList) {
            specs.append(spec).append(",");
        }
        //将最后一个逗号去除
        if (specs.length() > 0) {
            specs.deleteCharAt(specs.length() - 1);
        }
        otherStandard.setSpec(specs.toString());

        List<String> lineTypeList = otherStandard.getLineTypeList();
        for (String lineType : lineTypeList) {
            lineTypes.append(lineType).append(",");
        }
        //将最后一个逗号去除
        if (lineTypes.length() > 0) {
            lineTypes.deleteCharAt(lineTypes.length() - 1);
        }
        otherStandard.setLineType(lineTypes.toString());

        List<String> reelNumberList = otherStandard.getReelNumberList();
        if (reelNumberList != null){
            for (String reelNumber : reelNumberList) {
                reelNumbers.append(reelNumber).append(",");
            }
            //将最后一个逗号去除
            if (reelNumbers.length() > 0) {
                reelNumbers.deleteCharAt(reelNumbers.length() - 1);
            }
        }
        otherStandard.setReelNumber(reelNumbers.toString());

        List<String> colorList = otherStandard.getColorList();
        if (colorList != null){
            for (String color : colorList) {
                colors.append(color).append(",");
            }
            //将最后一个逗号去除
            if (colors.length() > 0) {
                colors.deleteCharAt(colors.length() - 1);
            }
        }
        otherStandard.setColor(colors.toString());

        /*List<String> productTypeList = otherStandard.getProductTypeList();
        for (String productTypeCode : productTypeList) {
            productTypeCodes.append(productTypeCode).append(",");
        }
        //将最后一个逗号去除
        if (productTypeCodes.length() > 0) {
            productTypeCodes.deleteCharAt(productTypeCodes.length() - 1);
        }
        otherStandard.setProductType(productTypeCodes.toString());//产品分类*/

        List<OtherStandardVo> otherStandardVoList = otherStandard.getOtherStandardVoList();
        for (OtherStandardVo otherStandardVo  : otherStandardVoList) {
            if (StringUtils.isNotBlank(otherStandardVo.getItemDevice()) && StringUtils.isNotBlank(otherStandardVo.getItemName()) && StringUtils.isNotBlank(otherStandardVo.getItemStandard()) && StringUtils.isNotBlank(otherStandardVo.getRemark())) {
                OtherStandardItem otherStandardItem = new OtherStandardItem();
                otherStandardItem
                        .setEnableFlag(otherStandard.getEnableFlag())
                        .setOtherStandardNumber(otherStandard.getNumber())//其他检验标准编号
                        .setName(otherStandardVo.getItemName())
                        .setStandard(otherStandardVo.getItemStandard())
                        .setDevice(otherStandardVo.getItemDevice())
                        .setRemark(otherStandardVo.getRemark());
                otherStandardItemService.saveOtherStandardItem(otherStandardItem);
            }
        }
        otherStandardService.saveOtherStandard(otherStandard);

        return success(200).setMsg("新增成功");
    }



    @PostMapping("/update")
    @Operation(summary = "修改其他检验标准")
    public CommonResult<Integer> update(@RequestBody OtherStandard otherStandard){
        /*List<OtherStandard> otherStandardList = otherStandardService.findOtherStandardList();
        if(!otherStandardList.isEmpty()){
            for(OtherStandard otherStandard1:otherStandardList){
                String model = otherStandard1.getModel();
                String[] modelArray = model.split(",");
                for (String m : modelArray) {
                    List<String> modelList = otherStandard.getModelList();
                    for(String models:modelList){
                        List<OtherStandard> otherStandardList1 = otherStandardService.findOtherStandardByModel(m);
                        for(OtherStandard otherStandardDo:otherStandardList1) {
                            System.out.println("===============输出model============" + otherStandardDo.getEnableFlag());
                            if (m.equals(models) && "true".equals(otherStandardDo.getEnableFlag())) {
                                throw exception(MODEL_STANDARD_ENABLED);
                            }
                        }
                    }
                }

                String spec = otherStandard1.getSpec();
                String[] specArray = spec.split(",");
                for (String s : specArray) {
                    List<String> specList = otherStandard.getSpecList();
                    for(String specs:specList){
                        List<OtherStandard> otherStandardList1 = otherStandardService.findOtherStandardBySpec(s);
                        for(OtherStandard otherStandardDo:otherStandardList1) {
                            System.out.println("===============输出spec============" + otherStandardDo.getEnableFlag());
                            if (s.equals(specs) && "true".equals(otherStandardDo.getEnableFlag())) {
                                throw exception(SPC_STANDARD_ENABLED);
                            }
                        }
                    }
                }

                String lineType = otherStandard1.getLineType();
                String[] lineTypeArray = lineType.split(",");
                for (String l : lineTypeArray) {
                    List<String> lineTypeList = otherStandard.getLineTypeList();
                    for(String lineTypes:lineTypeList){
                        List<OtherStandard> otherStandardList1 = otherStandardService.findOtherStandardByLineType(l);
                        for(OtherStandard otherStandardDo:otherStandardList1) {
                            System.out.println("===============输出lineType============" + otherStandardDo.getEnableFlag());
                            if (l.equals(lineTypes) && "true".equals(otherStandardDo.getEnableFlag())) {
                                throw exception(LINE_TYPE_STANDARD_ENABLED);
                            }
                        }
                    }
                }
            }
        }*/
        StringBuilder models = new StringBuilder();
        StringBuilder specs = new StringBuilder();
        StringBuilder lineTypes = new StringBuilder();
        StringBuilder reelNumbers = new StringBuilder();
        StringBuilder colors = new StringBuilder();
        StringBuilder productTypeCodes = new StringBuilder();

        List<String> modelList = otherStandard.getModelList();
        for (String model : modelList) {
            models.append(model).append(",");
        }
        //将最后一个逗号去除
        if (models.length() > 0) {
            models.deleteCharAt(models.length() - 1);
        }
        otherStandard.setModel(models.toString());

        List<String> speclList = otherStandard.getSpecList();
        for (String spec : speclList) {
            specs.append(spec).append(",");
        }
        //将最后一个逗号去除
        if (specs.length() > 0) {
            specs.deleteCharAt(specs.length() - 1);
        }
        otherStandard.setSpec(specs.toString());

        List<String> lineTypeList = otherStandard.getLineTypeList();
        for (String lineType : lineTypeList) {
            lineTypes.append(lineType).append(",");
        }
        //将最后一个逗号去除
        if (lineTypes.length() > 0) {
            lineTypes.deleteCharAt(lineTypes.length() - 1);
        }
        otherStandard.setLineType(lineTypes.toString());

        List<String> reelNumberList = otherStandard.getReelNumberList();
        if (reelNumberList != null){
            for (String reelNumber : reelNumberList) {
                reelNumbers.append(reelNumber).append(",");
            }
            //将最后一个逗号去除
            if (reelNumbers.length() > 0) {
                reelNumbers.deleteCharAt(reelNumbers.length() - 1);
            }
        }
        otherStandard.setReelNumber(reelNumbers.toString());

        List<String> colorList = otherStandard.getColorList();
        if (colorList != null){
            for (String color : colorList) {
                colors.append(color).append(",");
            }
            //将最后一个逗号去除
            if (colors.length() > 0) {
                colors.deleteCharAt(colors.length() - 1);
            }
        }
        otherStandard.setColor(colors.toString());

        /*List<String> productTypeList = otherStandard.getProductTypeList();
        for (String productTypeCode : productTypeList) {
            productTypeCodes.append(productTypeCode).append(",");
        }
        //将最后一个逗号去除
        if (productTypeCodes.length() > 0) {
            productTypeCodes.deleteCharAt(productTypeCodes.length() - 1);
        }
        otherStandard.setProductType(productTypeCodes.toString());//产品分类*/

        //先删除检验标准明细，再添加
        otherStandardItemService.deleteOtherStandardItem(otherStandard.getNumber());
        List<OtherStandardVo> otherStandardVoList = otherStandard.getOtherStandardVoList();
        for (OtherStandardVo otherStandardVo  : otherStandardVoList) {
            if (StringUtils.isNotBlank(otherStandardVo.getItemDevice()) && StringUtils.isNotBlank(otherStandardVo.getItemName()) && StringUtils.isNotBlank(otherStandardVo.getItemStandard()) && StringUtils.isNotBlank(otherStandardVo.getRemark())) {
                OtherStandardItem otherStandardItem = new OtherStandardItem();
                otherStandardItem
                        .setEnableFlag(otherStandard.getEnableFlag())
                        .setOtherStandardNumber(otherStandard.getNumber())//其他检验标准编号
                        .setName(otherStandardVo.getItemName())
                        .setStandard(otherStandardVo.getItemStandard())
                        .setDevice(otherStandardVo.getItemDevice())
                        .setRemark(otherStandardVo.getRemark());
                otherStandardItemService.saveOtherStandardItem(otherStandardItem);
            }
        }
        otherStandardService.updateOtherStandard(otherStandard);

        return success(200).setMsg("修改成功");
    }


    @GetMapping("/find/{id}")
    @Operation(summary = "查询其他检验标准详情")
    public CommonResult<OtherStandard> findReceiveStandard(@PathVariable Long id){
        OtherStandard otherStandard = otherStandardService.findOtherStandard(id);
        String model = otherStandard.getModel();//型号
        String[] modelArray = model.split(",");
        otherStandard.setModelList(Arrays.asList(modelArray));

        String spec = otherStandard.getSpec();//规格
        String[] specArray = spec.split(",");
        otherStandard.setSpecList(Arrays.asList(specArray));

        String lineType = otherStandard.getLineType();//线别
        String[] lineTypeArray = lineType.split(",");
        otherStandard.setLineTypeList(Arrays.asList(lineTypeArray));

        String reelNumber = otherStandard.getReelNumber();//线别
        String[] reelNumberArray = reelNumber.split(",");
        otherStandard.setReelNumberList(Arrays.asList(reelNumberArray));

        String color = otherStandard.getColor();//线别
        String[] colorArray = color.split(",");
        otherStandard.setColorList(Arrays.asList(colorArray));

        /*String productType = otherStandard.getProductType();//产品分类
        String[] productTypeArray = productType.split(",");
        otherStandard.setProductTypeList(Arrays.asList(productTypeArray));*/

        String number = otherStandard.getNumber();//其他检验标准编号
        List<OtherStandardItem> otherStandardItemList = otherStandardItemService.findOtherStandardItemByCode(number);
        ArrayList<OtherStandardVo> arrayList = new ArrayList<>();
        for(OtherStandardItem otherStandardItem:otherStandardItemList){
            OtherStandardVo otherStandardVo = new OtherStandardVo();
            otherStandardVo.setItemName(otherStandardItem.getName());
            otherStandardVo.setItemStandard(otherStandardItem.getStandard());
            otherStandardVo.setItemDevice(otherStandardItem.getDevice());
            otherStandardVo.setRemark(otherStandardItem.getRemark());
            arrayList.add(otherStandardVo);
        }
        otherStandard.setOtherStandardVoList(arrayList);
        return success(otherStandard);
    }


    @DeleteMapping("/batchDelete")
    @Operation(summary = "删除其他检验标准")
    public CommonResult<Integer> batchDelete(@RequestBody List<Long> ids){
        for(Long id:ids){
            OtherStandard otherStandard = otherStandardService.findOtherStandard(id);
            otherStandardItemService.deleteOtherStandardItem(otherStandard.getNumber());
        }
        return success( otherStandardService.deleteOtherStandard(ids)).setMsg("删除成功");
    }


    @DeleteMapping("/remove/{deviceCode}/{number}")
    @Operation(summary = "修改的时候移除检测标准中的检测项")
    public CommonResult<Integer> removeStandDevice(@PathVariable String deviceCode,@PathVariable String number){
        //根据器具编号与检验标准编号，删除来料检测标准中的检测项
        return success( otherStandardItemService.removeOtherStandardItemByCode(deviceCode,number)).setMsg("移除成功");
    }
}
