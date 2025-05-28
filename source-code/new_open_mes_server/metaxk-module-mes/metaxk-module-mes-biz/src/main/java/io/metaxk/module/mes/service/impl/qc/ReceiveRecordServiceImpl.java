package io.metaxk.module.mes.service.impl.qc;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.qc.vo.ProcessRecordExcelVo;
import io.metaxk.module.mes.controller.admin.qc.vo.ReceiveRecordExcelVo;
import io.metaxk.module.mes.controller.admin.qc.vo.ReceiveRecordQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.ReturnsItem;
import io.metaxk.module.mes.dal.dataobject.qc.ProcessRecord;
import io.metaxk.module.mes.dal.dataobject.qc.ReceiveRecord;
import io.metaxk.module.mes.dal.dataobject.qc.ReceiveStandard;
import io.metaxk.module.mes.dal.dataobject.qc.ReceiveStandardItem;
import io.metaxk.module.mes.dal.mysql.qc.ReceiveRecordMapper;
import io.metaxk.module.mes.dal.mysql.qc.ReceiveStandardItemMapper;
import io.metaxk.module.mes.dal.mysql.qc.ReceiveStandardMapper;
import io.metaxk.module.mes.service.qc.ReceiveRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/26 14:59
 */
@Service
public class ReceiveRecordServiceImpl implements ReceiveRecordService {

    @Resource
    private ReceiveRecordMapper receiveRecordMapper;
    @Resource
    private ReceiveStandardMapper receiveStandardMapper;
    @Resource
    private ReceiveStandardItemMapper receiveStandardItemMapper;


    @Override
    public PageResult<ReceiveRecord> findPage(ReceiveRecordQueryVo receiveRecordQueryVo) {
        return receiveRecordMapper.findPage(receiveRecordQueryVo);
    }

    @Override
    public List<ReceiveRecordExcelVo> exportData() {
        List<ReceiveRecord> list = receiveRecordMapper.selectList();
        ArrayList<ReceiveRecordExcelVo> excelList = new ArrayList<>(list.size());
        list.forEach(dict -> {
            ReceiveRecordExcelVo excelDTO = new ReceiveRecordExcelVo();
            BeanUtils.copyProperties(dict, excelDTO);
            if ("fullInspection".equals(excelDTO.getInspectWay())){
                excelDTO.setInspectWay("全检");
            }else if("casualInspection".equals(excelDTO.getInspectWay())){
                excelDTO.setInspectWay("抽检");
            }else if ("batchInspection".equals(excelDTO.getInspectWay())){
                excelDTO.setInspectWay("批量检");
            }

            excelList.add(excelDTO);
        });
        return excelList;
    }

    @Override
    public Integer updateReceiveRecord(ReceiveRecord receiveRecord) {
        ReceiveRecord r = receiveRecordMapper.selectById(receiveRecord.getId());
        if (receiveRecord.getInspectStartTime() != null && !"".equals(receiveRecord.getInspectStartTime())){
            r.setInspectStartTime(receiveRecord.getInspectStartTime());//检验开始时间
        }
        if (receiveRecord.getInspectEndTime() != null && !"".equals(receiveRecord.getInspectEndTime())){
            r.setInspectEndTime(receiveRecord.getInspectEndTime());//检验结束时间
        }
        if (receiveRecord.getInspectUser() != null && !"".equals(receiveRecord.getInspectUser())){
            r.setInspectUser(receiveRecord.getInspectUser());//质检员
        }
        return receiveRecordMapper.updateById(r);
    }

    @Override
    public ReceiveRecord findReceiveRecordById(Long id) {
        return receiveRecordMapper.selectById(id);
    }

    @Override
    public Integer updateStatus(Long id, String status) {
        ReceiveRecord receiveRecord = receiveRecordMapper.selectById(id);
        receiveRecord.setStatus(status);
        return receiveRecordMapper.updateById(receiveRecord);
    }

    @Override
    public Integer updateReceiveRecords(ReceiveRecord receiveRecord) {

        //首先查来料对应的检验标准是否有启用的版本，是启用则查询是否存在已启用的来料检验标准对应来料的检测项目，不存在inspectFlag赋值为0,存在inspectFlag赋值为1。
        //是禁用则直接inspectFlag赋值为0。
        //查询来料标准明细表
        List<ReceiveStandardItem> receiveStandardItemList = receiveStandardItemMapper.findReceiveStandardItemByItemCode(receiveRecord.getItemCode());
        System.out.println("==========bbbbb=====receiveStandardItemList:"+receiveStandardItemList);
        //如果检验标准为空
        if(receiveStandardItemList.isEmpty()){
            List<ReceiveStandard> receiveStandardList = receiveStandardMapper.findReceiveStandardByItemCode(receiveRecord.getItemCode());
            System.out.println("==========bbbbb==111===receiveStandardList:"+receiveStandardList);
            for(ReceiveStandard receiveStandard:receiveStandardList) {
                if ("true".equals(receiveStandard.getEnableFlag())) {
                    //检验方式
                    receiveRecord.setInspectWay(receiveStandard.getMethod());
                    //版本
                    receiveRecord.setVersion(receiveStandard.getVersion());
                    //是否有检验项目0：否1：是
                    receiveRecord.setInspectFlag("0");
                }
            }
        }else {
            for (ReceiveStandardItem receiveStandardItem : receiveStandardItemList) {
                String[] itemCodeArray = receiveStandardItem.getItemCode().split(",");
                for (String code : itemCodeArray) {
                    List<ReceiveStandard> receiveStandardList = receiveStandardMapper.findReceiveStandardByItemCode(code);
                    System.out.println("==========bbbbb==222===receiveStandardList:"+receiveStandardList);
                    for(ReceiveStandard receiveStandard:receiveStandardList){
                        if("true".equals(receiveStandard.getEnableFlag())){
                            //检验方式
                            receiveRecord.setInspectWay(receiveStandard.getMethod());
                            //版本
                            receiveRecord.setVersion(receiveStandard.getVersion());

                            //如果检验标准抽检数>检验单的数量，则抽检数为检验单的数量。
                            if (new BigDecimal(receiveStandard.getQuantity()).compareTo(receiveRecord.getQuantity()) == 1){
                                //抽检数
                                receiveRecord.setCheckNumber(receiveRecord.getQuantity());
                            }else {
                                //抽检数
                                receiveRecord.setCheckNumber(new BigDecimal(receiveStandard.getQuantity()));
                            }
                        }
                    }
                    if (code.equals(receiveRecord.getItemCode()) && "true".equals(receiveStandardItem.getEnableFlag())) {
                        receiveRecord.setInspectFlag("1");
                        break;
                    } else {
                        receiveRecord.setInspectFlag("0");
                    }
                }
            }
        }
        return receiveRecordMapper.updateById(receiveRecord);
    }

    @Override
    public List<ReceiveRecord> findReceiveRecordByRecNumber(String recNumber) {
        LambdaQueryWrapperX<ReceiveRecord> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ReceiveRecord::getRecNumber,recNumber);
        return receiveRecordMapper.selectList(queryWrapperX);
    }

    @Override
    public List<ReceiveRecord> findReceiveRecordByItemCode(String itemCode) {
        LambdaQueryWrapperX<ReceiveRecord> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ReceiveRecord::getItemCode,itemCode);
        return receiveRecordMapper.selectList(queryWrapperX);
    }
}
