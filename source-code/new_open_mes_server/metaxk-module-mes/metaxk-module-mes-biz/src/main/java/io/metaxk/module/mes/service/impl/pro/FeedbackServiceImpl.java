package io.metaxk.module.mes.service.impl.pro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.pro.vo.EquationTimeVo;
import io.metaxk.module.mes.controller.admin.pro.vo.FeedbackQueryVo;
import io.metaxk.module.mes.controller.admin.pro.vo.ProfeedBackVo;
import io.metaxk.module.mes.dal.dataobject.md.WorkStation;
import io.metaxk.module.mes.dal.dataobject.pro.*;
import io.metaxk.module.mes.dal.dataobject.qc.ProcessRecord;
import io.metaxk.module.mes.dal.dataobject.qc.Standard;
import io.metaxk.module.mes.dal.dataobject.qc.StandardDetail;
import io.metaxk.module.mes.dal.mysql.md.ItemMapper;
import io.metaxk.module.mes.dal.mysql.md.WorkStationMapper;
import io.metaxk.module.mes.dal.mysql.pro.*;
import io.metaxk.module.mes.dal.mysql.qc.ProcessRecordMapper;
import io.metaxk.module.mes.dal.mysql.qc.StandardDetailMapper;
import io.metaxk.module.mes.dal.mysql.qc.StandardMapper;
import io.metaxk.module.mes.service.pro.FeedbackService;
import io.metaxk.module.mes.utils.AutoCodeUtil;
import io.metaxk.module.mes.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.*;
import io.metaxk.module.mes.common.UserConstants;


/**
 * 生产报工实现类
 * @author 万界星空
 * @time 2023/6/20 15:33
 */
@Service
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {

    @Resource
    private FeedbackMapper feedbackMapper;

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private FeedBackStatusMapper feedBackStatusMapper;

    @Resource
    private FeedbackEquipmentMapper feedbackEquipmentMapper;

    @Resource
    private FeedbackEquStatusMapper feedbackEquStatusMapper;

    @Resource
    private FeedbackHoursMapper feedbackHoursMapper;

    @Resource
    private WorkStationMapper workstationMapper;


    @Resource
    private WorkOrderMapper workorderMapper;

    @Resource
    private AutoCodeUtil autoCodeUtil;

    @Resource
    private ProcessRecordMapper processRecordMapper;

    @Resource
    private StandardDetailMapper standardDetailMapper;

    @Resource
    private StandardMapper standardMapper;

    @Resource
    private ItemMapper itemMapper;





    @Override
    public void removeFeedBackById(Long id) {
        feedbackMapper.deleteById(id);
    }



    @Override
    public Feedback getFeedback(Long id) {
        return feedbackMapper.selectById(id);
    }



    @Override
    public PageResult<Feedback> getFeedbackPage(FeedbackQueryVo page) {
        return feedbackMapper.selectPage(page);
    }

    @Override
    public Integer removeById(Long id) {
        return feedbackMapper.deleteById(id);
    }


    @Override
    public Integer insertFeedback(Feedback feedbackDO) {
        return feedbackMapper.insert(feedbackDO);
    }


    /**
     * 新增报工2
     * @param profeedBackVo
     * @return Integer
     */
    @Override
    public Integer saveFeedBack(ProfeedBackVo profeedBackVo) {
        Task proTask = taskMapper.findTaskByTaskCode(profeedBackVo.getTaskCode());
        if(StringUtils.isNull(proTask)){
            throw  exception(NO_TASKCODE);
        }
        List<Map<String, String>> equipmentCode = profeedBackVo.getEquipmentCode();
        StringBuilder sb = new StringBuilder();
        for (Map<String, String> map : equipmentCode) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String value = entry.getValue();
                sb.append(value).append("#");
            }
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        String equipmentCodeString = sb.toString();
        //查询报工表
        Feedback feedBack = feedbackMapper.findFeedBackByTaskCode(profeedBackVo.getTaskCode());
        if(feedBack == null){
            Feedback proFeedback = new Feedback();
            BeanUtils.copyProperties(proTask,proFeedback);
            proFeedback.setTaskId(proTask.getId());
            proFeedback.setFeedbackTime(LocalDateTime.now());
            proFeedback.setCreateTime(LocalDateTime.now());
            proFeedback.setEquipmentCode(equipmentCodeString);
            BeanUtils.copyProperties(profeedBackVo,proFeedback);
            proFeedback.setCreator(profeedBackVo.getUserName());
            proFeedback.setStatus("STARTED");
            feedbackMapper.insert(proFeedback);
            //获取新增后id
            Feedback feedBack1 = feedbackMapper.findFeedBackByTaskCode(proTask.getTaskCode());

            //向状态表添加数据
            FeedbackStatus proFeedbackStatus = new FeedbackStatus();
            BeanUtils.copyProperties(profeedBackVo,proFeedbackStatus);
            proFeedbackStatus.setStatus(proFeedback.getStatus());
            proFeedbackStatus.setTime(feedBack1.getFeedbackTime());
            proFeedbackStatus.setFeedbackId(feedBack1.getId());
            feedBackStatusMapper.insert(proFeedbackStatus);

            //向报工设备表添加数据
            FeedbackEquipment proFeedbackEquipment = new FeedbackEquipment();
            proFeedbackEquipment.setFeedbackCode(proFeedback.getId());
            proFeedbackEquipment.setEquipmentCode(equipmentCodeString);
            feedbackEquipmentMapper.insert(proFeedbackEquipment);

            //查询报工设备信息
            FeedbackEquipment proFeedbackEquipment1 =  feedbackEquipmentMapper.selectFeedEquipIdByfeedbackCode(proFeedbackEquipment.getFeedbackCode());

            //向报工设备状态表添加数据
            FeedbackEquStatus proProFeedbackEquStatus = new FeedbackEquStatus();
            proProFeedbackEquStatus.setId(proFeedbackEquipment1.getId());
            proProFeedbackEquStatus.setStatus("STARTED");
            proProFeedbackEquStatus.setTime(feedBack1.getFeedbackTime());
           return  feedbackEquStatusMapper.insert(proProFeedbackEquStatus);
        }else {
            throw  exception(TASK_ALERY_EXIST);
        }
    }


    /**
     * 测试报工2修改
     */
    @Override
    public CommonResult updateStatus(ProfeedBackVo profeedBackVo)  throws  Exception{
        //定义初始值
        String FinishStatus = null;
        String result = null;
        //根据任务编号查询报工表
        Feedback feedBack = feedbackMapper.findFeedBackByTaskCode(profeedBackVo.getTaskCode());
        //创建设备状态的对象以及工时的对象
        FeedbackStatus proFeedbackStatus = new FeedbackStatus();
        FeedbackHours proFeedbackHours = new FeedbackHours();
        if (("STARTED".equals(profeedBackVo.getStatus()) && !"STARTED".equals(feedBack.getStatus()))) {
             feedBack.setStatus(profeedBackVo.getStatus())
                    .setUserName(profeedBackVo.getUserName())
                    .setUpdater(profeedBackVo.getUserName())
                    .setFeedbackTime(LocalDateTime.now())
                    .setUpdateTime(LocalDateTime.now());
            feedbackMapper.updateById(feedBack);
            //查询报工表
            Feedback feedBack1 = feedbackMapper.findFeedBackByTaskCode(profeedBackVo.getTaskCode());
            //修改报工状态表中未开工的状态为STARTED状态
            FeedbackStatus  feedbackStatus = feedBackStatusMapper.findFeedBackStatusByTaskCodes(feedBack1.getTaskCode());
            feedbackStatus.setStatus("STARTED");
            feedbackStatus.setTime(feedBack1.getFeedbackTime());
            feedBackStatusMapper.updateById(feedbackStatus);

            //查询设备表
            FeedbackEquipment feedbackEquipment =   feedbackEquipmentMapper .findIdByFeedCode(feedBack1.getId());
            //获取设备id
            Long id = feedbackEquipment.getId();
            String status = "NoSTARTED";
            FeedbackEquStatus feedbackEquStatus1 = feedbackEquStatusMapper.findByIdAndStatus(id,status);
            feedbackEquStatus1.setTime(feedBack1.getFeedbackTime());
            feedbackEquStatus1.setId(id);
            feedbackEquStatus1.setStatus("STARTED");
            feedbackEquStatusMapper.updateById(feedbackEquStatus1);
        }

        if (("PAUSED".equals(profeedBackVo.getStatus()) && !"PAUSED".equals(feedBack.getStatus())) ||
                ("RESUMED".equals(profeedBackVo.getStatus()) && !"RESUMED".equals(feedBack.getStatus()))) {
            feedBack.setStatus(profeedBackVo.getStatus())
                    .setUserName(profeedBackVo.getUserName())
                    .setUpdater(profeedBackVo.getUserName())
                    .setUpdateTime(LocalDateTime.now());
            if ("PAUSED".equals(profeedBackVo.getStatus())) {
                log.info("============获取输入的暂停原因========" + profeedBackVo.getStatus());
                feedBack.setPauseReason(profeedBackVo.getPauseReason());
            }
            feedbackMapper.updateById(feedBack);



            proFeedbackStatus.setTaskCode(feedBack.getTaskCode())
                    .setStatus(profeedBackVo.getStatus())
                    .setTime(LocalDateTime.now())
                    .setFeedbackId(feedBack.getId());
            feedBackStatusMapper.insert(proFeedbackStatus);

            if ("RESUMED".equals(profeedBackVo.getStatus())) {
                String pausedTime = findTimeByStatus(profeedBackVo.getTaskCode(), "PAUSED").replace("T", " ");
                String resumedTime = findTimeByStatus(profeedBackVo.getTaskCode(), "RESUMED").replace("T", " ");
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date pausedTime2 = df.parse(pausedTime);
                Date resumedTime2 = df.parse(resumedTime);
                long diffMillisTime = resumedTime2.getTime() - pausedTime2.getTime();
                long diffSeconds = diffMillisTime / 1000;
                long diffMinutes = diffSeconds / 60;
                long diffHours = diffMinutes / 60;
                result = String.format("%d天 %d小时 %d分钟", diffHours / 24, diffHours % 24, diffMinutes % 60);
                log.info("==========暂停时间=========" + result);
                feedBack.setPauseTime(result);
                feedbackMapper.updateById(feedBack);
            }
        }
        if (("FINISHED".equals(profeedBackVo.getStatus()) && !"FINISHED".equals(feedBack.getStatus()))) {

            List<Map<String, String>> equipmentCode = profeedBackVo.getEquipmentCode();
            StringBuilder sb = new StringBuilder();
            for (Map<String, String> map : equipmentCode) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String value = entry.getValue();
                    sb.append(value).append("#");
                }
            }
            if (sb.length() > 0) {
                sb.setLength(sb.length() - 1);
            }

            ///加的
            BigDecimal quantity2 = profeedBackVo.getQuantity();
            Feedback feedBackByTaskCode = feedbackMapper.findFeedBackByTaskCode(profeedBackVo.getTaskCode());
            BigDecimal orderQuantity = feedBackByTaskCode.getOrderQuantity();
            BigDecimal percentage1 = quantity2.divide(orderQuantity, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
            BigDecimal roundedPercentage1 = percentage1.setScale(2, RoundingMode.HALF_UP);



            //报工之后修改生产任务的进度----任务的数量除以订单的总数
            Task task = taskMapper.findTaskByTaskCode(profeedBackVo.getTaskCode());
            WorkOrder order =  workorderMapper.selectProWorkorderByWorkorderCode(task.getWorkorderCode());
            BigDecimal quantityOrder = order.getQuantity();
            System.out.println("**************当前订单的总数量**********" + order.getQuantity());
            BigDecimal arrangeQuantity = profeedBackVo.getQuantity();
            BigDecimal taskPercentage = arrangeQuantity.divide(quantityOrder, 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
            // 保留两位小数
            taskPercentage = taskPercentage.setScale(2, RoundingMode.HALF_UP);
            System.out.println("===========任务数量=========" + task.getQuantity());
            Long id = task.getId();
            task.setSchedule(taskPercentage);
            task.setId(id);
            //设置已报工数量
            task.setQuantityFeedback(profeedBackVo.getQuantity());
            taskMapper.updateById(task);

             // 在此处添加您想要执行的代码
                String equipmentCodeString = sb.toString();
                feedBack.setFinishedTime(LocalDateTime.now())
                        .setUserName(profeedBackVo.getUserName())
                        .setUpdater(profeedBackVo.getUserName())
                        .setStatus(profeedBackVo.getStatus())
                        .setEquipmentCode(equipmentCodeString)
                        .setQuantity(profeedBackVo.getQuantity())
                        .setReportingProgress(roundedPercentage1)
                        .setUpdateTime(LocalDateTime.now());
                feedbackMapper.updateById(feedBack);


            //报工之后修改顶的的生产进度
            Feedback feedback =  feedbackMapper.findFeedBackByTaskCode(profeedBackVo.getTaskCode());
           // String workOrderCode = feedback.getWorkorderCode();
            WorkOrder workorder = workorderMapper.selectProWorkorderByWorkorderCode(feedback.getWorkorderCode());
            log.info("**************订单的总数量========" + workorder.getQuantity());
            BigDecimal productionSchedule = workorder.getProductionSchedule();
            log.info("-------------获取到原来的订单百分比-------"+productionSchedule);
            BigDecimal quantity = profeedBackVo.getQuantity();
            BigDecimal quantity1 = workorder.getQuantity();
            //使用报工的数量除以订单的数量的到订单的进度
            BigDecimal percentage = quantity.divide(quantity1, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
            BigDecimal roundedPercentage = percentage.setScale(2, RoundingMode.HALF_UP);
            BigDecimal sum = productionSchedule.add(roundedPercentage);
            log.info("*************输出订单的百分比=========" + roundedPercentage);
            //进度百分比超过100时，将订单进度修改为100
            if (sum.compareTo(new BigDecimal(100)) > 0) {
                sum = new BigDecimal(100);
            }
            workorder.setProductionSchedule(sum);
            workorder.setId(workorder.getId());
            workorderMapper.updateById(workorder);


            //选择报工时向工序质检单添加数据
            ProcessRecord processForm = new ProcessRecord();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = dateFormat.parse(workorder.getOrderDate());
            processForm
                    .setRecordCode(autoCodeUtil.genSerialCode(UserConstants.PROCESS_FORM_CODE, null))
                    .setTaskCode(feedBack.getTaskCode())
                    .setOrderCode(feedBack.getWorkorderCode())
                    .setProcessCode(feedBack.getProcessCode())
                    .setProcessName(feedBack.getProcessName())
                    .setQuantity(feedBack.getQuantity().toString())
                    .setProductName(feedback.getItemName())
                    .setProductCode(itemMapper.findItemByName(feedback.getItemName()).getItemCode())
                    .setReportUser(feedBack.getUserName())
                    .setStatus("0")
                    .setOrderDate(parse);
            //查询工序标准明细表
            List<StandardDetail> standardDetailList = standardDetailMapper.findDetailByProcessCode(feedback.getProcessCode());
            //如果检验标准为空
            if(standardDetailList.isEmpty()){
                List<Standard> standardList = standardMapper.findStandByProcessCode(feedback.getProcessCode());
                for(Standard standard:standardList) {
                    if ("true".equals(standard.getEnableFlag())) {
                        processForm.setInspectWay(standard.getInspectMethod());
                        processForm.setVersion(standard.getVersion());
                        processForm.setInspectFlag("0");
                    }
                }
            }else {
                for (StandardDetail standardDetail : standardDetailList) {
                    String[] processArray = standardDetail.getProcessCode().split(",");
                    for (String code : processArray) {
                     System.out.println("=========================输出编号===============" + code);
                     List<Standard> standardList =   standardMapper.findStandByProcessCode(code);
                        for(Standard standard:standardList){
                            if("true".equals(standard.getEnableFlag())){
                                processForm.setInspectWay(standard.getInspectMethod());
                                processForm.setVersion(standard.getVersion());
                                processForm.setCheckNumber(standard.getQuantity());
                                System.out.println("=========================取出为true的版本号===============" + standard.getVersion());
                                System.out.println("=========================取出true的方式===============" + standard.getInspectMethod());
                         }
                     }
            if (code.equals(feedBack.getProcessCode()) && "true".equals(standardDetail.getEnableFlag())) {
                processForm.setInspectFlag("1");
                break;
            } else {
                processForm.setInspectFlag("0");
             }
            }
        }
      }
            processRecordMapper.insert(processForm);


            //修改报工设备中的设备号
            FeedbackEquipment feedbackEquipment = feedbackEquipmentMapper.selectFeedEquipIdByfeedbackCode(feedBack.getId());
            feedbackEquipment.setEquipmentCode(equipmentCodeString);
            feedbackEquipmentMapper.updateById(feedbackEquipment);

            List<FeedbackStatus> proFeedbackStatus1 = feedBackStatusMapper.findFeedBackStatusByTaskCode(profeedBackVo.getTaskCode());
                for (FeedbackStatus feedbackStatus : proFeedbackStatus1) {
                    FinishStatus = feedbackStatus.getStatus();
                }
                if (!"FINISHED".equals(FinishStatus)) {
                    proFeedbackStatus.setTaskCode(feedBack.getTaskCode())
                            .setStatus("FINISHED")
                            .setTime(LocalDateTime.now())
                            .setFeedbackId(feedBack.getId());
                    feedBackStatusMapper.insert(proFeedbackStatus);
                    //查询状态表中的时间计算工时
                    boolean containsPaused = false;
                    boolean containsResumed = false;
                    List<FeedbackStatus> proFeedbackStatus2 = feedBackStatusMapper.selectProFeedbackStatusByTaskCode(profeedBackVo.getTaskCode());
                    for (FeedbackStatus Status : proFeedbackStatus2) {
                        if (Status.getStatus().contains("PAUSED")) {
                            containsPaused = true;
                        }
                        if (Status.getStatus().contains("RESUMED")) {
                            containsResumed = true;
                        }
                    }
                    if (!containsPaused && !containsResumed) {
                        EquationTimeVo equationTimeVo = feedBackStatusMapper.matrixingTime(profeedBackVo.getTaskCode());
                        log.info("===============只有开工以及完工时间===============" + equationTimeVo.getTimeDiff());
                        result = equationTimeVo.getTimeDiff();
                    } else if (containsPaused && !containsResumed) {
                        String StartTime = findTimeByStatus(profeedBackVo.getTaskCode(), "STARTED").replace("T", " ");
                        ;
                        String PausedTime = findTimeByStatus(profeedBackVo.getTaskCode(), "PAUSED").replace("T", " ");
                        ;
                        try {
                            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date onlyPausedTime = df2.parse(PausedTime);
                            Date onlyStartTime = df2.parse(StartTime);
                            long diffMillisTime2 = onlyPausedTime.getTime() - onlyStartTime.getTime();
                            long diffSeconds2 = diffMillisTime2 / 1000;
                            long diffMinutes2 = diffSeconds2 / 60;
                            long diffHours2 = diffMinutes2 / 60;
                            int pauseDays = (int) (diffSeconds2 / 86400);
                            int pausedHours = (int) ((diffSeconds2 % 86400) / 3600);
                            int pausedMinutes = (int) ((diffSeconds2 % 3600) / 60);
                            result = String.format("%d天 %d小时 %d分钟", pauseDays, pausedHours, pausedMinutes);

                            log.info("===========暂停之后直接完工，只有暂停时间没有恢复时间==========" + result);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else if (containsPaused && containsResumed) {
                        //查询各个状态的时间，并且去除时间中的T
                        String StartTime = findTimeByStatus(profeedBackVo.getTaskCode(), "STARTED").replace("T", " ");
                        String PausedTime = findTimeByStatus(profeedBackVo.getTaskCode(), "PAUSED").replace("T", " ");
                        String ResumedTime = findTimeByStatus(profeedBackVo.getTaskCode(), "RESUMED").replace("T", " ");
                        String FinishedTime = findTimeByStatus(profeedBackVo.getTaskCode(), "FINISHED").replace("T", " ");
                        try {
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date startTime = df.parse(StartTime);
                            Date pausedTime = df.parse(PausedTime);
                            Date resumedTime = df.parse(ResumedTime);
                            Date finishedTime = df.parse(FinishedTime);
                            long diffMillis = pausedTime.getTime() - startTime.getTime();
                            long diffMillisTime = finishedTime.getTime() - resumedTime.getTime();
                            long totalSeconds = (diffMillis / 1000) + (diffMillisTime / 1000);
                            int days = (int) (totalSeconds / 86400);
                            int hours = (int) ((totalSeconds % 86400) / 3600);
                            int minutes = (int) ((totalSeconds % 3600) / 60);
                            result = String.format("%d天 %d小时 %d分钟", days, hours, minutes);
                            log.info("============既有暂停又有恢复，计算总的时间============" + result);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    //设置新的数据----工作站编号---工序---以及设备等等
                    WorkStation workstation = workstationMapper.findWorkshopCodeByWorkstationCode(feedBack.getWorkstationCode());
                    Feedback feedbackDO = feedbackMapper.selectFeedBackByFeedBackId(feedBack.getId());
                    FeedbackHours feedbackHoursDO = feedbackHoursMapper.findByFeedBackId(feedbackDO.getId());
                    if (feedbackHoursDO == null) {
                        //设置值执行添加方法
                        BeanUtils.copyProperties(feedBack, proFeedbackHours);
                        proFeedbackHours.setWorkerFinishedTime(feedbackDO.getFinishedTime())
                                .setWorkHour(result)
                                .setCreateBy(feedBack.getCreator())
                                .setUpdateBy(feedBack.getUpdater())
                                .setUserName(feedBack.getUpdater())
                                .setFeedbackTime(feedBack.getFeedbackTime())
                                .setFeedbackId(feedBack.getId())
                                .setWorkshopCode(workstation.getWorkshopCode())
                                .setWorkstationCode(workstation.getWorkstationCode())
                                .setEquipmentCode(feedbackDO.getEquipmentCode());
                        feedbackHoursMapper.insert(proFeedbackHours);
                    } else {
                        feedbackHoursDO.setWorkHour(result)
                                .setId(feedbackHoursDO.getId())
                                .setWorkerFinishedTime(feedbackDO.getFinishedTime());
                        feedbackHoursMapper.updateById(feedbackHoursDO);
                    }
                    return CommonResult.success(1).setMsg("任务已经完工");
                }
            }
           return CommonResult.success(null);
    }


    /**
     * 根据状态任务号查询时间的方法
     * @param taskCode
     * @param status
     * @return String
     */
    private String findTimeByStatus(String taskCode, String status) {
        return feedBackStatusMapper.findTimeByPausedStatus(taskCode, status);
    }




    @Override
    public Feedback findById(Long recoreId) {
        QueryWrapper<Feedback> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",recoreId);
        return feedbackMapper.selectOne(queryWrapper);
    }



    @Override
    public void updateCommenCompletion(Feedback proFeedback) {
        Feedback feedbackDO = new Feedback();
        feedbackDO.setId(proFeedback.getId());
        feedbackDO.setQuantity(proFeedback.getQuantity());
        feedbackDO.setEquipmentCode(proFeedback.getEquipmentCode());
        feedbackMapper.updateById(feedbackDO);
    }

    @Override
    public List<Feedback> findByOrderCode(String workorderCode) {
        LambdaQueryWrapperX<Feedback> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(Feedback::getWorkorderCode,workorderCode);
        return feedbackMapper.selectList(queryWrapper);
    }

    @Override
    public Feedback findByTaskCode(String taskCode) {
        LambdaQueryWrapperX<Feedback> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Feedback::getTaskCode,taskCode);
        queryWrapperX.last("LIMIT 1");
        return feedbackMapper.selectOne(queryWrapperX);
    }

    @Override
    public List<Feedback> findUnStartOrders() {
        LambdaQueryWrapperX<Feedback> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Feedback::getStatus,"NoSTARTED");
        return feedbackMapper.selectList(queryWrapperX);
    }

    @Override
    public List<Feedback> findCompletedOrders() {
        LambdaQueryWrapperX<Feedback> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Feedback::getStatus,"FINISHED");
        return feedbackMapper.selectList(queryWrapperX);
    }

    @Override
    public List<Feedback> findPausedOrders() {
        LambdaQueryWrapperX<Feedback> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Feedback::getStatus,"PAUSED");
        return feedbackMapper.selectList(queryWrapperX);
    }

    @Override
    public Feedback findByOrderCodeAndTaskCode(String workorderCode, String taskCode) {
        LambdaQueryWrapperX<Feedback> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(Feedback::getWorkorderCode,workorderCode);
        queryWrapper.eq(Feedback::getTaskCode,taskCode);
        queryWrapper.last("LIMIT 1");
        return feedbackMapper.selectOne(queryWrapper);
    }

    @Override
    public Integer updateFeedBack(Feedback feedback) {
        return feedbackMapper.updateById(feedback);
    }

    @Override
    public PageResult<Feedback>  findFeedBackByWorkOrderCode(FeedbackQueryVo workorder) {
        return feedbackMapper.selectPage(workorder);
    }

    @Override
    public  List<Feedback> findFeedBackByOrderId(Long orderId) {
        LambdaQueryWrapperX<Feedback> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Feedback::getWorkorderId,orderId);
        return feedbackMapper.selectList(queryWrapperX);
    }

    @Override
    public List<Feedback> findFeedBackList() {
        return feedbackMapper.selectList();
    }

}
