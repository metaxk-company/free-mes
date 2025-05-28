package io.metaxk.module.mes.service.pro;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import io.metaxk.module.mes.controller.admin.pro.vo.ProWorkorderVo;
import io.metaxk.module.mes.controller.admin.pro.vo.WorkorderExcelVo;
import io.metaxk.module.mes.controller.admin.pro.vo.WorkOrderQueryVo;
import io.metaxk.module.mes.dal.dataobject.pro.WorkOrder;
import io.metaxk.framework.common.pojo.PageResult;


/**
 * 生产工单 Service 接口
 * @author 万界星空
 */
public interface WorkOrderService {

    /**
     * 根据id查询订单信息
     * @param id
     * @return Worker
     */
    WorkOrder getWorkorder(Long id);


    /**
     * 订单条件分页
     * @param pageReqVO
     * @return PageResult<Worker>
     */
    PageResult<WorkOrder> getWorkOrderPage(WorkOrderQueryVo pageReqVO);

    /**
     * 查询订单信息
     * @param workOrderVo
     * @return  List<ProWorkorderVo>
     */
    List<ProWorkorderVo> synchronizeOrders(ProWorkorderVo workOrderVo);

    /**
     * 导出订单信息
     * @param
     * @return  List<WorkerExcelVO>
     */
    List<WorkorderExcelVo> listData();

    /**
     * 校验订单信息
     * @param createReqVO
     * @return String
     */
    String checkWorkorderCodeUnique(WorkOrder createReqVO);

    /**
     * 新增订单信息
     * @param worker
     * @return
     */
    void saveWorkOrder(WorkOrder worker);
    /**
     * 根据id查询订单信息
     * @param id
     * @return Worker
     */

    WorkOrder selectProWorkorderByWorkorderId(Long id);

    /**
     * 修改订单信息
     * @param worker
     * @return Worker
     */
    Integer updateProWorkorder(WorkOrder worker);

    /**
     * 批量删除订单信息
     * @param workOrderIds
     * @return Integer
     */
    Integer removeWorkOrderByWorkOrderIds(List<Long> workOrderIds);

    /**
     * 生成工序二维码
     * @param taskCode
     * @param processCode
     * @param processName
     * @param barcodeFormat
     * @return String
     */
    String generateProcessUrl(String taskCode, String processCode, String processName, String barcodeFormat);

    /**
     * 生成任务二维码
     * @param taskCode
     * @param itemCode
     * @param unitOfMeasure
     * @param workOrderCodes
     * @param itemName
     * @param barcodeFormat
     * @return Worker
     */
    String generateTaskUrl(String taskCode, String itemCode, String unitOfMeasure, String workOrderCodes, String itemName, String barcodeFormat);

    /**
     * 查询订单集合
     * @param proWorker
     * @return Worker
     */
    List<WorkOrder> selectProWorkorderList(WorkOrder proWorker);

    /**
     * 同步订单
     * @param
     * @return Worker
     * @throws IOException
     * @throws ParseException
     */
    void syncOrders() throws IOException, ParseException;

    /**
     * 查询同步订单列表
     * @param proWorkerVo
     * @return List<Worker>
     */
    List<WorkOrder> synchronizeOrdersList(ProWorkorderVo proWorkerVo);

    /**
     * 根据id查询订单信息
     * @param workerId
     * @return Worker
     */
    WorkOrder selectByWorkOrderId(Long workerId);

    /**
     * 查询所有订单
     * @param
     * @return  List<Worker>
     */
    List<WorkOrder> findWorkerOrderAll();

    /**
     * 根据订单号查询订单信息
     * @param workOrderCode
     * @return Worker
     */
    WorkOrder findWorkOrderCode(String workOrderCode);

    /**
     * 根据id查询订单
     * @param id
     * @return Worker
     */
    WorkOrder findWorkOrderById(Long id);


    Map<String, Object> findRouteProcess(String itemCode);
}
