package io.metaxk.module.mes.service.impl.pro;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.pro.vo.FeedBackEquipmentQueryVo;
import io.metaxk.module.mes.controller.admin.pro.vo.FeedBackEquipmentStatusVo;
import io.metaxk.module.mes.controller.admin.pro.vo.FeedBackEquipmentVo;
import io.metaxk.module.mes.dal.dataobject.md.WorkStation;
import io.metaxk.module.mes.dal.dataobject.pro.*;
import io.metaxk.module.mes.dal.mysql.md.WorkStationMapper;
import io.metaxk.module.mes.dal.mysql.pro.FeedbackEquStatusMapper;
import io.metaxk.module.mes.dal.mysql.pro.FeedbackEquipmentMapper;
import io.metaxk.module.mes.dal.mysql.pro.FeedbackHoursMapper;
import io.metaxk.module.mes.dal.mysql.pro.FeedbackMapper;
import io.metaxk.module.mes.service.pro.FeedbackEquipmentService;
import io.metaxk.module.mes.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;



@Service
public class FeedbackEquipmentServiceImpl implements FeedbackEquipmentService {

    @Resource
    private FeedbackMapper feedbackMapper;

    @Resource
    private FeedbackEquipmentMapper feedbackEquipmentMapper;


    @Resource
    private FeedbackEquStatusMapper feedbackEquStatusMapper;

    @Resource
    private FeedbackHoursMapper feedbackHoursMapper;


    @Resource
    private WorkStationMapper workstationMapper;




    @Override
    public FeedbackEquipment findFeedbackEquipmentById(Long id) {
        LambdaQueryWrapper<FeedbackEquipment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FeedbackEquipment::getFeedbackCode,id);
        queryWrapper.last("LIMIT 1");
        return feedbackEquipmentMapper.selectOne(queryWrapper);
    }


    @Override
    public Integer updateFeedbackEquipment(FeedbackEquipment proFeedbackEquipment) {
        FeedbackEquipment feedbackEquipmentDO = new FeedbackEquipment();
        feedbackEquipmentDO.setId(proFeedbackEquipment.getId());
        feedbackEquipmentDO.setFeedbackCode(proFeedbackEquipment.getFeedbackCode());
        feedbackEquipmentDO.setEquipmentCode(proFeedbackEquipment.getEquipmentCode());
       return feedbackEquipmentMapper.updateById(feedbackEquipmentDO);
    }

    @Override
    public Integer removeFeedBackEquipmentById(Long id) {
        return feedbackEquipmentMapper.deleteById(id);
    }


//    @Override
//    public void deleteFeedbackEquipmentByRecordIds(Long id) {
//        QueryWrapper<FeedbackEquipment> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("feedback_code",id);
//        feedbackEquipmentMapper.delete(queryWrapper);
//    }




    @Override
    public FeedbackEquipment selectById(Long id) {
        QueryWrapper<FeedbackEquipment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("feedback_code",id);
        return feedbackEquipmentMapper.selectOne(queryWrapper);
    }

    @Override
    public List<FeedBackEquipmentVo> list(FeedBackEquipmentQueryVo pageReqVO) {
        return feedbackEquipmentMapper.list(pageReqVO);
    }



   // 修改设备状态
    @Override
    public CommonResult updateFeedbackEquipmentStatus(FeedBackEquipmentStatusVo feedBackEquipmentStatusVo) throws Exception{
        String result = null;
        FeedbackEquStatus ProFeedbackEquStatusDb =    feedbackEquStatusMapper.findEquipmentStatusByfeedStatusId(feedBackEquipmentStatusVo.getId());

        //创建设备状态表
        FeedbackEquStatus ProFeedbackEquStatus1 = new FeedbackEquStatus();
        if(feedBackEquipmentStatusVo.getStatus().equals("PAUSED")){
            ProFeedbackEquStatus1.setId(ProFeedbackEquStatusDb.getId())
                                 .setTime(LocalDateTime.now())
                                 .setTaskCode(ProFeedbackEquStatusDb.getTaskCode())
                                 .setStatus(feedBackEquipmentStatusVo.getStatus())
                                 .setRemork(feedBackEquipmentStatusVo.getRemork());
            feedbackEquStatusMapper.insert(ProFeedbackEquStatus1);
        }
        if(feedBackEquipmentStatusVo.getStatus().equals("RESUMED")){
            ProFeedbackEquStatus1.setId(ProFeedbackEquStatusDb.getId())
                                 .setTime(LocalDateTime.now())
                                 .setTaskCode(ProFeedbackEquStatusDb.getTaskCode())
                                 .setStatus(feedBackEquipmentStatusVo.getStatus());
            feedbackEquStatusMapper.insert(ProFeedbackEquStatus1);
            String PausedTime = findTimeByStatus(feedBackEquipmentStatusVo.getId(), "PAUSED").replace("T", " ");
            String ResumedTime = findTimeByStatus(feedBackEquipmentStatusVo.getId(), "RESUMED").replace("T", " ");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date pausedTime2 = df.parse(PausedTime);
            Date resumedTime2 = df.parse(ResumedTime);
            long diffMillisTime = resumedTime2.getTime() - pausedTime2.getTime();
            long diffSeconds = diffMillisTime / 1000;
            long diffMinutes = diffSeconds / 60;
            long diffHours = diffMinutes / 60;
            String  PausedResult = String.format("%d天 %d小时 %d分钟", diffHours / 24, diffHours % 24, diffMinutes % 60);
            System.out.println("==========暂停时间=========" + PausedResult);
            FeedbackEquipment feedbackEquipmentDO = feedbackEquipmentMapper.selectById(feedBackEquipmentStatusVo.getId());
            feedbackEquipmentDO.setPauseTime(PausedResult);
            feedbackEquipmentMapper.updateById(feedbackEquipmentDO);
        }
        if(feedBackEquipmentStatusVo.getStatus().equals("FINISHED") && !ProFeedbackEquStatusDb.getStatus().equals("FINISHED")){
            ProFeedbackEquStatus1.setId(ProFeedbackEquStatusDb.getId())
                                 .setTime(LocalDateTime.now())
                                 .setTaskCode(ProFeedbackEquStatusDb.getTaskCode())
                                 .setStatus(feedBackEquipmentStatusVo.getStatus());
            feedbackEquStatusMapper.insert(ProFeedbackEquStatus1);
            boolean containsPaused = false;
            boolean containsResumed = false;
            List<FeedbackEquStatus> equStatus =    feedbackEquStatusMapper.selectEquipmentStatusByfeedStatusId(feedBackEquipmentStatusVo.getId());
            for(FeedbackEquStatus statusList:equStatus) {
                if (statusList.getStatus().contains("PAUSED")) {
                    containsPaused = true;
                }
                if (statusList.getStatus().contains("RESUMED")) {
                    containsResumed = true;
                }
            }
                if(!containsPaused && !containsResumed ){
                    EquipmentTimeVo equipmentTimeVo =   feedbackEquStatusMapper.findTimeByFeedEqipId(feedBackEquipmentStatusVo.getId());
                    System.out.println("==========输出设备只有开工，直接完工的时间=======" + equipmentTimeVo.getEquipmentTime());
                    result = equipmentTimeVo.getEquipmentTime();
                }else if(containsPaused && !containsResumed){
                    String StartTime = findTimeByStatus(feedBackEquipmentStatusVo.getId(), "STARTED").replace("T", " ");;
                    String PausedTime = findTimeByStatus(feedBackEquipmentStatusVo.getId(), "PAUSED").replace("T", " ");;
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
                        System.out.println("===========暂停之后直接完工，只有暂停时间没有恢复时间==========" + result);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (containsPaused && containsResumed ) {
                    String StartTime = findTimeByStatus(feedBackEquipmentStatusVo.getId(), "STARTED").replace("T", " ");;
                    String PausedTime = findTimeByStatus(feedBackEquipmentStatusVo.getId(), "PAUSED").replace("T", " ");;
                    String ResumedTime = findTimeByStatus(feedBackEquipmentStatusVo.getId(), "RESUMED").replace("T", " ");;
                    String FinishedTime = findTimeByStatus(feedBackEquipmentStatusVo.getId(), "FINISHED").replace("T", " ");;
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
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    System.out.println("===========设备既有恢复又有暂停的时间计算==============" + result);
                }
            FeedbackEquipment feedbackEquipmentDO = feedbackEquipmentMapper.selectById(feedBackEquipmentStatusVo.getId());
            Long feedbackCode = feedbackEquipmentDO.getFeedbackCode();
            FeedbackHours feedbackHoursDO =    feedbackHoursMapper.selectFeedbackHours(feedbackCode);
            String FinishedTime = findTimeByStatus(feedBackEquipmentStatusVo.getId(), "FINISHED");
            if(StringUtils.isNotNull(feedbackHoursDO)) {
                feedbackHoursDO.setEquipmentHour(result);
                Long id = feedbackHoursDO.getId();
                feedbackHoursDO.setId(id)
                               .setEquipmentFinishedTime(LocalDateTime.parse(FinishedTime));
                feedbackHoursMapper.updateById(feedbackHoursDO);
            }else {
                Feedback feedback =     feedbackMapper.selectFeedBackByFeedBackId(feedbackEquipmentDO.getFeedbackCode());
                WorkStation workstation = workstationMapper.findWorkshopCodeByWorkstationCode(feedback.getWorkstationCode());

                FeedbackHours feedbackHoursDO1 = new FeedbackHours();
                BeanUtils.copyProperties(feedback,feedbackHoursDO1);
                feedbackHoursDO1
                                .setWorkshopCode(workstation.getWorkshopCode())
                                .setWorkstationCode(workstation.getWorkstationCode())
                                .setEquipmentHour(result)
                                .setWorkHour(null)
                                .setEquipmentFinishedTime(LocalDateTime.parse(FinishedTime))
                                .setFeedbackId(feedbackEquipmentDO.getFeedbackCode());
                feedbackHoursMapper.insert(feedbackHoursDO1);
            }
        }
       return  CommonResult.success(null);
    }



    //根据状态任务查询时间的方法
    private String findTimeByStatus(Long taskCode, String status) {
        return feedbackEquStatusMapper.findTimeByPausedStatus(taskCode, status);
    }






    //查询设备编号
    @Override
    public FeedbackEquipment findEquipmentCodeById(Long id) {
        LambdaQueryWrapperX<FeedbackEquipment> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(FeedbackEquipment::getId,id);
        return feedbackEquipmentMapper.selectOne(queryWrapperX);
    }



    @Override
    public Integer save(FeedbackEquipment feedbackEquipment) {
        return feedbackEquipmentMapper.insert(feedbackEquipment);
    }



}
