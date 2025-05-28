package io.metaxk.module.mes.service.impl.wh;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.common.UserConstants;
import io.metaxk.module.mes.controller.admin.wh.vo.InboundRecBillItemVo;
import io.metaxk.module.mes.controller.admin.wh.vo.InboundRecBillQueryVo;
import io.metaxk.module.mes.controller.admin.wh.vo.InboundRecBillVo;
import io.metaxk.module.mes.dal.dataobject.order.PurchaseOrder;
import io.metaxk.module.mes.dal.dataobject.order.PurchaseOrderItem;
import io.metaxk.module.mes.dal.dataobject.qc.ReceiveRecord;
import io.metaxk.module.mes.dal.dataobject.qc.ReceiveStandard;
import io.metaxk.module.mes.dal.dataobject.qc.ReceiveStandardItem;
import io.metaxk.module.mes.dal.dataobject.wh.InboundRecBill;
import io.metaxk.module.mes.dal.dataobject.wh.InboundRecBillItem;
import io.metaxk.module.mes.dal.mysql.order.PurchaseOrderItemMapper;
import io.metaxk.module.mes.dal.mysql.order.PurchaseOrderMapper;
import io.metaxk.module.mes.dal.mysql.qc.ReceiveRecordMapper;
import io.metaxk.module.mes.dal.mysql.qc.ReceiveStandardItemMapper;
import io.metaxk.module.mes.dal.mysql.qc.ReceiveStandardMapper;
import io.metaxk.module.mes.dal.mysql.wh.InboundRecBillItemMapper;
import io.metaxk.module.mes.dal.mysql.wh.InboundRecBillMapper;
import io.metaxk.module.mes.service.order.PurchaseOrderItemService;
import io.metaxk.module.mes.service.qc.ReceiveRecordService;
import io.metaxk.module.mes.service.qc.ReceiveStandardItemService;
import io.metaxk.module.mes.service.qc.ReceiveStandardService;
import io.metaxk.module.mes.service.wh.InboundRecBillService;
import io.metaxk.module.mes.utils.AutoCodeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 到货通知ServiceImpl
 *
 * @author 万界星空MES
 */
@Service
public class InboundRecBillServiceImpl implements InboundRecBillService {

    @Resource
    private PurchaseOrderMapper purchaseOrderMapper;
    @Resource
    private PurchaseOrderItemMapper purchaseOrderItemMapper;
    @Resource
    private InboundRecBillMapper inboundRecBillMapper;
    @Resource
    private InboundRecBillItemMapper inboundRecBillItemMapper;
    @Resource
    private ReceiveRecordMapper receiveRecordMapper;

    @Resource
    private AutoCodeUtil autoCodeUtil;
    @Resource
    private PurchaseOrderItemService purchaseOrderItemService;
    @Resource
    private ReceiveStandardService receiveStandardService;
    @Resource
    private ReceiveStandardItemService receiveStandardItemService;
    @Resource
    private ReceiveRecordService receiveRecordService;
    @Resource
    private ReceiveStandardMapper receiveStandardMapper;
    @Resource
    private ReceiveStandardItemMapper receiveStandardItemMapper;


    @Override
    public PageResult<InboundRecBill> findPage(InboundRecBillQueryVo inboundRecBillQueryVo) {
        return inboundRecBillMapper.findPage(inboundRecBillQueryVo);
    }

    @Override
    public void insertInboundRecBill(InboundRecBillVo inboundRecBillVo) throws ParseException {
        //自动生成到货通知单
        //String receiveBillCode = autoCodeUtil.genSerialCode(UserConstants.RECEIVE_BILL_CODE, null);
        PurchaseOrder purchaseOrder = purchaseOrderMapper.selectById(inboundRecBillVo.getId());
        /*//根据采购单编号查询采购单信息
        PurchaseOrder purchaseOrder = purchaseOrderMapper.findPurchaseByNum(inboundRecBillVo.getReceiptNumber());*/
        InboundRecBill inboundRecBill = new InboundRecBill();
        inboundRecBill.setNumber(inboundRecBillVo.getReceiveBillCode());//到货通知单编号
        inboundRecBill.setReceiptNumber(inboundRecBillVo.getReceiptNumber());//采购单编号
        inboundRecBill.setAddress(purchaseOrder.getAddress());
        inboundRecBill.setVendorName(purchaseOrder.getVendorName());
        inboundRecBill.setWareHouse(inboundRecBillVo.getWareHouse());//仓库
        inboundRecBill.setTaxRate(purchaseOrder.getTaxRate());
        inboundRecBill.setDeliveryDate(purchaseOrder.getDeliveryDate());
        inboundRecBill.setProductType(purchaseOrder.getProductType());
        inboundRecBill.setNoIncludTax(purchaseOrder.getTaxPrice());
        inboundRecBill.setIncludTax(purchaseOrder.getTaxPrice());
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(inboundRecBillVo.getReceiveDate());
        inboundRecBill.setReceiveDate(date);//到货时间
        inboundRecBill.setStatus("0");//0:未质检1:已质检
        inboundRecBillMapper.insert(inboundRecBill);


        List<InboundRecBillItemVo> inboundRecBillItemVoList = inboundRecBillVo.getInboundRecBillItemVoList();
        for (InboundRecBillItemVo inboundRecBillItemVo:inboundRecBillItemVoList){
            PurchaseOrderItem purchaseOrderItem = purchaseOrderItemMapper.selectById(inboundRecBillItemVo.getId());

            //List<PurchaseOrderItem> purchaseOrderItemList = purchaseOrderItemMapper.selectBatchIds(ids);
            //for (PurchaseOrderItem purchaseOrderItem:purchaseOrderItemList){
            InboundRecBillItem inboundRecBillItem = new InboundRecBillItem();
            inboundRecBillItem.setRecNumber(inboundRecBillVo.getReceiveBillCode());//到货通知单编号
            inboundRecBillItem.setItemCode(purchaseOrderItem.getItemCode());
            inboundRecBillItem.setItemName(purchaseOrderItem.getItemName());
            inboundRecBillItem.setModel(purchaseOrderItem.getModel());
            inboundRecBillItem.setSpec(purchaseOrderItem.getSpec());
            inboundRecBillItem.setKind(purchaseOrderItem.getKind());
            inboundRecBillItem.setUnitOfMeasure(purchaseOrderItem.getUnitOfMeasure());
            inboundRecBillItem.setPurchasePrice(purchaseOrderItem.getPurchasePrice());//采购价
            inboundRecBillItem.setQuantity(inboundRecBillItemVo.getAmount());//数量(到货数)
            //inboundRecBillItem.setIncludTax(purchaseOrderItem.getIncludTax());
            //inboundRecBillItem.setNoIncludTax(purchaseOrderItem.getNoIncludTax());
            if (purchaseOrderItem.getNoIncludTax() == null) {
                //到货数量*单价
                inboundRecBillItem.setIncludTax(inboundRecBillItemVo.getAmount().multiply(purchaseOrderItem.getPurchasePrice()));
            }else {
                inboundRecBillItem.setNoIncludTax(inboundRecBillItemVo.getAmount().multiply(purchaseOrderItem.getPurchasePrice()));
            }
            inboundRecBillItem.setVendor(purchaseOrderItem.getVendor());
            inboundRecBillItemMapper.insert(inboundRecBillItem);

            BigDecimal num = purchaseOrderItem.getArrivedQuantity();
            num = num.add(inboundRecBillItemVo.getAmount());
            //修改采购订单明细的已到货数
            purchaseOrderItem.setArrivedQuantity(num);
            purchaseOrderItemMapper.updateById(purchaseOrderItem);

            //添加来料检验单信息
            addReceiveRecord(inboundRecBillVo.getReceiveBillCode(),purchaseOrderItem,inboundRecBillItemVo.getAmount());
            // }
        }

        //根据采购订单编号查询采购明细信息
        List<PurchaseOrderItem> purchaseOrderItemList = purchaseOrderItemService.findReceiptItemByNum(inboundRecBillVo.getReceiptNumber());
        BigDecimal num1 = BigDecimal.ZERO;
        for (PurchaseOrderItem p:purchaseOrderItemList){
            //num1 = num1.add(p.getArrivedQuantity());
            num1 = num1.add(p.getQuantity());
        }

        //根据采购订单编号查询到货通知明细
        List<InboundRecBillItemVo> inboundRecBillItemVoList1 = inboundRecBillItemMapper.findInboundRecBillItem(inboundRecBillVo.getReceiptNumber());
        BigDecimal num2 = BigDecimal.ZERO;
        for (InboundRecBillItemVo v:inboundRecBillItemVoList1){
            num2 = num2.add(v.getAmount());
        }

        if (num1.compareTo(num2) == 0){//相等时
            //同一采购订单下的产品已到货数量之和=同一采购订单下到货通知单的数量之和，则采购订单的状态为全部到货
            purchaseOrder.setState("2");
        }else if (num1.compareTo(num2) == 1){//num1>num2
            //同一采购订单下的产品数量之和>同一采购订单下到货通知单的已到数量之和，则采购订单的状态为部分到货
            purchaseOrder.setState("1");
        }
        //修改采购订单的状态
        purchaseOrderMapper.updateById(purchaseOrder);
    }

    @Override
    public void insertInboundRecBillAll(InboundRecBillVo inboundRecBillVo) {
        //自动生成到货通知单
        //String receiveBillCode = autoCodeUtil.genSerialCode(UserConstants.RECEIVE_BILL_CODE, null);
        String receiveBillCode = inboundRecBillVo.getReceiveBillCode();
        PurchaseOrder purchaseOrder = purchaseOrderMapper.selectById(inboundRecBillVo.getId());
        InboundRecBill inboundRecBill = new InboundRecBill();
        inboundRecBill.setNumber(receiveBillCode);//到货通知单编号
        inboundRecBill.setReceiptNumber(inboundRecBillVo.getReceiptNumber());//采购单编号
        inboundRecBill.setCurrency(purchaseOrder.getCurrency());
        inboundRecBill.setAddress(purchaseOrder.getAddress());
        inboundRecBill.setVendorName(purchaseOrder.getVendorName());
        inboundRecBill.setWareHouse(inboundRecBillVo.getWareHouse());//仓库
        inboundRecBill.setTaxRate(purchaseOrder.getTaxRate());
        inboundRecBill.setDeliveryDate(purchaseOrder.getDeliveryDate());
        inboundRecBill.setProductType(purchaseOrder.getProductType());
        inboundRecBill.setNoIncludTax(purchaseOrder.getNoTaxPrice());
        inboundRecBill.setIncludTax(purchaseOrder.getTaxPrice());
        inboundRecBill.setStatus("0");//0:未质检1:已质检
        inboundRecBillMapper.insert(inboundRecBill);

        //根据采购单编号，查询采购单明细
        List<PurchaseOrderItem> purchaseOrderItemList = purchaseOrderItemMapper.selectReceiptItemByReceiptNumber(inboundRecBillVo.getReceiptNumber());
        for (PurchaseOrderItem purchaseOrderItem:purchaseOrderItemList){
            InboundRecBillItem inboundRecBillItem = new InboundRecBillItem();
            inboundRecBillItem.setRecNumber(receiveBillCode);//到货通知单编号
            inboundRecBillItem.setItemCode(purchaseOrderItem.getItemCode());
            inboundRecBillItem.setItemName(purchaseOrderItem.getItemName());
            inboundRecBillItem.setModel(purchaseOrderItem.getModel());
            inboundRecBillItem.setSpec(purchaseOrderItem.getSpec());
            inboundRecBillItem.setKind(purchaseOrderItem.getKind());
            inboundRecBillItem.setUnitOfMeasure(purchaseOrderItem.getUnitOfMeasure());
            inboundRecBillItem.setPurchasePrice(purchaseOrderItem.getPurchasePrice());
            inboundRecBillItem.setQuantity(purchaseOrderItem.getQuantity());//数量
            inboundRecBillItem.setIncludTax(purchaseOrderItem.getIncludTax());
            inboundRecBillItem.setNoIncludTax(purchaseOrderItem.getNoIncludTax());
            inboundRecBillItem.setVendor(purchaseOrderItem.getVendor());
            inboundRecBillItemMapper.insert(inboundRecBillItem);

            //修改采购订单明细的已到货数
            purchaseOrderItem.setArrivedQuantity(purchaseOrderItem.getQuantity());
            purchaseOrderItemMapper.updateById(purchaseOrderItem);

            //添加来料检验单信息
            addReceiveRecord(receiveBillCode,purchaseOrderItem,purchaseOrderItem.getQuantity());

        }

        //修改采购订单的状态
        purchaseOrder.setState("2");//状态为2：全部到货
        purchaseOrderMapper.updateById(purchaseOrder);
    }

    @Override
    public Integer updateStatus(String number,String status) {
        LambdaQueryWrapperX<InboundRecBill> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(InboundRecBill::getNumber,number);
        InboundRecBill inboundRecBill = inboundRecBillMapper.selectOne(queryWrapperX);
        inboundRecBill.setStatus(status);
        return inboundRecBillMapper.updateById(inboundRecBill);
    }

    @Override
    public InboundRecBill selectByNumber(String number) {
        LambdaQueryWrapperX<InboundRecBill> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(InboundRecBill::getNumber,number);
        return inboundRecBillMapper.selectOne(queryWrapperX);
    }


    public void addReceiveRecord(String receiveBillCode,PurchaseOrderItem purchaseOrderItem,BigDecimal amount){
        //自动生成到来料检验单编号
        String receiveRecordCode = autoCodeUtil.genSerialCode(UserConstants.RECEIVE_RECORD_CODE, null);
        //来料检验单信息
        ReceiveRecord receiveRecord = new ReceiveRecord();
        receiveRecord.setRecordCode(receiveRecordCode);//来料检验单编号
        receiveRecord.setRecNumber(receiveBillCode);//到货通知单编号
        /*receiveRecord.setItemCode(purchaseOrderItem.getItemCode());
        receiveRecord.setItemName(purchaseOrderItem.getItemName());
        receiveRecord.setModel(purchaseOrderItem.getModel());
        receiveRecord.setSpec(purchaseOrderItem.getSpec());
        receiveRecord.setKind(purchaseOrderItem.getKind());
        receiveRecord.setUnitOfMeasure(purchaseOrderItem.getUnitOfMeasure());
        receiveRecord.setPurchasePrice(purchaseOrderItem.getPurchasePrice());*/
        receiveRecord.setQuantity(amount);//到货数量
        //receiveRecord.setIncludTax(purchaseOrderItem.getIncludTax());
        //receiveRecord.setNoIncludTax(purchaseOrderItem.getNoIncludTax());
        /*if (purchaseOrderItem.getNoIncludTax() == null) {
            //到货数量*单价
            receiveRecord.setIncludTax(amount.multiply(purchaseOrderItem.getPurchasePrice()));
        }else {
            receiveRecord.setNoIncludTax(amount.multiply(purchaseOrderItem.getPurchasePrice()));
        }
        receiveRecord.setVendor(purchaseOrderItem.getVendor());*/

        String itemCode = purchaseOrderItem.getItemCode();
        String enableFlag = "true";
        //根据产品编号，查询启用的来料检验标准信息。
        ReceiveStandard receiveStandard = receiveStandardService.findReceiveStandardByItemCode(itemCode,enableFlag);
        //String method = receiveStandard.getMethod();//检测方式
        receiveRecord.setCheckNumber(new BigDecimal(receiveStandard.getQuantity()));//抽检数
        receiveRecord.setInspectWay(receiveStandard.getMethod());//检测方式
        //根据产品编号，查询标准检测项
        List<ReceiveStandardItem> receiveStandardItemList = receiveStandardItemService.findReceiveStandardItemByItemCode(itemCode);
        if (receiveStandardItemList !=null){
            receiveRecord.setInspectFlag("1");//是否有检验项目0：否1：是
        }else {
            receiveRecord.setInspectFlag("0");//是否有检验项目0：否1：是
        }

        //状态0：未质检1：已质检
        receiveRecord.setStatus("0");//状态，默认0：未质检

        //添加
        receiveRecordMapper.insert(receiveRecord);
    }
}
