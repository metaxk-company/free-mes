package io.metaxk.module.mes.controller.admin.pro;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.pro.vo.FeedbackHoursExcelVo;
import io.metaxk.module.mes.controller.admin.pro.vo.FeedbackHoursListResult;
import io.metaxk.module.mes.controller.admin.pro.vo.FeedbackHoursVo;
import io.metaxk.module.mes.dal.dataobject.pro.FeedbackHours;
import io.metaxk.module.mes.service.pro.FeedbackHoursService;
import io.metaxk.module.mes.utils.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.EXPORT_DATA_ERROR;



/**
 * @author 万界星空
 * @time 2023/6/20 15:33
 */
@RestController
@RequestMapping("/mes/pro/hours")
public class FeedbackHoursController {


    @Resource
    public FeedbackHoursService feedbackHoursService;



    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermission('pro:prohours:list')")
    @Operation(summary = "工时列表")
    public CommonResult<PageResult<FeedbackHoursListResult>> list(FeedbackHoursVo feedbackHoursVO) throws ParseException {
        FeedbackHoursListResult feedbackHoursListResult =  new FeedbackHoursListResult();
        PageResult<FeedbackHours> paggList = feedbackHoursService.list(feedbackHoursVO);
        List<FeedbackHours> list = paggList.getList();
        //工人工时
        int totalWorkHours = 0;
        //对工时进行遍历相加
        for (FeedbackHours workHours : list) {
            if (StringUtils.isNotBlank(workHours.getWorkHour())) {
                String[] parts = workHours.getWorkHour().split(" ");
                int days = 0;
                int hours = 0;
                int minutes = 0;
                // 使用循环逐一解析每个时间段的数值
                for (String part : parts) {
                    if (part.contains("天")) {
                        days = Integer.parseInt(part.replace("天", ""));
                    } else if (part.contains("小时")) {
                        hours = Integer.parseInt(part.replace("小时", ""));
                    } else if (part.contains("分钟")) {
                        minutes = Integer.parseInt(part.replace("分钟", ""));
                    }
                }
                int workMinutes = days * 24 * 60 + hours * 60 + minutes;
                totalWorkHours += workMinutes;
            }
        }
            int days = totalWorkHours / (24 * 60);
            int hours = (totalWorkHours % (24 * 60)) / 60;
            int minutes = totalWorkHours % 60;
            String totalWorkHoursStr = days + "天 " + hours + "小时 " + minutes + "分钟";
        //设备工时
        int equipmentWorkHours = 0;
        //对工时进行遍历相加
        for (FeedbackHours workHours : list) {
            if (StringUtils.isNotBlank(workHours.getEquipmentHour())) {
                String[] parts = workHours.getEquipmentHour().split(" ");
                int equipmentDays = 0;
                int equipmentHours = 0;
                int equipmentMinutes = 0;
                // 使用循环逐一解析每个时间段的数值
                for (String part : parts) {
                    if (part.contains("天")) {
                        equipmentDays = Integer.parseInt(part.replace("天", ""));
                    } else if (part.contains("小时")) {
                        equipmentHours = Integer.parseInt(part.replace("小时", ""));
                    } else if (part.contains("分钟")) {
                        equipmentMinutes = Integer.parseInt(part.replace("分钟", ""));
                    }
                }
                int workMinutes = equipmentDays * 24 * 60 + equipmentHours * 60 + equipmentMinutes;
                equipmentWorkHours += workMinutes;
            }
            int equipmentDays = equipmentWorkHours / (24 * 60);
            int equipmentHours = (equipmentWorkHours % (24 * 60)) / 60;
            int equipmentMinutes = equipmentWorkHours % 60;
            String equipmentWorkHourStr = equipmentDays + "天 " + equipmentHours + "小时 " + equipmentMinutes + "分钟";
            feedbackHoursListResult.setEquipmentWorkhours(equipmentWorkHourStr);
        }
        //设置集合
        List<FeedbackHoursListResult> resultList = new ArrayList<>();
         feedbackHoursListResult.setList(list);
         feedbackHoursListResult.setTotalWorkhours(totalWorkHoursStr);
         //将值设置到集合中
         resultList.add(feedbackHoursListResult);
         //设置分页
         PageResult<FeedbackHoursListResult> resultPage = new PageResult<>();
         resultPage.setList(resultList);
         resultPage.setTotal(paggList.getTotal());
         return success(resultPage);
    }













    @GetMapping("/export")
    @Operation(summary = "工时导出")
    @PreAuthorize("@ss.hasPermission('pro:prohours:export')")
    public void export(HttpServletResponse response, @RequestBody(required = false) List<Long> ids){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("工时管理", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            List<FeedbackHoursExcelVo> dataList = feedbackHoursService.listData(ids);
            //工人工时
            int totalWorkHours = 0;
            //对工时进行遍历相加
            for (FeedbackHoursExcelVo workHours : dataList) {
                if(StringUtils.isNotBlank(workHours.getWorkHour())) {
                    String[] parts = workHours.getWorkHour().split(" ");
                    int days = 0;
                    int hours = 0;
                    int minutes = 0;
                    // 使用循环逐一解析每个时间段的数值
                    for (String part : parts) {
                        if (part.contains("天")) {
                            days = Integer.parseInt(part.replace("天", ""));
                        } else if (part.contains("小时")) {
                            hours = Integer.parseInt(part.replace("小时", ""));
                        } else if (part.contains("分钟")) {
                            minutes = Integer.parseInt(part.replace("分钟", ""));
                        }
                    }
                    int workMinutes = days * 24 * 60 + hours * 60 + minutes;
                    totalWorkHours += workMinutes;
                }
            }
            int days = totalWorkHours / (24 * 60);
            int hours = (totalWorkHours % (24 * 60)) / 60;
            int minutes = totalWorkHours % 60;
            String totalWorkHoursStr = days + "天 " + hours + "小时 " + minutes + "分钟";
            //设备工时
            int equipmentWorkHours = 0;
            //对工时进行遍历相加
            for (FeedbackHoursExcelVo workHours : dataList) {
                if (StringUtils.isNotBlank(workHours.getEquipmentHour())) {
                    String[] parts = workHours.getEquipmentHour().split(" ");
                    int equipmentDays = 0;
                    int equipmentHours = 0;
                    int equipmentMinutes = 0;
                    // 使用循环逐一解析每个时间段的数值
                    for (String part : parts) {
                        if (part.contains("天")) {
                            equipmentDays = Integer.parseInt(part.replace("天", ""));
                        } else if (part.contains("小时")) {
                            equipmentHours = Integer.parseInt(part.replace("小时", ""));
                        } else if (part.contains("分钟")) {
                            equipmentMinutes = Integer.parseInt(part.replace("分钟", ""));
                        }
                    }
                    int workMinutes = equipmentDays * 24 * 60 + equipmentHours * 60 + equipmentMinutes;
                    equipmentWorkHours += workMinutes;
                }
            }
            int equipmentDays = equipmentWorkHours / (24 * 60);
            int equipmentHours = (equipmentWorkHours % (24 * 60)) / 60;
            int equipmentMinutes = equipmentWorkHours % 60;
            String equipmentWorkHourStr = equipmentDays + "天 " + equipmentHours + "小时 " + equipmentMinutes + "分钟";
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            for (FeedbackHoursExcelVo data : dataList) {
                data.setTotalWorkHours(totalWorkHoursStr);
                data.setEquipmenTotalWorkhours(equipmentWorkHourStr);
            }
            EasyExcel.write(response.getOutputStream(), FeedbackHoursExcelVo.class)
                    .registerWriteHandler(styleStrategy)
                    .sheet("工时管理")
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .doWrite(dataList);
        }catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }













}
