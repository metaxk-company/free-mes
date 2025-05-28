package io.metaxk.module.mes.controller.admin.pro;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.pro.vo.FeedBackEquipmentQueryVo;
import io.metaxk.module.mes.controller.admin.pro.vo.FeedBackEquipmentStatusVo;
import io.metaxk.module.mes.controller.admin.pro.vo.FeedBackEquipmentVo;
import io.metaxk.module.mes.dal.dataobject.pro.FeedbackEquipment;
import io.metaxk.module.mes.service.pro.FeedbackEquipmentService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.*;
import static io.metaxk.framework.common.pojo.CommonResult.success;


/**
 * @author 万界星空
 * @time 2023/6/20 15:33
 */
@RestController
@RequestMapping("/mes/pro/fed/equipment")
public class FeedbackEquipmentController {


    @Resource
    private FeedbackEquipmentService feedbackEquipmentService;



    @GetMapping("/list")
    public CommonResult<PageResult<FeedBackEquipmentVo>> list(FeedBackEquipmentQueryVo pageReqVO){
        List<FeedBackEquipmentVo> list =  feedbackEquipmentService.list(pageReqVO);
        PageResult<FeedBackEquipmentVo> pageResult = new PageResult<>();
        pageResult.setList(list);
        pageResult.setTotal((long) list.size());
       return  success(pageResult);
    }




    @PostMapping("/updateStatus")
    public CommonResult updateStatus(@RequestBody FeedBackEquipmentStatusVo feedBackEquipmentStatusVo) throws  Exception{
        return success( feedbackEquipmentService.updateFeedbackEquipmentStatus(feedBackEquipmentStatusVo));
    }






    @GetMapping("/get/{id}")
    public CommonResult<FeedbackEquipment> findEquipmentCodeById(@PathVariable Long id) throws JsonProcessingException {
        FeedbackEquipment feedbackEquipment = feedbackEquipmentService.findEquipmentCodeById(id);
        String inputJson = feedbackEquipment.getEquipmentCode();
        List<String> equipmentCodes = Arrays.asList(inputJson.split("#"));
        List<Map<String, String>> equipmentCodeList = new ArrayList<>();
        for (String code : equipmentCodes) {
            Map<String, String> equipmentCodeMap = new HashMap<>(200);
            equipmentCodeMap.put("equipmentCode", code);
            equipmentCodeList.add(equipmentCodeMap);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String equipmentCodeJson = objectMapper.writeValueAsString(equipmentCodeList);
        feedbackEquipment.setEquipmentCode(equipmentCodeJson);
        return  success(feedbackEquipment);
    }













}
