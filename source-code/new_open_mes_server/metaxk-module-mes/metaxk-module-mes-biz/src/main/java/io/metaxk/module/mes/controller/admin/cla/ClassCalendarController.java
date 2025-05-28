package io.metaxk.module.mes.controller.admin.cla;

import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.cla.vo.PlanMemberVo;
import io.metaxk.module.mes.controller.admin.cla.vo.PlanTeamQueryVo;
import io.metaxk.module.mes.controller.admin.cla.vo.ScheduleMemberVo;
import io.metaxk.module.mes.dal.dataobject.cla.ClassPlan;
import io.metaxk.module.mes.dal.dataobject.pro.Feedback;
import io.metaxk.module.mes.dal.dataobject.pro.Task;
import io.metaxk.module.mes.service.cla.ClassPlanService;
import io.metaxk.module.mes.service.pro.FeedbackService;
import io.metaxk.module.mes.service.pro.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import static io.metaxk.framework.common.pojo.CommonResult.success;




/**
 * @author 万界星空
 * @time 2023/6/28 16:08
 */
@Tag(name = "管理后台 - 班组人员")
@RestController
@RequestMapping("/mes/cla/calendar")
public class ClassCalendarController {


    @Resource
    private TaskService taskService;

    @Resource
    private FeedbackService feedbackService;

    @Resource
    private ClassPlanService classPlanService;



    @GetMapping("/findPlanTeam")
    @Operation(summary = "日历中对应日期上班的班组")
    public CommonResult<PageResult<ClassPlan>>  findPlanTeam(PlanTeamQueryVo planTeamPageVO){
         PageResult<ClassPlan> pageResult = classPlanService.findPlanByTime(planTeamPageVO);
         return  success(pageResult);
    }




    @GetMapping("/findPlanMember")
    @Operation(summary = "排班日历中对应日期上班的人员")
    public CommonResult<List<ScheduleMemberVo>>  findPlanMember(PlanMemberVo planMemberVO){
       List<ScheduleMemberVo> list = classPlanService.findMemberByCodeAndWay(planMemberVO.getTeamCode(),planMemberVO.getShiftWay());
       return  success(list);
    }






    /**
     *  排班日历报工数量
     */
    @GetMapping("/feedbackQuantityList")
    @Operation(summary = "报工柱状图接口")
    public CommonResult<HashMap<String,Object>> findScheduleQuantityList(@RequestParam(value = "page", defaultValue = "1") int page) {
        int pageSize = 7;
        List<Feedback> feedbackList = feedbackService.findFeedBackList();
        TreeMap<String, BigDecimal> quantityMap = new TreeMap<>();
        for (Feedback feedback : feedbackList) {
            LocalDateTime finishedTime = feedback.getFinishedTime();
            if (finishedTime != null) {
                // 创建日期时间格式化器
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String finishedDate = finishedTime.format(formatter);
                BigDecimal quantity = feedback.getQuantity();
                if (quantityMap.containsKey(finishedDate)) {
                    BigDecimal totalQuantity = quantityMap.get(finishedDate);
                    totalQuantity = totalQuantity.add(quantity);
                    quantityMap.put(finishedDate, totalQuantity);
                } else {
                    quantityMap.put(finishedDate, quantity);
                }
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        List<BigDecimal> quantities = new ArrayList<>();
        List<String> times = new ArrayList<>();
        int totalCount = quantityMap.size();
        int startIndex = (page - 1) * pageSize;
        if (startIndex >= totalCount) {
            map.put("quantity", quantities);
            map.put("time", times);
            map.put("total", totalCount);
            return success(map);
        }
        int entryCount = 0;
        int currentEntry = 0;
        for (Map.Entry<String, BigDecimal> entry : quantityMap.entrySet()) {
            if (currentEntry >= startIndex) {
                quantities.add(entry.getValue());
                times.add(entry.getKey());
                entryCount++;
            }
            currentEntry++;
            if (entryCount >= pageSize) {
                break;
            }
        }
        map.put("quantity", quantities);
        map.put("time", times);
        map.put("total", totalCount);
        return success(map);
    }








    @GetMapping("/scheduleQuantityList")
    @Operation(summary = "排产柱状图接口")
    public CommonResult<HashMap<String, Object>> feedbackQuantityList(@RequestParam(value = "page", defaultValue = "1") int page) {
        int pageSize = 7;
        List<Task> taskList = taskService.findTaskList();
        TreeMap<String, BigDecimal> quantityMap = new TreeMap<>();
        for (Task task : taskList) {
            String startTime = task.getStartTime().substring(0, 10);
            BigDecimal quantity = task.getQuantity();
            if (quantityMap.containsKey(startTime)) {
                BigDecimal totalQuantity = quantityMap.get(startTime);
                totalQuantity = totalQuantity.add(quantity);
                quantityMap.put(startTime, totalQuantity);
            } else {
                quantityMap.put(startTime, quantity);
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        List<BigDecimal> quantities = new ArrayList<>();
        List<String> times = new ArrayList<>();
        int totalCount = quantityMap.size();
        int startIndex = (page - 1) * pageSize;
        if (startIndex >= totalCount) {
            map.put("quantity", quantities);
            map.put("time", times);
            map.put("total", totalCount);
            return success(map);
        }
        int entryCount = 0;
        int currentEntry = 0;
        for (Map.Entry<String, BigDecimal> entry : quantityMap.entrySet()) {
            if (currentEntry >= startIndex) {
                quantities.add(entry.getValue());
                times.add(entry.getKey());
                entryCount++;
            }
            currentEntry++;
            if (entryCount >= pageSize) {
                break;
            }
        }
        map.put("quantity", quantities);
        map.put("time", times);
        map.put("total", totalCount);
        return success(map);
    }








    @GetMapping("/calendarList")
    @Operation(summary = "排班日历列表返回日期对应上班班组数量")
    public CommonResult<HashMap<String, Object>> calendarList() {
        List<ClassPlan> classPlanList = classPlanService.findClassPlanList();
        HashMap<String, Object> map = new HashMap<>();
        Map<String, Set<String>> dateMap = new HashMap<>();
        for (ClassPlan classPlan : classPlanList) {
            String teamCode = classPlan.getTeamCode();
            Date startDate = classPlan.getStartDate();
            Date endDate = classPlan.getEndDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            while (!calendar.getTime().after(endDate)) {
                String date = dateToString(calendar.getTime());
                Set<String> teamCodes = dateMap.getOrDefault(date, new HashSet<>());
                teamCodes.add(teamCode);
                dateMap.put(date, teamCodes);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        List<String> sortedDates = new ArrayList<>(dateMap.keySet());
        Collections.sort(sortedDates);
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (String date : sortedDates) {
            Set<String> teamCodes = dateMap.get(date);
            Map<String, Object> dateInfo = new HashMap<>();
            dateInfo.put("date", date);
            dateInfo.put("teamCount", teamCodes.size());
            resultList.add(dateInfo);
        }
        map.put("result", resultList);
        return success(map);
    }



    private String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }








}
