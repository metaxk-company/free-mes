package io.metaxk.module.mes.service.impl.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.order.vo.PurchaseOrderReturnExcelVo;
import io.metaxk.module.mes.controller.admin.order.vo.PurchaseOrderReturnQueryVo;
import io.metaxk.module.mes.controller.admin.order.vo.QuoteExportVo;
import io.metaxk.module.mes.dal.dataobject.order.PurchaseOrderReturn;
import io.metaxk.module.mes.dal.dataobject.order.PurchaseOrderReturnItem;
import io.metaxk.module.mes.dal.dataobject.order.Quote;
import io.metaxk.module.mes.dal.mysql.order.PurchaseOrderReturnItemMapper;
import io.metaxk.module.mes.dal.mysql.order.PurchaseOrderReturnMapper;
import io.metaxk.module.mes.service.order.PurchaseOrderReturnService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author 万界星空
 * @time 2023/8/15 11:55
 */
@Service
public class PurchaseOrderReturnServiceImpl implements PurchaseOrderReturnService {

    @Resource
    private PurchaseOrderReturnMapper purchaseOrderReturnMapper;
    @Resource
    private PurchaseOrderReturnItemMapper purchaseOrderReturnItemMapper;


    @Override
    public PageResult<PurchaseOrderReturn> findPage(PurchaseOrderReturnQueryVo purchaseOrderReturnQueryVo) {
        return purchaseOrderReturnMapper.findPage(purchaseOrderReturnQueryVo);
    }

    @Override
    public Integer savePurchaseOrderReturn(PurchaseOrderReturn purchaseOrderReturn) {
        purchaseOrderReturn.setCreateTime(new Date());
        List<PurchaseOrderReturnItem> purchaseOrderReturnItemList = purchaseOrderReturn.getPurchaseOrderReturnItemList();
        for (PurchaseOrderReturnItem purchaseOrderReturnItem : purchaseOrderReturnItemList){
            purchaseOrderReturnItem.setReturnNumber(purchaseOrderReturn.getNumber());
            purchaseOrderReturnItemMapper.insert(purchaseOrderReturnItem);
        }
        return purchaseOrderReturnMapper.insert(purchaseOrderReturn);
    }

    @Override
    public Integer updatePurchaseOrderReturn(PurchaseOrderReturn purchaseOrderReturn) {
        PurchaseOrderReturn purchaseOrderReturnDo = purchaseOrderReturnMapper.selectById(purchaseOrderReturn.getId());
        //修改的时候数组先执行删除方法在添加
        purchaseOrderReturnItemMapper.delete(new LambdaQueryWrapperX<PurchaseOrderReturnItem>().eq(PurchaseOrderReturnItem::getReturnNumber, purchaseOrderReturnDo.getNumber()));
        purchaseOrderReturn.setUpdateTime(new Date());
        List<PurchaseOrderReturnItem> purchaseOrderReturnItemList = purchaseOrderReturn.getPurchaseOrderReturnItemList();
        for (PurchaseOrderReturnItem purchaseOrderReturnItem : purchaseOrderReturnItemList){
            purchaseOrderReturnItem.setReturnNumber(purchaseOrderReturn.getNumber());
            purchaseOrderReturnItemMapper.insert(purchaseOrderReturnItem);
        }
        return purchaseOrderReturnMapper.updateById(purchaseOrderReturn);
    }

    @Override
    public Integer removePurchaseOrderReturn(List<Long> ids) {
        return purchaseOrderReturnMapper.deleteBatchIds(ids);
    }

    @Override
    public PurchaseOrderReturn findPurchaseOrderReturn(Long id) {
        return purchaseOrderReturnMapper.selectById(id);
    }


    @Override
    public List<PurchaseOrderReturnExcelVo> exportData() {
        List<PurchaseOrderReturn> purchaseOrderReturnList = purchaseOrderReturnMapper.selectList();
        ArrayList<PurchaseOrderReturnExcelVo> purchaseOrderReturnExcelVo = new ArrayList<>(purchaseOrderReturnList.size());
        purchaseOrderReturnList.forEach(dict -> {
            PurchaseOrderReturnExcelVo excelDictDTO = new PurchaseOrderReturnExcelVo();
            BeanUtils.copyProperties(dict, excelDictDTO);
            purchaseOrderReturnExcelVo.add(excelDictDTO);
        });
        return purchaseOrderReturnExcelVo;
    }
}
