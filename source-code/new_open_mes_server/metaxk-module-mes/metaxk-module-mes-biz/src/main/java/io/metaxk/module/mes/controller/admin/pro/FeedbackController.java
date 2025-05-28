package io.metaxk.module.mes.controller.admin.pro;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.metaxk.module.mes.controller.admin.pro.vo.FeedbackQueryVo;
import io.metaxk.module.mes.controller.admin.pro.vo.ProfeedBackVo;
import io.metaxk.module.mes.dal.dataobject.md.WorkStation;
import io.metaxk.module.mes.dal.dataobject.pro.Feedback;
import io.metaxk.module.mes.dal.dataobject.pro.FeedbackEquipment;
import io.metaxk.module.mes.service.md.WorkStationService;
import io.metaxk.module.mes.service.pro.*;
import io.metaxk.module.mes.utils.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import java.math.BigDecimal;
import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.common.pojo.CommonResult;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.*;



/**
 * @author 万界星空
 * @time 2023/6/20 15:33
 */
@Tag(name = "管理后台 - 生产报工")
@RestController
@RequestMapping("/mes/pro/feedback")
public class FeedbackController {

    @Resource
    private FeedbackService feedbackService;

    @Resource
    private WorkStationService workstationService;

    @Resource
    private FeedbackEquipmentService feedbackEquipmentService;

    @Resource
    private FeedbackHoursService feedbackHoursService;

    @Resource
    private FeedBackStatusService feedBackStatusService;

    @Resource
    private FeedbackEquStatusService feedbackEquStatusService;





    @Operation(summary = "查询未开工的订单数量")
    @GetMapping("/unStartOrders")
    public CommonResult<HashMap<String, Object>>   findUnStartOrders(){
     List<Feedback>  list =  feedbackService.findUnStartOrders();
        int totalQuantity = 0;
        for (Feedback feedbackList : list) {
            totalQuantity += feedbackList.getOrderQuantity().intValue();
        }
        HashMap<String, Object> map = new HashMap<>(200);
        map.put("totalQuentity",totalQuantity);
        map.put("name","未开工订单");
     return  success(map);
    }







    @Operation(summary = "查询已完工的订单数量")
    @GetMapping("/completed")
    public CommonResult<HashMap<String, Object>> findCompletedOrders(){
        List<Feedback>  list =  feedbackService.findCompletedOrders();
        int totalQuantity = 0;
        for (Feedback feedbackList : list) {
            totalQuantity += feedbackList.getOrderQuantity().intValue();
        }
        HashMap<String, Object> map = new HashMap<>(200);
        map.put("totalQuentity",totalQuantity);
        map.put("name","已完工订单");
        return  success(map);
    }





    @Operation(summary = "查询已暂停的订单数量")
    @GetMapping("/paused")
    public CommonResult<HashMap<String, Object>>  findPausedOrders(){
        List<Feedback>  list =  feedbackService.findPausedOrders();
        int totalQuantity = 0;
        for (Feedback feedbackList : list) {
            totalQuantity += feedbackList.getOrderQuantity().intValue();
        }
        HashMap<String, Object> map = new HashMap<>(200);
        map.put("totalQuentity",totalQuantity);
        return  success(map);
    }






    @GetMapping("/list")
    @Operation(summary = "报工2条件分页查询")
    @PreAuthorize("@ss.hasPermission('pro:feedbacktwo:list')")
    public CommonResult<PageResult<Feedback>>  getFeedbackPage( FeedbackQueryVo page) throws JsonProcessingException {
        PageResult<Feedback> pageResult = feedbackService.getFeedbackPage(page);
        List<Feedback> list = pageResult.getList();
        for(Feedback feedbackList:list){
            String equipmentCode = feedbackList.getEquipmentCode();
            List<String> equipmentCodes = Arrays.asList(equipmentCode.split("#"));
            List<Map<String, String>> equipmentCodeList = new ArrayList<>();
            for (String code : equipmentCodes) {
                Map<String, String> equipmentCodeMap = new HashMap<>(200);
                equipmentCodeMap.put("equipmentCode", code);
                equipmentCodeList.add(equipmentCodeMap);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String equipmentCodeJson = objectMapper.writeValueAsString(equipmentCodeList);
            feedbackList.setEquipmentCode(equipmentCodeJson);
        }
        return success(pageResult);
    }




    @PostMapping("/insert")
    @Operation(summary = "新增报工2")
    @PreAuthorize("@ss.hasPermission('pro:feedbacktwo:insert')")
    public CommonResult<Integer> insertFeedBack(@RequestBody ProfeedBackVo profeedBackVo){
        return success(feedbackService.saveFeedBack(profeedBackVo)).setMsg("新增成功");
    }






    @PostMapping("/updateStatus")
    @Operation(summary = " 测试实现类编写修改状态")
    @PreAuthorize("@ss.hasPermission('pro:feedbacktwo:updateStatus')")
    public CommonResult<Integer> updateStatus(@RequestBody ProfeedBackVo profeedBackVo) throws Exception {
       if(profeedBackVo.getStatus().equals("FINISHED") && profeedBackVo.getQuantity()!=null){
           BigDecimal quantity = profeedBackVo.getQuantity();
           Feedback task = feedbackService.findByTaskCode(profeedBackVo.getTaskCode());
           BigDecimal orderQuantity = task.getOrderQuantity();
           if(orderQuantity.compareTo(BigDecimal.ZERO) == 0) {

           }
           if (quantity.compareTo(orderQuantity) == 0) {
               feedbackService.updateStatus(profeedBackVo);
           }else {
               throw exception(REPORTWORKQUENTITY_EQUAL_TASKQUENTITY);
           }
       }else {
           feedbackService.updateStatus(profeedBackVo);
       }
        return success(200);
    }







    @GetMapping("/find/{recoreId}")
    @Operation(summary = " 报工2详情")
    @PreAuthorize("@ss.hasPermission('pro:feedbacktwo:find')")
    public CommonResult<Feedback> findAllByTaskCode(@PathVariable Long recoreId) throws JsonProcessingException {
        Feedback feedback = feedbackService.findById(recoreId);
        String inputJson = feedback.getEquipmentCode();
        List<String> equipmentCodes = Arrays.asList(inputJson.split("#"));
        List<Map<String, String>> equipmentCodeList = new ArrayList<>();
        for (String code : equipmentCodes) {
            Map<String, String> equipmentCodeMap = new HashMap<>(200);
            equipmentCodeMap.put("equipmentCode", code);
            equipmentCodeList.add(equipmentCodeMap);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String equipmentCodeJson = objectMapper.writeValueAsString(equipmentCodeList);
        feedback.setEquipmentCode(equipmentCodeJson);
        return success(feedback);
    }





    @PutMapping("/updateFeedBack/{id}")
    @PreAuthorize("@ss.hasPermission('pro:feedbacktwo:updateFeedBack')")
    @Operation(summary = " 报工2修改设备")
    public CommonResult updateFeedBack(@PathVariable Long id ,@RequestBody ProfeedBackVo profeedBackVo) throws JsonProcessingException {
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
        Feedback proFeedback = new Feedback();
        proFeedback.setUpdater(profeedBackVo.getUserName());
        proFeedback.setId(id);
        proFeedback.setEquipmentCode(equipmentCodeString);
        proFeedback.setQuantity(profeedBackVo.getQuantity());
        feedbackService.updateCommenCompletion(proFeedback);
        //根据Id查询设备--用于修改
        FeedbackEquipment proFeedbackEquipment1 =   feedbackEquipmentService.findFeedbackEquipmentById(id);
        FeedbackEquipment proFeedbackEquipment = new FeedbackEquipment();
        proFeedbackEquipment.setId(proFeedbackEquipment1.getId());
        proFeedbackEquipment.setEquipmentCode(equipmentCodeString);
        return CommonResult.success(feedbackEquipmentService.updateFeedbackEquipment(proFeedbackEquipment)).setMsg("修改成功");
    }






    @DeleteMapping("/delete/{id}")
    @Operation(summary = "生产报工2删除")
    @PreAuthorize("@ss.hasPermission('pro:feedbacktwo:delete')")
    public CommonResult<Integer> removeFeedBack(@PathVariable Long id) {
        //删除报工表
        feedbackService.removeById(id);
        //删除报工状态
        feedBackStatusService.removeFeedBackStatusById(id);
        //删除报工工时
        feedbackHoursService.removeFeedBackHoursById(id);
        //查询设备id
        FeedbackEquipment feedbackEquipment = feedbackEquipmentService.selectById(id);
        //删除设备
        feedbackEquipmentService.removeFeedBackEquipmentById(id);
        //删除设备状态
       return success( feedbackEquStatusService.removeFeedbackEquStatusById(feedbackEquipment.getId())).setMsg("删除成功");
    }










    @PostMapping("/save")
    @Operation(summary = "创建生产报工记录-报工1")
    public CommonResult<Integer> createFeedback( @RequestBody Feedback feedbackDO) {
       Long workstationId = feedbackDO.getWorkstationId();
       WorkStation workstationDO = workstationService.selectWorkstationById(workstationId);
       if(StringUtils.isNotNull(workstationDO)){
           feedbackDO.setProcessId(workstationDO.getProcessId());
           feedbackDO.setProcessCode(workstationDO.getProcessCode());
           feedbackDO.setProcessName(workstationDO.getProcessName());
       }else{
           throw exception(WORKSTATION_PROTASK_NOT_EXISTS);
       }
        return success(feedbackService.insertFeedback(feedbackDO));
    }





}
