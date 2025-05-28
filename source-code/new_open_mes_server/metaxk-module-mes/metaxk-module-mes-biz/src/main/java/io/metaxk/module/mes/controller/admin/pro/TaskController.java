package io.metaxk.module.mes.controller.admin.pro;


import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.common.UserConstants;
import io.metaxk.module.mes.controller.admin.pro.vo.*;
import io.metaxk.module.mes.dal.dataobject.dv.Machinery;
import io.metaxk.module.mes.dal.dataobject.md.WorkStation;
import io.metaxk.module.mes.dal.dataobject.md.WorkStationMachine;
import io.metaxk.module.mes.dal.dataobject.pro.*;
import io.metaxk.module.mes.dal.dataobject.pro.Process;
import io.metaxk.module.mes.service.dv.MachineryService;
import io.metaxk.module.mes.service.md.WorkStationMachineService;
import io.metaxk.module.mes.service.md.WorkStationService;
import io.metaxk.module.mes.service.pro.*;
import io.metaxk.module.mes.utils.AutoCodeUtil;
import io.metaxk.module.mes.utils.MesConstantUtils;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.metaxk.framework.common.pojo.CommonResult;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.*;




/**
 * @author 万界星空
 * @time 2023/6/20 15:33
 */
@Tag(name = "管理后台 - 生产任务")
@RestController
@RequestMapping("/mes/pro/task")
public class TaskController {

    @Resource
    private TaskService taskService;

    @Resource
    private WorkOrderService workorderService;

    @Resource
    private WorkStationService workstationService;

    @Resource
    private ProcessService processService;

    @Resource
    private AutoCodeUtil autoCodeUtil;

    @Resource
    private FeedbackService feedbackService;

    @Resource
    private FeedBackStatusService feedBackStatusService;

    @Resource
    private FeedbackEquipmentService feedbackEquipmentService;

    @Resource
    private FeedbackEquStatusService feedbackEquStatusService;

    @Resource
    private RouteProductService routeProductService;

    @Resource
    private RouteProcessService  routeProcessService;

    @Resource
    private FeedbackHoursService  feedbackHoursService;

    @Resource
    private WorkStationMachineService workstationMachineService;

    @Resource
    private MachineryService machineryService;



    @GetMapping("/listByConditional")
    @Operation(summary = "条件查询生产任务")
    public CommonResult<PageResult<TaskListByConditionalVo>>  listByConditional(ProTaskQueryVo task) {
        List<TaskListByConditionalVo> list = taskService.listByConditionals(task);



        PageResult<TaskListByConditionalVo> pageResult = new PageResult<>();
        pageResult.setList(list);
        pageResult.setTotal((long) list.size());
        return success(pageResult);
    }



    @GetMapping("/findTaskByOrderCode")
    @Operation(summary = "根据订单号查询所排的生产任务")
    public CommonResult<PageResult<TaskListByConditionalVo>>  findTaskByOrderCode(WorkerOrderFindVo workOrderCode) {
        List<TaskListByConditionalVo> list = taskService.findTaskByOrderCode(workOrderCode.getWorkOrderCode());
        PageResult<TaskListByConditionalVo> pageResult = new PageResult<>();
        pageResult.setList(list);
        pageResult.setTotal((long) list.size());
        return success(pageResult);
    }




        @Operation(summary = "生产任务条件分页查询")
        @GetMapping("/list")
        public CommonResult  list( TaskQueryVo task){
            HashMap<String, Object> map = new HashMap<>(200);
            PageResult<Task> taskPage = taskService.getTaskPage(task);
            List<Task> taskList = taskPage.getList();
            int countQuantity = 0;
            for (Task proTask : taskPage.getList()) {
                countQuantity += proTask.getQuantity().intValue();
            }
            List<Task>  list = taskList;
            for (Task taskDo : list) {
                taskDo.setCountQuantity(countQuantity);
                WorkOrder workOrderCode = workorderService.findWorkOrderCode(taskDo.getWorkorderCode());
                BigDecimal quantity = workOrderCode.getQuantity();
                taskDo.setCountQuantity(quantity.intValue() - countQuantity);
                map.put("countQuantity",taskDo.getCountQuantity());
            }
            map.put("taskPage",taskPage);
            return  success(map);
        }












    @GetMapping("/findTaskOrder/{workorderCode}")
    @Operation(summary = "根据工单编号查询任务单生成二维码")
    public CommonResult<List<TaskOrderVo>>  findTaskOrder(@PathVariable String workorderCode ){
        List<TaskOrderVo> list = taskService.selectTaskOrder(workorderCode);
        if(list.size() ==0){
            throw exception(NOT_SCHEDULED_FOR_PRODUCTION);
        }
        for (TaskOrderVo proTaskOrder: list) {
            //生成工序二维码
            String processUrl=  workorderService.generateProcessUrl(proTaskOrder.getTaskCode(),proTaskOrder.getProcessCode(),proTaskOrder.getProcessName(),proTaskOrder.getBarcodeFormat());
            //生成任务二维码
            String taskUrl =  workorderService.generateTaskUrl(proTaskOrder.getTaskCode(),proTaskOrder.getItemCode(),proTaskOrder.getMeasureName(),proTaskOrder.getWorkorderCode(),proTaskOrder.getItemName(),proTaskOrder.getBarcodeFormat());
            Task proTask=  taskService.selectProTaskByTaskCode(proTaskOrder.getTaskCode());
            if( proTask.getProcessUrl() == null || proTask.getWorkorderCode() !=null && proTask.getTaskUrl() ==null || proTask.getWorkorderCode() !=null){
                proTask.setProcessUrl(processUrl);
                proTask.setTaskUrl(taskUrl);
                taskService.updateProTask(proTask);
            }
        }
        return success(taskService.selectTaskOrder(workorderCode));
    }





    @Operation(summary = "生产任务详细信息")
    @GetMapping( "/get/{id}")
    public CommonResult<Task> getTaskById(@PathVariable Long id)
    {
        return success(taskService.selectProTaskById(id));
    }





    @Operation(summary = "甘特图任务的显示")
    @GetMapping("/listGanttTaskList")
    public CommonResult<Object> getGanttTaskList(WorkOrder workorder){
        GanttTask ganttTask = new GanttTask();
        List<GanttData> ganttData = new ArrayList<GanttData>();
        List<GanttLink> ganttLinks = new ArrayList<GanttLink>();
        //查询所有的WorkOrder
        List<WorkOrder> workOrders = workorderService.selectProWorkorderList(workorder);
        //为每个workOrder生成type=project的GanttData
        //为每个proTask生产type=task的GanttData
        BigDecimal haveQuantity ;
        BigDecimal countQuantity;
        BigDecimal residueQuantity;
        BigDecimal processOrderCount ;
        Task param = new Task();
        if(CollUtil.isNotEmpty(workOrders)){
            for (WorkOrder order: workOrders) {
                RouteProduct routeProduct = routeProductService.findByItemId(order.getProductId());
                //如果工艺路线为空，则不进行下一步操作
                if(routeProduct == null) continue;
                //根据工艺id查询当前工艺之下有几道工序
                List<RouteProcess> routeProcessList = routeProcessService.findByRouteId(routeProduct.getRouteId());
                int size = routeProcessList.size();
                countQuantity = order.getQuantity();
                processOrderCount = new BigDecimal(size).multiply(countQuantity);
                BigDecimal produceProgress = order.getProduceProgress();

                BigDecimal percentage = produceProgress.divide(new BigDecimal(100));
                haveQuantity = processOrderCount.multiply(percentage);

                residueQuantity = processOrderCount.subtract(haveQuantity);
                //先添加当前的生产工单TASK
                GanttData wData = new GanttData();
                GanttLink gLink = new GanttLink();
                wData
                    .setId(order.getId().toString())
                    .setCode(order.getWorkorderCode())
                    .setItemCode(order.getProductCode())
                    .setItemName(order.getProductName())
                    .setQuantity(order.getQuantity())
                    .setQuantity(order.getQuantity())
                    .setStart_date(order.getProduceDate().substring(0, 10))
                    .setEnd_date(order.getRequestDate().substring(0, 10))
                    .setArrangedQuantity(haveQuantity)
                    .setUnArrangedQuantity(residueQuantity)
                    .setOrderDate(order.getOrderDate())
                    .setType(UserConstants.GANTT_TASK_TYPE_PROJECT)
                    .setProgress(order.getProduceProgress());
                     if(!order.getWorkorderCode().startsWith("M")){
                       wData.setParent(order.getId().toString());
                     }
                    gLink.setId(Math.toIntExact(order.getId()));
                    gLink.setSource(Math.toIntExact(order.getId()));
                    gLink.setTarget(Math.toIntExact(order.getId()));
                    gLink.setItemName(order.getProductName());
                    gLink.setType(UserConstants.GANTT_TASK_TYPE_PROJECT);
                    ganttData.add(wData);
                    ganttLinks.add(gLink);
                    //查询当前生产工单下所有的生产任务
                    param.setWorkorderId(order.getId());
                    List<Task> proTasks = taskService.selectProTaskList(param);
                    if(CollUtil.isNotEmpty(proTasks)){
                     for (Task task:proTasks) {
                             GanttData data = new GanttData();
                             GanttLink link = new GanttLink();
                             link.setId(Math.toIntExact(task.getId()));
                             link.setSource(Math.toIntExact(order.getId()));
                             link.setTarget(Math.toIntExact(task.getId()));
                             link.setItemName(task.getItemName());
                             link.setType(UserConstants.GANTT_TASK_TYPE_TASK);
                               data
                                .setId(task.getId().toString())
                                .setCode(task.getTaskCode())
                                .setItemName(task.getItemName())
                                .setItemCode(task.getItemCode())
                                .setStart_date(task.getStartTime().substring(0, 10))
                                .setEnd_date(task.getEndTime().substring(0, 10))
                                .setParent(order.getId().toString())
                                .setQuantity(task.getQuantity())
                                .setProcess(task.getProcessName())
                                .setQuantity(task.getQuantity())
                                .setProgress(task.getSchedule())
                                .setWorkstation(task.getWorkstationName())
                                .setType(UserConstants.GANTT_TASK_TYPE_TASK);
                                ganttData.add(data);
                                ganttLinks.add(link);
                        }
                 }
            }
        }
            ganttTask.setData(ganttData);
            ganttTask.setLinks(ganttLinks);
            return success(ganttTask);
    }






    @GetMapping("/findDetails")
    public CommonResult<Object>  findDetails( Long id ,String type){
        if (type.equals(MesConstantUtils.project)) {
            WorkOrder workorder = workorderService.findWorkOrderById(id);
            return  success(workorder);
        }
        if (type.equals(MesConstantUtils.task)) {
            ProTaskQueryVo task = new ProTaskQueryVo();
            task.setId(id);
            TaskListByConditionalVo taskListByConditionalVO = taskService.findTaskByCode(task);
            return  success(taskListByConditionalVO);
        }
        return  null;
    }






    @GetMapping("/findQuantity/{workOrderCode}")
    @Operation(summary = "查询订单的总数，排产数量")
    public CommonResult<Object> findQuantity(@PathVariable String workOrderCode){
         WorkOrder workorder =   workorderService.findWorkOrderCode(workOrderCode);
          //订单数量
          BigDecimal quantity = workorder.getQuantity();
          List<Task> taskList = taskService.findByOrderCode(workOrderCode);
          int totalQuantity = 0;
          //对工时进行遍历相加
          for (Task task : taskList) {
            totalQuantity += task.getQuantity().intValue();
          }
          HashMap<String, Object> map = new HashMap<>(200);
          map.put("orderQuantity",quantity);
          map.put("arrangeQuantity",totalQuantity);
          return  success(map);
    }





    @GetMapping("/findProduceQuantity/{taskCode}")
    @Operation(summary = "查询任务单总数和报工数量")
    public CommonResult<Task> findTaskQuantity(@PathVariable String taskCode){
        Task task = taskService.findTaskByTaskCode(taskCode);
        return  success(task);
    }






    @PostMapping("/save")
    @Operation(summary = "新增生产任务")
    public CommonResult<Integer> save(@RequestBody Task proTask) throws ParseException {

        List<Task> Orderlist = taskService.findTaskByOrderId(proTask.getWorkorderId());
        for(Task task:Orderlist){
            String scheduleType = task.getScheduleType();
            if("orderType".equals(scheduleType)){
                throw exception(ORDER_TYPE_SCHEDULE_TASK);
            }
        }
        WorkOrder orderDo = workorderService.selectByWorkOrderId(proTask.getWorkorderId());
        orderDo.setRemainQuantity(proTask.getRemainQuantity());
        workorderService.updateProWorkorder(orderDo);




            String orderDate = orderDo.getOrderDate();
            Date workOrderDate = new SimpleDateFormat("yyyy-MM-dd HH").parse(orderDate);
            System.out.println("============订单的时间==========" + workOrderDate);
            String startTime = proTask.getStartTime();
            Date startDate = new SimpleDateFormat("yyyy-MM-dd HH").parse(startTime);
            System.out.println("============开始的时间==========" + startDate);
            if (startDate.compareTo(workOrderDate) > 0) {
                //根据传递的工序id查询工序信息
                Process process = processService.selectProProcessByProcessId(proTask.getProcessId());
                //查询生产订单的信息
                WorkOrder order = workorderService.selectProWorkorderByWorkorderId(proTask.getWorkorderId());
                //从订单中获取订单的数量
                BigDecimal orderQuantity = order.getQuantity();
                //先进行判断排产的数量不能超过订单的数量
                if (proTask.getQuantity().compareTo(order.getQuantity()) > 0) {
                    throw exception(QUENTITY_NO_BIG_ORDER);
                }
                /*判断设备产能是否超过排产数量
                 * 1.根据传递的工作站id查询工作站设备资源表得到设备资源表中的设备编号
                 * 2.根据设备编号查询设备表得到产能
                 * 3.将设备的产能与传递的排产数量比较，排产数量大于产能抛出异常
                 * */
                List<WorkStationMachine> workstationMachineList = workstationMachineService.findWorkstationMachineByWorkstationId(proTask.getWorkstationId());
                ArrayList<Object> arrayList = new ArrayList<>();
                int capacitySum = 0;
                for(WorkStationMachine workstationMachine:workstationMachineList){
                    Machinery  machinery = machineryService.findMachineryByCode(workstationMachine.getMachineryCode());
                    String status = machinery.getStatus();
                    if("在线".equals(status)){
                        capacitySum += Integer.parseInt(machinery.getCapacity());
                    }
                }
                if (proTask.getQuantity().compareTo( BigDecimal.valueOf(capacitySum)) >0){
                    throw exception(CAPACITY_NOT_EXCEED_ORDER_QUANTITY);
                }
                //根据订单中的产品Id查询工艺产品表,需要从工艺产品表中获取工艺id，在根据工艺id查询工艺工序信息，得到当前工艺下有几道工序
                RouteProduct routeProduct = routeProductService.findByItemId(order.getProductId());
                //根据工艺id查询当前工艺之下有几道工序
                List<RouteProcess> routeProcessList = routeProcessService.findByRouteId(routeProduct.getRouteId());
                //获取前端输入的排产数量
                BigDecimal productionQuantity = proTask.getQuantity();
                //定义变量，用于计算总的排产数量
                int totalProduction = 0;
                //根据订单号，工序id查询任务表，并进行遍历，得到当前订单，当前工序下一共排产的数量
                List<Task> taskDo = taskService.findByWorkOrderCode(order.getWorkorderCode(), proTask.getProcessId());
                if (!taskDo.isEmpty()) {
                    for (Task task : taskDo) {
                        // 每次循环得到排产的数量，并将数量进行相加赋值给totalQuantity---由此可以得到当前订单，当前工序之下一共排了多少订单
                        totalProduction += task.getQuantity().intValue();
                    }
                }

                //判断当前排产总数是否小于订单总数---小于的话继续判断传递的排产数量是否小于订单数量----小于的话继续执行添加生产任务方法
                if (totalProduction < orderQuantity.intValue()) {
                    if (productionQuantity.compareTo(orderQuantity) <= 0) {
                        proTask.setWorkorderCode(order.getWorkorderCode())
                                .setWorkorderName(order.getWorkorderName())
                                .setItemId(order.getProductId())
                                .setItemCode(order.getProductCode())
                                .setItemName(order.getProductName())
                                .setSpecification(order.getProductSpc())
                                .setUnitOfMeasure(order.getUnitOfMeasure())
                                .setClientCode(order.getClientCode())
                                .setClientName(order.getClientName())
                                .setProcessId(process.getId())
                                .setProcessCode(process.getProcessCode())
                                .setProcessName(process.getProcessName())
                                .setScheduleType("processType")
                                // 自动生成任务编号和名称
                                .setTaskCode(autoCodeUtil.genSerialCode(UserConstants.TASK_CODE, null))
                                .setTaskName(new StringBuilder().append(proTask.getItemName()).append("【").append(proTask.getQuantity().toString()).append("】").append(proTask.getUnitOfMeasure()).toString())
                                .setBarcodeFormat("QR_CODE")
                                .setMachineryCode(proTask.getMachineryCode())
                                .setWorkstationType(proTask.getWorkstationType())
                                .setMachineryName(proTask.getMachineryName())

                                .setResidueQuantity(orderQuantity)

                                .setRequestDate(proTask.getEndTime());
                        //用订单的数量去减去排产总数量，，得到还可以排多少订单
                        BigDecimal remainingQuantity = orderQuantity.subtract(BigDecimal.valueOf(totalProduction));


                        //如果订单的数量与排产数量相同，，说明前端传递的排产数量刚好与订单的数量相同--此时添加之后便不能在继续添加，此时已经排完了
                        if (orderQuantity.compareTo(productionQuantity) <= 0) {
                            taskService.insertProTask(proTask);
                        } else {
                            //否则继续判断，传递的排产数量是否大于已经减去的剩余的订单数量--大于抛出异常
                            if (productionQuantity.compareTo(remainingQuantity) > 0) {
                                System.out.println("---------------------剩余4--------------------" +remainingQuantity);
                                throw exception(QUENTITY_NO_BIG_ORDER);
                            }
                            // 不大于继 //否则继续判断，传递的排产数量是否大于已经减去的剩余的订单数量--大于抛出异常
                            taskService.insertProTask(proTask);
                            orderQuantity = remainingQuantity.subtract(productionQuantity);

                            Long processId = proTask.getProcessId();
                            String workOrderCode = proTask.getWorkorderCode();
                            List<Task> taskList = taskService.findByWorkOrderCode(workOrderCode, processId);
                            for(Task task:taskList){
                                Task task1 = taskService.findTaskByTaskCode(task.getTaskCode());
                                task1.setId(task.getId());
                                task1.setResidueQuantity(orderQuantity);
                                taskService.updateProTask(task1);
                            }
                            System.out.println("---------------------剩余7--------------------" +orderQuantity);
                        }
                    } else {
                        throw exception(SCHEDULING_QUENTITY_TOO_MUCH);
                    }
                } else {
                    throw exception(SCHEDULING_QUENTITY_TOO_MUCH);
                }
                //根据订单号订单名产品号产品名以及工作站名查询任务信息
                List<Task> taskList = taskService.selectByCodeAndName(order.getWorkorderCode(), order.getWorkorderName(), order.getProductCode(), order.getProductName(), proTask.getWorkstationName());
                String machinery = "";
                for(WorkStationMachine workstationMachine:workstationMachineList){
                    Machinery  machineryDo = machineryService.findMachineryByCode(workstationMachine.getMachineryCode());
                    String machineryCode = machineryDo.getMachineryCode();
                    machinery += "#" + machineryCode;
                    if (machinery != null && machinery.startsWith("#")) {
                        machinery = machinery.substring(1);
                    }
                    System.out.println("***********设备编号*************" + machinery);
                }
                //获取到最后一条任务信息，用于添加报工
                Task taskDoList = taskList.get(taskList.size() - 1);
                //往报工表中添加数据
                Feedback feedback = new Feedback();
                feedback
                        .setWorkstationId(taskDoList.getWorkstationId())
                        .setWorkstationCode(taskDoList.getWorkstationCode())
                        .setWorkstationName(taskDoList.getWorkstationName())
                        .setWorkorderId(taskDoList.getWorkorderId())
                        .setWorkorderCode(taskDoList.getWorkorderCode())
                        .setWorkorderName(taskDoList.getWorkorderName())
                        .setTaskId(taskDoList.getId())
                        .setTaskCode(taskDoList.getTaskCode())
                        .setItemId(taskDoList.getItemId())
                        .setItemCode(taskDoList.getItemCode())
                        .setItemName(taskDoList.getItemName())
                        .setUnitOfMeasure(taskDoList.getUnitOfMeasure())
                        .setProcessId(taskDoList.getProcessId())
                        .setProcessCode(taskDoList.getProcessCode())
                        .setProcessName(taskDoList.getProcessName())
                        .setStatus("NoSTARTED")
                        .setAssignUsername("admin#demo1#demo2")
                        .setQuantity(null)
                        .setWorkOrderQuentity(order.getQuantity())
                        .setCreateTime(LocalDateTime.now())
                        .setOrderQuantity(taskDoList.getQuantity())
                        .setEquipmentCode(machinery);
                feedbackService.insertFeedback(feedback);

                //根据工单号，根据任务号在查询一次报工表
                Feedback feedbackDo = feedbackService.findByOrderCodeAndTaskCode(order.getWorkorderCode(), taskDoList.getTaskCode());
                //往报工状态表添加数据
                FeedbackStatus feedbackStatus = new FeedbackStatus();
                feedbackStatus
                        .setTaskCode(feedbackDo.getTaskCode())
                        .setStatus(feedbackDo.getStatus())
                        .setTime(feedbackDo.getCreateTime())
                     //   .setEquipmentCode(feedbackDo.getEquipmentCode())
                        .setFeedbackId(feedbackDo.getId());
                feedBackStatusService.save(feedbackStatus);

                //往设备表中添加数据
                FeedbackEquipment feedbackEquipment = new FeedbackEquipment();
                feedbackEquipment.setFeedbackCode(feedbackDo.getId())
                        .setTaskCode(feedbackDo.getTaskCode())
                        .setEquipmentCode(feedbackDo.getEquipmentCode());
                feedbackEquipmentService.save(feedbackEquipment);

                //根据报工id查询报工设备表
                FeedbackEquipment feedbackEquipmentDo = feedbackEquipmentService.findFeedbackEquipmentById(feedbackDo.getId());
                //往设备状态表添加数据
                FeedbackEquStatus feedbackEquStatus = new FeedbackEquStatus();
                feedbackEquStatus
                        .setId(feedbackEquipmentDo.getId())
                        .setTaskCode(feedbackDo.getTaskCode())
                        .setStatus(feedbackDo.getStatus())
                        .setTime(feedbackDo.getCreateTime());
                feedbackEquStatusService.save(feedbackEquStatus);
                // 将工序数量乘以订单数量----用于计算多工序订单排产的百分比
                BigDecimal multipliedQuantity = BigDecimal.valueOf(routeProcessList.size()).multiply(order.getQuantity());
                //再次根据订单号查询任务表  ----- 用于计算百分比
                List<Task> taskAll = taskService.findByOrderCode(order.getWorkorderCode());
                int quantityCount = 0;
                //对工时进行遍历相加
                for (Task task : taskAll) {
                    quantityCount += task.getQuantity().intValue();
                }
                //将排产数量全部加起来除以订单的数量乘以工序数量，，得到排产百分比
                BigDecimal total = BigDecimal.valueOf(quantityCount);
                BigDecimal percentage = total.divide(multipliedQuantity, 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100))
                        .setScale(2, RoundingMode.HALF_UP)
                        .stripTrailingZeros();
                //得到百分比之后修改订单中的百分比
                WorkOrder workorder = new WorkOrder();
                workorder.setProduceProgress(percentage);
                workorder.setId(order.getId());
                workorderService.updateProWorkorder(workorder);
                //在根据工单号查询生产任务
                List<Task> task = taskService.findTaskByWorkOrdercode(order.getWorkorderCode());
                if (task.size() > 0) {
                    //设备一开始的任务状态为未开工状态
                    WorkOrder workOrder1 = new WorkOrder();
                    workOrder1.setStatus("SCHEDUL");
                    workOrder1.setId(order.getId());
                    workorderService.updateProWorkorder(workOrder1);
                }
                int totalQuantity = 0;
                //对数量进行遍历相加
                for (Task taskDb : task) {
                    totalQuantity += taskDb.getQuantity().intValue();
                }
                //如果减去的订单数量等于0说明已经没有订单可以排产，此时修改状态为已完成
                if (multipliedQuantity.compareTo(BigDecimal.valueOf(totalQuantity)) == 0) {
                    WorkOrder workOrder = new WorkOrder();
                    workOrder.setStatus("COMPLETED");
                    workOrder.setId(order.getId());
                    workorderService.updateProWorkorder(workOrder);
                }
            } else {
                throw exception(START_TIME_EXCEED_ORDER_DATE);
            }
        return success( null );
    }






    @Operation(summary = "根据工序id以及订单号查询未排产订单")
    @GetMapping("/taskResidueQuantity")
    public CommonResult<HashMap<String, Object>> findResidueQuantity(Task task){
        HashMap<String, Object> map = new HashMap<>(200);
        //根据订单号以及工序id查询任务信息
        List<Task> taskList = taskService.findByWorkOrderCode(task.getWorkorderCode(), task.getProcessId());
        //如果任务为空，说明还没有排产
        if(taskList.isEmpty() ){
            WorkOrder workOrderCode = workorderService.findWorkOrderCode(task.getWorkorderCode());
            map.put("residueQuantity",workOrderCode.getQuantity());
        }else {
            int residueQuantity = 0;
            for(Task taskDo:taskList){
                residueQuantity = taskDo.getResidueQuantity().intValue();
            }
            map.put("residueQuantity",residueQuantity);
        }
        return  success(map);
    }










    @PostMapping("/orderAddition")
    @Operation(summary = "按照订单排产新增生产任务")
    public CommonResult<Integer> orderAddition(@RequestBody Task proTask)
    {
        WorkOrder order = workorderService.selectProWorkorderByWorkorderId(proTask.getWorkorderId());
        //获取工单的数量
        BigDecimal orderQuantity = order.getQuantity();
        List<Task> Orderlist = taskService.findTaskByOrderId(proTask.getWorkorderId());
        for(Task task:Orderlist){
            String scheduleType = task.getScheduleType();
            if("processType".equals(scheduleType)){
                throw exception(PROCESS_TYPE_SCHEDULE_TASK);
            }
        }
        if(Orderlist.size() >0){
            throw exception(ORDER_TYPE_SCHEDULE_TASK_FILL_UP);
        }
        //根据订单中的产品Id查询工艺产品表,需要从工艺产品表中获取工艺id，在根据工艺id查询工艺工序信息，得到当前工艺下有几道工序
        RouteProduct routeProduct =  routeProductService.findByItemId(order.getProductId());
        //根据工艺id查询当前工艺之下有几道工序
        List<RouteProcess> routeProcessList =  routeProcessService.findByRouteId(routeProduct.getRouteId());
        for(RouteProcess process:routeProcessList){
        List<WorkStation> list =    workstationService.findWorkStationByProcessId(process.getProcessId());
            Task task = new Task();
            task.setTaskCode(autoCodeUtil.genSerialCode(UserConstants.TASK_CODE, null))
                    .setTaskName(new StringBuilder().append(order.getProductName()).append("【").append(order.getQuantity().toString()).append("】").append(order.getUnitOfMeasure()).toString())
                    .setWorkorderId(order.getId())
                    .setWorkorderCode(order.getWorkorderCode())
                    .setWorkorderName(order.getWorkorderName())
                    .setProcessId(process.getId())
                    .setProcessCode(process.getProcessCode())
                    .setProcessName(process.getProcessName())
                    .setItemId(order.getProductId())
                    .setItemCode(order.getProductCode())
                    .setItemName(order.getProductName())
                    .setUnitOfMeasure(order.getUnitOfMeasure())
                    .setQuantity(orderQuantity)
                    .setClientCode(order.getClientCode())
                    .setClientName(order.getClientName())
                    .setEndTime(order.getRequestDate())
                    .setRequestDate(order.getRequestDate())
                    .setBarcodeFormat("QR_CODE")
                    .setScheduleType("orderType")
                    .setWorkstationType(null);
            for(WorkStation workstationList:list){
                   task
                        .setWorkstationId(workstationList.getId())
                        .setWorkstationName(workstationList.getWorkstationName())
                        .setWorkstationCode(workstationList.getWorkstationCode());

            }
            taskService.insertProTask(task);
            WorkOrder order1 = workorderService.selectProWorkorderByWorkorderId(proTask.getWorkorderId());
            BigDecimal value = new BigDecimal("100");
            order1.setProduceProgress(value);
            order1.setStatus("COMPLETED");
            workorderService.updateProWorkorder(order1);
            Feedback feedback = new Feedback();
            feedback.setWorkorderId(order.getId())
                    .setWorkorderCode(order.getWorkorderCode())
                    .setWorkorderName(order.getWorkorderName())
                    .setTaskId(task.getId())
                    .setTaskCode(task.getTaskCode())
                    .setItemId(order.getProductId())
                    .setItemCode(order.getProductCode())
                    .setItemName(order.getProductName())
                    .setOrderQuantity(orderQuantity)
                    .setUnitOfMeasure(order.getUnitOfMeasure())
                    .setWorkOrderQuentity(orderQuantity)
                     .setAssignUsername("admin")
                    .setCreateTime(LocalDateTime.now())
                    .setStatus("NoSTARTED");
            Long workstationId= null;
            for(WorkStation workstationList:list){
                feedback.setWorkstationId(workstationList.getId())
                        .setWorkstationCode(workstationList.getWorkstationCode())
                        .setWorkstationName(workstationList.getWorkstationName())
                        .setProcessId(workstationList.getProcessId())
                        .setProcessCode(workstationList.getProcessCode())
                        .setProcessName(workstationList.getProcessName());

                workstationId = workstationList.getId();
            }
            feedbackService.insertFeedback(feedback);
            List<WorkStationMachine> workstationMachineList = workstationMachineService.findWorkstationMachineByWorkstationId(workstationId);
            String machinery = "";
            for(WorkStationMachine workstationMachine:workstationMachineList){
                Machinery  machineryDo = machineryService.findMachineryByCode(workstationMachine.getMachineryCode());
                String machineryCode = machineryDo.getMachineryCode();
                machinery += "#" + machineryCode;
                if (machinery != null && machinery.startsWith("#")) {
                    machinery = machinery.substring(1);
                }
                System.out.println("***********设备编号*************" + machinery);
            }
            FeedbackStatus feedbackStatus = new FeedbackStatus();
            feedbackStatus.setTaskCode(task.getTaskCode())
                          .setStatus("NoSTARTED")
                          .setFeedbackId(feedback.getId())
                          .setTime(feedback.getCreateTime());
            feedBackStatusService.save(feedbackStatus);

            FeedbackEquipment feedbackEquipment = new FeedbackEquipment();
            feedbackEquipment.setFeedbackCode(feedback.getId())
                             .setEquipmentCode(machinery);
            feedbackEquipmentService.save(feedbackEquipment);

            FeedbackEquStatus feedbackEquStatus = new FeedbackEquStatus();
            feedbackEquStatus.setStatus("NoSTARTED")
                    .setTime(feedback.getCreateTime())
                    .setId(feedbackEquipment.getId());
            feedbackEquStatusService.save(feedbackEquStatus);

            List<Feedback> feedbackList =  feedbackService.findFeedBackByOrderId(proTask.getWorkorderId());
            for(Feedback  feedback1:feedbackList){
                feedback1.setEquipmentCode(feedbackEquipment.getEquipmentCode());
                feedbackService.updateFeedBack(feedback1);
            }
        }
        return  success(200).setMsg("添加成功");
    }










    @PutMapping("/update")
    @Operation(summary = "修改生产任务")
    public CommonResult<Integer> update(@RequestBody TaskUpdateVo proTask)
    {
        WorkOrder order = workorderService.selectProWorkorderByWorkorderId(proTask.getWorkorderId());
        //从订单获取到订单的总数
        BigDecimal orderQuantity = order.getQuantity();
        //根据工单中的产品查询工艺产品
        RouteProduct routeProduct =  routeProductService.findByItemId(order.getProductId());
        //获取到工艺id
        Long routeId = routeProduct.getRouteId();
        List<RouteProcess> routeProcessList =  routeProcessService.findByRouteId(routeId);
        List<Task> taskDo = taskService.findByWorkOrderCode(order.getWorkorderCode(), proTask.getProcessId());
        Integer totalQuantity = 0;
        //对工时进行遍历相加
        for (Task taskQuantity : taskDo) {
            BigDecimal quantity1 = taskQuantity.getQuantity();
            totalQuantity += quantity1.intValue();
        }
        if (totalQuantity.equals(orderQuantity.intValue())) {
            throw exception(ALREADY_BEEN_SCHEDULED);
        }
        BigDecimal quantity = proTask.getQuantity();
        if ( quantity.compareTo(order.getQuantity()) > 0) {
            throw exception(QUENTITY_NO_BIG_ORDER);
        }
         Task task =    taskService.findTaskByWoroOrderCodeAndProcessId(order.getWorkorderCode(),proTask.getProcessId(),proTask.getTaskCode());
            BigDecimal remainingQuantity = orderQuantity.subtract(quantity);

           System.out.println("----------------------剩余订单数量-----------------" + orderQuantity);


            task.setQuantity(proTask.getQuantity());
            //修改生产任务的任务名
            task.setTaskName(new StringBuilder().append(task.getItemName()).append("【").append(task.getQuantity().toString()).append("】").append(task.getUnitOfMeasure()).toString());
            taskService.updateProTask(task);

            //修改报工中的数量
            Feedback feedback = feedbackService.findByTaskCode(proTask.getTaskCode());
            feedback.setOrderQuantity(proTask.getQuantity());
            feedback.setId(feedback.getId());
            feedbackService.updateFeedBack(feedback);
            // 将工序数量乘以订单数量
            BigDecimal multipliedQuantity = BigDecimal.valueOf(routeProcessList.size()).multiply(order.getQuantity());

            List<Task> list = taskService.findByOrderCode(order.getWorkorderCode());
            int totalQuantity2 = 0;
            //对工时进行遍历相加
            for (Task taskList : list) {
                  totalQuantity2 += taskList.getQuantity().intValue();
            }
            BigDecimal totalQuantity1 = BigDecimal.valueOf(totalQuantity2);
            BigDecimal percentage = totalQuantity1.divide(multipliedQuantity, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(2, RoundingMode.HALF_UP)
                    .stripTrailingZeros();
            WorkOrder workorder = new WorkOrder();
            workorder.setProduceProgress(percentage);
            workorder.setId(order.getId());
            return success(workorderService.updateProWorkorder(workorder)).setMsg("修改成功");
    }








    @DeleteMapping("/batch")
    @Operation(summary = "批量删除生产任务")
    public CommonResult<Integer> batch(@RequestBody List <Long> ids)
    {
        Task task = null;
        for(Long id:ids){
             task = taskService.selectProTaskById(id);
        }
        assert task != null;
        Feedback feedbackDo = feedbackService.findByTaskCode(task.getTaskCode());
     if(feedbackDo.getStatus().equals("NoSTARTED")){
        taskService.deleteProTaskByTIds(ids);
        WorkOrder workOrder1 = workorderService.selectByWorkOrderId(task.getWorkorderId());
        //根据工单中的产品查询工艺产品
        RouteProduct routeProduct =  routeProductService.findByItemId(workOrder1.getProductId());
        //获取到工艺id
        Long routeId = routeProduct.getRouteId();
        List<RouteProcess> routeProcessList =  routeProcessService.findByRouteId(routeId);
        BigDecimal multipliedQuantity = BigDecimal.valueOf(routeProcessList.size()).multiply(workOrder1.getQuantity());
        List<Task> byOrderCode = taskService.findByOrderCode(task.getWorkorderCode());
        int totalQuantity = 0;
        //对工时进行遍历相加
        for (Task taskQuantity : byOrderCode) {
            BigDecimal quantity1 = taskQuantity.getQuantity();
            totalQuantity += quantity1.intValue();
        }
        BigDecimal totalQuantity1 = BigDecimal.valueOf(totalQuantity);
        BigDecimal percentage = totalQuantity1.divide(multipliedQuantity, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP)
                .stripTrailingZeros();
        WorkOrder order = new WorkOrder();
         order.setProduceProgress(percentage);
         order.setId(workOrder1.getId());
        if (percentage.compareTo(BigDecimal.ZERO) == 0) {
            order.setStatus("NOSCHEDUL");
        }
        BigDecimal hundredPercent = BigDecimal.valueOf(100);
        if (percentage.compareTo(hundredPercent) < 0 && percentage.compareTo(BigDecimal.ZERO) != 0) {
            order.setStatus("SCHEDUL");
        }
       workorderService.updateProWorkorder(order);
       //根据任务id查询报工表
       Feedback feedback = feedbackService.findByTaskCode(task.getTaskCode());
        if(feedback!= null) {
           //获取报工id
           FeedbackEquipment feedBackEquipment = feedbackEquipmentService.findFeedbackEquipmentById(feedback.getId());
           if(feedBackEquipment != null) {
               //删除设备状态信息
               feedbackEquStatusService.removeFeedbackEquStatusById(feedBackEquipment.getId());
               //删除设备表
               feedbackEquipmentService.removeFeedBackEquipmentById(feedBackEquipment.getId());
           }
               //删除报工表
               feedbackService.removeFeedBackById(feedback.getId());
               //删除工时表
               feedbackHoursService.removeFeedBackHoursById(feedback.getId());
         }
        }else{
            throw  exception(PROTASK_ALERY_STARTEDED);
        }
        return success(200).setMsg("删除成功");
    }







    @GetMapping("/listByConditional/export")
    public void export(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("生产任务", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), TaskListByConditionalVo.class).registerWriteHandler(styleStrategy).sheet("生产任务").doWrite(taskService.exportTaskData());
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }
}
