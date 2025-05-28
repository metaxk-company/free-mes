package io.metaxk.module.mes.controller.admin.cla;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.cla.vo.*;
import io.metaxk.module.mes.dal.dataobject.cla.ClassMember;
import io.metaxk.module.mes.dal.dataobject.cla.ClassPlan;
import io.metaxk.module.mes.dal.dataobject.cla.ClassPlanMember;
import io.metaxk.module.mes.dal.dataobject.cla.PeopleInfo;
import io.metaxk.module.mes.dal.dataobject.pro.Feedback;
import io.metaxk.module.mes.dal.dataobject.pro.Task;
import io.metaxk.module.mes.service.cla.ClassMemberService;
import io.metaxk.module.mes.service.cla.ClassPlanMemberService;
import io.metaxk.module.mes.service.cla.ClassPlanService;
import io.metaxk.module.mes.service.pro.FeedbackService;
import io.metaxk.module.mes.service.pro.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.*;




/**
 * @author 万界星空
 * @time 2023/6/26 13:33
 */
@Tag(name = "管理后台 - 排班计划")
@RestController
@RequestMapping("/mes/cal/plan")
public class ClassPlanController {


    @Resource
    private ClassPlanService calPlanService;

    @Resource
    private ClassPlanMemberService  classPlanMemberService;


    @Resource
    private ClassMemberService  classMemberService;


    @Resource
    private TaskService taskService;

    @Resource
    private FeedbackService feedbackService;



//    @GetMapping("/findTaskQuantity/{taskCode}")
//    public  CommonResult<Map<String,Object>>  findTaskQuantity(@PathVariable String taskCode){
//        Task task = taskService.findTaskByTaskCode(taskCode);
//        HashMap<String, Object> map = new HashMap<>(200);
//        map.put("countQuantity",task.getQuantity());
//        return success(map);
//    }


    @GetMapping("/findTaskList/{teamCode}")
    @Operation(summary = "传入班组编号查询班组中所有任务信息")
    public CommonResult<List<Task>> findTaskList (@PathVariable String teamCode){
        List<Task> taskList = taskService.findTaskByTeamCode(teamCode);
        return success(taskList);
    }



    @Operation(summary = "排班计划分配人员任务数量")
    @PutMapping("/updateClassPlanMember")
    public  CommonResult updateClassPlanMember(@RequestBody List<ClassPlanMember> classPlanMemberList){
        for(ClassPlanMember planMember:classPlanMemberList){
            if(planMember.getTaskCode() == null){
                return CommonResult.error(500,"当前班组无任务");
            }
            Task task = taskService.findTaskByTaskCode(planMember.getTaskCode());

            Feedback feedback = feedbackService.findByTaskCode(planMember.getTaskCode());
            if(feedback == null) continue;
            String assignUsername = feedback.getAssignUsername();
            if (!assignUsername.contains(planMember.getPlanPeopleName())) {
                if (!assignUsername.isEmpty()) {
                    assignUsername += "#";
                }
                assignUsername += planMember.getPlanPeopleName();
                feedback.setAssignUsername(assignUsername);
                feedbackService.updateFeedBack(feedback);
            }

            //获取原有的任务数量
            BigDecimal quantity = task.getQuantity();
            // 计算传递过来的同一任务编号下的总数量
            BigDecimal totalQuantity = BigDecimal.ZERO;
            for (ClassPlanMember member : classPlanMemberList) {

                if(member.getTaskCode()!=null) {
                    if (member.getTaskCode().equals(planMember.getTaskCode())) {
                        totalQuantity = totalQuantity.add(member.getPeopleQuantity());
                    }
                }
            }
            // 比较传递的数量和数据库查询到的数量
            if (totalQuantity.compareTo(quantity) > 0) {
                return  CommonResult.error(500,"任务名称为---" + task.getTaskName() + "---的分配任务数量超过任务数量");
            }
            if (totalQuantity.compareTo(quantity) < 0) {
                return  CommonResult.error(500,"任务名称为---" + task.getTaskName() + "---的分配任务数量低于任务数量");
            }
            ClassPlanMember member =   classPlanMemberService.findPlanMemberByPeopleId(planMember.getPeopleId());
            member.setPeopleQuantity(planMember.getPeopleQuantity());
            member.setId(member.getId());
            member.setTaskCode(planMember.getTaskCode());
            classPlanMemberService.updateClassPlanMember(member);
        }
        return  success(null).setMsg("分配成功");
    }







    @Operation(summary = "删除排班计划中的人员")
    @DeleteMapping("/removePlanMember/{peopleId}")
    public CommonResult<Integer> batch(@PathVariable Long peopleId){
        ClassMember classMember = classMemberService.findMemberById(peopleId);
        classMember.setJoinProgram("N");
        classMemberService.updateClassMember(classMember);
        ClassPlanMember planMember = classPlanMemberService.findPlanMemberByPeopleId(peopleId);

        return success(classPlanMemberService.removeClassPlanMemberById(planMember.getId())).setMsg("移除成功");
    }








    @PostMapping("/save")
    @Operation(summary = "新增排班计划信息")
    public CommonResult<Integer> save(@RequestBody ClaPlanSaveVo classPlanSaveVO){
        if( calPlanService.findCalPlanByCondition(classPlanSaveVO.getTeamType(),classPlanSaveVO.getTeamCode(),classPlanSaveVO.getTeamName()) != null){
            throw exception(PERSON_JOIN_TEAM);
        }
        List<PeopleInfo> peopleInfoList = classPlanSaveVO.getSelectedPersonnel();
        ClassPlan classPlan = new ClassPlan();
        BeanUtils.copyProperties(classPlanSaveVO,classPlan);
        for(PeopleInfo peopleInfo:peopleInfoList){

         ClassMember classMember =   classMemberService.findMemberByPeopleIdAndName(peopleInfo.getPeopleId(),peopleInfo.getTeamPeople());
         classMember.setJoinProgram("Y");
         classMemberService.updateClassMember(classMember);
            ClassPlanMember classPlanMember = new ClassPlanMember();
            classPlanMember.setPlanCode(classPlanSaveVO.getPlanCode())
                           .setPlanName(classPlanSaveVO.getPlanName())
                           .setTeamType(classPlanSaveVO.getTeamType())
                           .setTeamCode(classPlanSaveVO.getTeamCode())
                           .setTeamName(classPlanSaveVO.getTeamName())
                           .setPeopleId(peopleInfo.getPeopleId())
                           .setPlanPeopleName(peopleInfo.getTeamPeople());
            classPlanMemberService.saveClassPlanMember(classPlanMember);
        }
        return success( calPlanService.saveCalPlan(classPlan)).setMsg("新增成功");
    }





    @Operation(summary = "修改排班计划信息")
    @PutMapping("/update")
    public CommonResult<Integer> update(@RequestBody ClaPlanSaveVo classPlanSaveVO){
        //查询班组计划信息
        ClassPlan classPlanInfo =  calPlanService.findPlanByCodeAndTypeAndName(classPlanSaveVO.getPlanCode(),classPlanSaveVO.getTeamCode(),classPlanSaveVO.getTeamType(),classPlanSaveVO.getTeamName());
        if(classPlanInfo == null){
            //查询班组计划--获取原有的班组类型及名称
            ClassPlan clasPlan = calPlanService.findCalPlanByCode(classPlanSaveVO.getPlanCode());
            List<ClassPlanMember> classPlanMemberList =  classPlanMemberService.findByTypeAndCodeAndName(clasPlan.getTeamType(),clasPlan.getTeamCode(),clasPlan.getTeamName());
            for(ClassPlanMember  classPlanMember:classPlanMemberList){
                Long peopleId = classPlanMember.getPeopleId();
                ClassMember classMember =  classMemberService.findMemberById(peopleId);
                classMember.setId(classMember.getId());
                classMember.setJoinProgram("N");
                classMemberService.updateClassMember(classMember);
                Long memberId = classPlanMember.getId();
                classPlanMemberService.removeClassPlanMemberById(memberId);
            }
        }


        //获取到已选择的人员id以及人员信息
        List<PeopleInfo> peopleInfoList = classPlanSaveVO.getSelectedPersonnel();
        ClassPlan classPlan = new ClassPlan();
        BeanUtils.copyProperties(classPlanSaveVO,classPlan);

        for(PeopleInfo peopleInfo:peopleInfoList){
            ClassPlanMember planMember =  classPlanMemberService.findByPeopleIdAndName( classPlanSaveVO.getPlanCode(),peopleInfo.getPeopleId(),peopleInfo.getTeamPeople());
            if(planMember == null) {
                ClassPlanMember classPlanMember = new ClassPlanMember();
                classPlanMember.setPlanCode(classPlanSaveVO.getPlanCode())
                        .setPlanName(classPlanSaveVO.getPlanName())
                        .setPeopleId(peopleInfo.getPeopleId())
                        .setTeamType(classPlanSaveVO.getTeamType())
                        .setTeamCode(classPlanSaveVO.getTeamCode())
                        .setTeamName(classPlanSaveVO.getTeamName())
                        .setPeopleId(peopleInfo.getPeopleId())
                        .setPlanPeopleName(peopleInfo.getTeamPeople());
                classPlanMemberService.saveClassPlanMember(classPlanMember);
            }
            //执行添加之后将添加到计划中的人的标识改为Y说明此人添加到计划中了
            ClassMember classMember = classMemberService.findMemberById(peopleInfo.getPeopleId());
            classMember.setId(classMember.getId());
            classMember.setJoinProgram("Y");
            classMemberService.updateClassMember(classMember);

            //判断同一班组去除其他人的情况
            List<ClassPlanMember> list = classPlanMemberService.findByTypeAndCodeAndName(classPlanSaveVO.getTeamType(), classPlanSaveVO.getTeamCode(), classPlanSaveVO.getTeamName());
            for (ClassPlanMember member : list) {
                boolean foundMatch = false;
                for (PeopleInfo peopleInfo1 : peopleInfoList) {
                    if (member.getPlanPeopleName().equals(peopleInfo1.getTeamPeople())) {
                        foundMatch = true;
                        break;
                    }
                }
                if (!foundMatch) {
                    ClassMember classPeople = classMemberService.findMemberById(member.getPeopleId());
                   // classPeople.setId(classMember.getId());
                    classPeople.setId(classPeople.getId());
                    classPeople.setJoinProgram("N");
                    classMemberService.updateClassMember(classPeople);
                    classPlanMemberService.removeClassPlanMemberById(member.getId());
                }
            }
        }
        //判断传递为为空数组的情况
        if (peopleInfoList.isEmpty()) {
            ClassPlan plan = calPlanService.findCalPlanById(classPlanSaveVO.getId());
            classPlanMemberService.removeByPlanCode(plan.getPlanCode());
            List<ClassMember> memberList = classMemberService.findMemberByCodes(classPlan.getTeamCode());
            for(ClassMember member:memberList){
               ClassMember classMember = classMemberService.findMemberById(member.getId());
                classMember.setId(classMember.getId());
                classMember.setJoinProgram("N");
                classMemberService.updateClassMember(classMember);
            }
        }
        return success( calPlanService.updateCalPlan(classPlan)).setMsg("修改成功");
    }







    @Operation(summary = "排班计划详情查看排班人员")
    @GetMapping("/findClassPlanMember")
    public  CommonResult<HashMap<String,Object>> findClassPlanMember(ClaPlanMemberQueryVo classPlanMemberPageReqVO){
        HashMap<String, Object> map = new HashMap<>(200);
        PageResult<ClassPlanMember> pageResult =  classPlanMemberService.classPlanMemberPage(classPlanMemberPageReqVO);
        List<ClassPlanMember> classPlanMemberList = pageResult.getList();
        if(classPlanMemberList.isEmpty()){
            ClassPlan classPlan = calPlanService.findCalPlanByCode(classPlanMemberPageReqVO.getPlanCode());
            List<Task> taskList = taskService.findTaskByTeamCode(classPlan.getTeamCode());
            int countQuantity = 0;
            for (Task proTask : taskList) {
                countQuantity += proTask.getQuantity().intValue();
            }
            map.put("countQuantity",countQuantity);
            map.put("pageResult", pageResult);
        }else {
            for (ClassPlanMember classPlanMember : classPlanMemberList) {
                ArrayList<Map<String,String>> arrayList = new ArrayList<>();
                int countQuantity = 0;
                List<Task> taskList = taskService.findTaskByTeamCode(classPlanMember.getTeamCode());
                for (Task proTask : taskList) {
                    countQuantity += proTask.getQuantity().intValue();
                    //将任务编号加入对象中
                    Map<String, String> taskMap = new HashMap<>();
                    taskMap.put("label",proTask.getTaskName());
                    taskMap.put("value",proTask.getTaskCode());
                    taskMap.put("quantity",proTask.getQuantity().toString());
                    arrayList.add(taskMap);
                }
                map.put("countQuantity", countQuantity);
                map.put("pageResult", pageResult);
                map.put("value",arrayList);
            }
        }
        return success(map);
    }







    @DeleteMapping("/batch")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids){
        for(Long id:ids){
         ClassPlan classPlan =   calPlanService.findCalPlanById(id);
            List<ClassPlanMember> classPlanMemberList = classPlanMemberService.findByTypeAndCodeAndName(classPlan.getTeamType(), classPlan.getTeamCode(), classPlan.getTeamName());
            for(ClassPlanMember classPlanMember:classPlanMemberList){
                ClassMember member = classMemberService.findMemberById(classPlanMember.getPeopleId());
                member.setId(member.getId());
                member.setJoinProgram("N");
                classMemberService.updateClassMember(member);
            }
            //根据排班计划编号删除排班计划人员
            classPlanMemberService.removeByPlanCode(classPlan.getPlanCode());
        }
        //删除排班计划
        return success( calPlanService.removeCalPlanByIds(ids)).setMsg("删除成功");
    }









    @Operation(summary = "排班计划列表")
    @GetMapping("/list")
    public CommonResult<PageResult<ClassPlan>> list(ClaPlanQueryVo calPlanPageReqVO){
      PageResult<ClassPlan> pageResult = calPlanService.getCalPlanPage(calPlanPageReqVO);
      return  success(pageResult);
    }




    @Operation(summary = "排班计划详情")
    @GetMapping("/get/{id}")
    public CommonResult<ClassPlan> getCalPlan(@PathVariable Long id){
        ClassPlan classPlan = calPlanService.getCalPlanById(id);
        //未加人员
        List<ClassMember> classMemberList = classMemberService.findArrangMemberByCode(classPlan.getTeamCode());
        //已加人员
        List<ClassMember> memberList = classMemberService.findNotArrangMemberByCode(classPlan.getTeamCode());
        List<ClaPlanMemberVo> unSelectedPersonnel = new ArrayList<>();
        List<ClaPlanMemberVo> selectedPersonnel = new ArrayList<>();
        for (ClassMember classMember:classMemberList){
            ClaPlanMemberVo memberVO = new ClaPlanMemberVo();
            memberVO.setId(classMember.getId());
            memberVO.setUserName(classMember.getUserName());
            unSelectedPersonnel.add(memberVO);
        }
        for (ClassMember classMember:memberList){
            ClaPlanMemberVo memberVO = new ClaPlanMemberVo();
            memberVO.setId(classMember.getId());
            memberVO.setUserName(classMember.getUserName());
            selectedPersonnel.add(memberVO);
        }
        classPlan.setSelectedPersonnel(selectedPersonnel);
        classPlan.setPersonsNotSelected(unSelectedPersonnel);
        return success(classPlan);
    }





    @GetMapping("/export")
    @Operation(summary = "排班计划导出")
    public void export(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("排班计划", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), ClaPlanExcelVo.class).registerWriteHandler(styleStrategy).sheet("排班计划").doWrite(calPlanService.exportData());
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }



      /**
      *  排班日历中 选择仓库查询排班详情
      */
      @GetMapping("/findByShiftType")
      public  CommonResult<PageResult<ClassPlan>> findByShiftType (ClaPlanQueryVo calPlanPageReqVO){
         PageResult<ClassPlan> pageResult =  calPlanService.findCalPlanByShiftType(calPlanPageReqVO);
         return  success(pageResult);
      }


}
