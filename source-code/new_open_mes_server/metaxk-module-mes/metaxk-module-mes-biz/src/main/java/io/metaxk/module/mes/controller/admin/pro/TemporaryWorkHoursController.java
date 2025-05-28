package io.metaxk.module.mes.controller.admin.pro;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.pro.vo.TemporaryWorkHoursExcelVo;
import io.metaxk.module.mes.controller.admin.pro.vo.TemporaryWorkHoursListResult;
import io.metaxk.module.mes.controller.admin.pro.vo.TemporaryWorkHoursVo;
import io.metaxk.module.mes.dal.dataobject.pro.TemporaryWorkHours;
import io.metaxk.module.mes.service.pro.TemporaryWorkHoursService;
import io.metaxk.module.mes.utils.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.CREATETIMEANDENDTIME_NOT_NULL;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.EXPORT_DATA_ERROR;




/**
 * @author 万界星空
 * @time 2023/6/20 15:33
 */
@Tag(name = "管理后台 - 临时工时管理")
@RestController
@RequestMapping("/mes/pro/temporary/workHours")
public class TemporaryWorkHoursController {


    @Resource
    private TemporaryWorkHoursService temporaryWorkHoursService;



    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermission('pro:temporary:workHours:list')")
    @Operation(summary = "临时工时管理条件分页")
    public CommonResult<PageResult<TemporaryWorkHoursListResult>> list(TemporaryWorkHoursVo temporaryWorkHoursVO){
        if(StringUtils.isNotBlank(temporaryWorkHoursVO.getCreateTime()) && StringUtils.isBlank(temporaryWorkHoursVO.getEndTime())
        || StringUtils.isBlank(temporaryWorkHoursVO.getCreateTime()) && StringUtils.isNotBlank(temporaryWorkHoursVO.getEndTime())){
            throw exception(CREATETIMEANDENDTIME_NOT_NULL);
        }
        //条件分页查询临时工时
        PageResult<TemporaryWorkHours> pageResult = temporaryWorkHoursService.list(temporaryWorkHoursVO);
        //将临时工时转成list集合
        List<TemporaryWorkHours> list = pageResult.getList();
        BigDecimal totalWorkhours = BigDecimal.ZERO;
        for (TemporaryWorkHours workHours : list) {
            BigDecimal workhoursDecimal = new BigDecimal(workHours.getWorkhours());
            totalWorkhours = totalWorkhours.add(workhoursDecimal);
        }
        List<TemporaryWorkHoursListResult> resultList = new ArrayList<>();

        double result = totalWorkhours.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        TemporaryWorkHoursListResult listResult = new TemporaryWorkHoursListResult();
        listResult.setList(list);
        listResult.setTotalWorkhours(result);

        resultList.add(listResult);

        PageResult<TemporaryWorkHoursListResult> resultPage = new PageResult<>();
        resultPage.setList(resultList);
        resultPage.setTotal(pageResult.getTotal());
        return success(resultPage);
    }





    @PostMapping("/save")
    @PreAuthorize("@ss.hasPermission('pro:temporary:workHours:save')")
    @Operation(summary = "新增临时工时管理")
    public CommonResult<Integer> save(@RequestBody TemporaryWorkHours temporaryWorkHours){
     return success( temporaryWorkHoursService.save(temporaryWorkHours)).setMsg("新增成功");
    }



    @DeleteMapping("/batch")
    @PreAuthorize("@ss.hasPermission('pro:temporary:workHours:delete')")
    @Operation(summary = "删除临时工时管理")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids ){
        return success( temporaryWorkHoursService.removeByIds(ids)).setMsg("删除成功");
    }




    @GetMapping("/get/{id}")
    @PreAuthorize("@ss.hasPermission('pro:temporary:workHours:get')")
    @Operation(summary = "临时工时管理详情")
    public CommonResult<TemporaryWorkHours> getById(@PathVariable Long id){
        return success( temporaryWorkHoursService.findTemporaryWorkHoursById(id));
    }






    @PutMapping("/update")
    @PreAuthorize("@ss.hasPermission('pro:temporary:workHours:update')")
    @Operation(summary = "修改临时工时管理")
    public CommonResult<Integer> update(@RequestBody TemporaryWorkHours temporaryWorkHours){
        return success( temporaryWorkHoursService.update(temporaryWorkHours)).setMsg("修改成功");
    }





    @GetMapping("/export")
    @Operation(summary = "临时工时导出")
    public void export(HttpServletResponse response, @RequestParam(required = false) List<Long> ids){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("临时工时", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            List<TemporaryWorkHoursExcelVo> dataList = temporaryWorkHoursService.listData(ids);
            BigDecimal totalWorkhours = BigDecimal.ZERO;
            for (TemporaryWorkHoursExcelVo data : dataList) {
                BigDecimal workhoursDecimal = new BigDecimal(data.getWorkhours());
                totalWorkhours = totalWorkhours.add(workhoursDecimal);
            }
            double result = totalWorkhours.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            for (TemporaryWorkHoursExcelVo data : dataList) {
                data.setTotalWorkHours(result);
            }
            EasyExcel.write(response.getOutputStream(), TemporaryWorkHoursExcelVo.class).registerWriteHandler(styleStrategy).sheet("临时工时").doWrite(dataList);
        }catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }





}
