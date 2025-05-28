package io.metaxk.module.mes.service.impl.qc;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.qc.vo.OtherRecordExcelVo;
import io.metaxk.module.mes.controller.admin.qc.vo.OtherRecordQueryVo;
import io.metaxk.module.mes.controller.admin.qc.vo.ProcessRecordExcelVo;
import io.metaxk.module.mes.dal.dataobject.order.PurchaseOrderItem;
import io.metaxk.module.mes.dal.dataobject.qc.OtherRecord;
import io.metaxk.module.mes.dal.dataobject.qc.OtherRecordItem;
import io.metaxk.module.mes.dal.dataobject.qc.OtherRecordResult;
import io.metaxk.module.mes.dal.dataobject.qc.ProcessRecord;
import io.metaxk.module.mes.dal.mysql.qc.OtherRecordItemMapper;
import io.metaxk.module.mes.dal.mysql.qc.OtherRecordMapper;
import io.metaxk.module.mes.dal.mysql.qc.OtherRecordResultMapper;
import io.metaxk.module.mes.service.qc.OtherRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/8/19 10:43
 */
@Service
public class OtherRecordServiceImpl implements OtherRecordService {

    @Resource
    private OtherRecordMapper otherRecordMapper;
    @Resource
    private OtherRecordItemMapper otherRecordItemMapper;
    @Resource
    private OtherRecordResultMapper otherRecordResultMapper;

    @Override
    public PageResult<OtherRecord> findPage(OtherRecordQueryVo otherRecordQueryVo) {
        return otherRecordMapper.findPage(otherRecordQueryVo);
    }

    @Override
    public List<OtherRecordExcelVo> exportDate() {
        List<OtherRecord> list = otherRecordMapper.selectList();
        ArrayList<OtherRecordExcelVo> excelList = new ArrayList<>(list.size());
        list.forEach(dict -> {
            OtherRecordExcelVo excelDTO = new OtherRecordExcelVo();
            BeanUtils.copyProperties(dict, excelDTO);
            if ("fullInspection".equals(excelDTO.getInspectWay())){
                excelDTO.setInspectWay("全检");
            }else if("casualInspection".equals(excelDTO.getInspectWay())){
                excelDTO.setInspectWay("抽检");
            }else if ("batchInspection".equals(excelDTO.getInspectWay())){
                excelDTO.setInspectWay("批量检");
            }

            if ("0".equals(excelDTO.getStatus())){
                excelDTO.setStatus("不合格");
            }else if("1".equals(excelDTO.getStatus())){
                excelDTO.setStatus("合格");
            }
            excelList.add(excelDTO);

        });
        return excelList;
    }


    @Override
    public Integer saveOtherRecord(OtherRecord otherRecord) {
        System.out.println("============otherRecord.getNumber():"+otherRecord.getNumber());
        List<OtherRecordItem> otherRecordItemList = otherRecord.getOtherRecordItemList();
        for (OtherRecordItem otherRecordItem : otherRecordItemList){
            otherRecordItem.setRecordNumber(otherRecord.getNumber());//检验单编码
            otherRecordItemMapper.insert(otherRecordItem);
        }
        //添加检验结果
        OtherRecordResult otherRecordResult = new OtherRecordResult();
        otherRecordResult.setRecordNumber(otherRecord.getNumber());
        otherRecordResult.setModel(otherRecord.getModel());
        otherRecordResult.setSpec(otherRecord.getSpec());
        otherRecordResult.setLineType(otherRecord.getLineType());
        otherRecordResult.setReelNumber(otherRecord.getReelNumber());
        otherRecordResult.setColor(otherRecord.getColor());
        otherRecordResult.setResultStatus(otherRecord.getStatus());
        otherRecordResultMapper.insert(otherRecordResult);

        return otherRecordMapper.insert(otherRecord);
    }

    @Override
    public Integer updateOtherRecord(OtherRecord otherRecord) {

        //otherRecordItemMapper.delete(new LambdaQueryWrapperX<OtherRecordItem>().eq(OtherRecordItem::getRecordId, otherRecord.getRecordCode()));
        List<OtherRecordItem> otherRecordItemList = otherRecord.getOtherRecordItemList();
        for (OtherRecordItem otherRecordItem : otherRecordItemList){
            otherRecordItem.setRecordNumber(otherRecord.getNumber());//检验单编码
            otherRecordItemMapper.updateById(otherRecordItem);
        }
        //修改检验结果
        OtherRecordResult otherRecordResult = otherRecordResultMapper.selectOtherRecordResultByNumber(otherRecord.getNumber());
        otherRecordResult.setRecordNumber(otherRecord.getNumber());
        otherRecordResult.setModel(otherRecord.getModel());
        otherRecordResult.setSpec(otherRecord.getSpec());
        otherRecordResult.setLineType(otherRecord.getLineType());
        otherRecordResult.setReelNumber(otherRecord.getReelNumber());
        otherRecordResult.setColor(otherRecord.getColor());
        otherRecordResult.setResultStatus(otherRecord.getStatus());
        otherRecordResultMapper.updateById(otherRecordResult);

        return otherRecordMapper.updateById(otherRecord);
    }

    @Override
    public OtherRecord findOtherRecordById(Long id) {
        return otherRecordMapper.selectById(id);
    }


}
