package io.metaxk.module.mes.controller.admin.plan.shift;

import io.metaxk.framework.common.exception.ErrorCode;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.plan.shift.vo.query.SelectShiftAttendanceQuery;
import io.metaxk.module.mes.controller.admin.plan.shift.vo.request.InsertShiftAttendanceReqVo;
import io.metaxk.module.mes.controller.admin.plan.shift.vo.request.UpdateShiftAttendanceReqVo;
import io.metaxk.module.mes.controller.admin.plan.shift.vo.response.SelectShiftAttendanceResVo;
import io.metaxk.module.mes.service.plan.shift.ShiftAttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.*;

/**
 * io.metaxk.module.mes.controller.admin.plan.shift
 *
 * @author 万界星空
 * @time 2023/8/3 17:34
 */

@Tag(name = "管理后台 - 考勤")
@RestController
@RequestMapping("/mes/plan/shift/shift_attendance")
public class ShiftAttendanceController {

    @Resource
    private ShiftAttendanceService shiftAttendanceService;

    @PostMapping("/save")
    @Operation(summary = "保存考勤")
    public CommonResult<Integer> save(@RequestBody @Valid InsertShiftAttendanceReqVo insertShiftAttendanceReqVo, BindingResult results) {
        //验空
        if (results.hasErrors())
            throw  exception(new ErrorCode(1000, Objects.requireNonNull(results.getFieldError()).getDefaultMessage()));
        //应到人数不可以为0
        if (insertShiftAttendanceReqVo.getAttendanceRequired() != null && insertShiftAttendanceReqVo.getAttendanceRequired() == 0)
            throw exception(SHIFT_ATTENDANCE_REQUIRED_IS_ZERO);
        //应到人数不能为负
        if (insertShiftAttendanceReqVo.getAttendanceRequired() != null && insertShiftAttendanceReqVo.getAttendanceRequired() < 0)
            throw exception(SHIFT_ATTENDANCE_REQUIRED_NOT_NEGATIVE);
        //实到人数不能为负
        if (insertShiftAttendanceReqVo.getAttendanceActual() != null && insertShiftAttendanceReqVo.getAttendanceActual() < 0)
            throw exception(SHIFT_ATTENDANCE_ACTUAL_NOT_NEGATIVE);
        //应到人数>=实到人数 都不能为null
        if ((insertShiftAttendanceReqVo.getAttendanceActual() != null && insertShiftAttendanceReqVo.getAttendanceRequired() != null)
                && (!(insertShiftAttendanceReqVo.getAttendanceRequired() >= insertShiftAttendanceReqVo.getAttendanceActual())))
            throw exception(SHIFT_ATTENDANCE_REQUIRED_GREATER_OR_EQUAL_ACTUAL);
        //检查传入日期格式是否匹配 2023-08-07
        if (!insertShiftAttendanceReqVo.getDate().matches("^([1-9]\\d{2,4})-(1[0-2]|0[1-9])-(0[1-9]|[1-2][0-9]|3[0-1])$"))
            throw exception(SHIFT_ATTENDANCE_DATE_FORMAT_NOT_MATCHED);

        Integer i = shiftAttendanceService.saveShiftAttendance(insertShiftAttendanceReqVo);
        //检查考勤编号是否已经存在 或 已经废弃
        if (i == -1)
            throw exception(SHIFT_ATTENDANCE_NUMBER_EXIST);
        //若填写了应到人数或实到人数，需要前端同时传入应到人数和实到人数
        else if (i == -2)
            throw exception(SHIFT_ATTENDANCE_UPDATED_NEED_ACTUAL_AND_REQUIRED);

        return success(i).setMsg("新增成功");
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除考勤")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids) {
        //ids列表不可为空
        if (ids.isEmpty())
            throw exception(PLAN_BATCH_NEED_ID);
        return success(shiftAttendanceService.removeShiftAttendanceByIds(ids)).setMsg("删除成功");
    }

    @PutMapping("/update")
    @Operation(summary = "修改考勤")
    public CommonResult<Integer> update(@RequestBody UpdateShiftAttendanceReqVo updateShiftAttendanceReqVo) {
        //应到人数不可以为0
        if (updateShiftAttendanceReqVo.getAttendanceRequired() != null && updateShiftAttendanceReqVo.getAttendanceRequired() == 0)
            throw exception(SHIFT_ATTENDANCE_REQUIRED_IS_ZERO);
        //应到人数不能为负
        if (updateShiftAttendanceReqVo.getAttendanceRequired() != null && updateShiftAttendanceReqVo.getAttendanceRequired() < 0)
            throw exception(SHIFT_ATTENDANCE_REQUIRED_NOT_NEGATIVE);
        //实到人数不能为负
        if (updateShiftAttendanceReqVo.getAttendanceActual() != null && updateShiftAttendanceReqVo.getAttendanceActual() < 0)
            throw exception(SHIFT_ATTENDANCE_ACTUAL_NOT_NEGATIVE);
        //应到人数>=实到人数 都不能为null
        if ((updateShiftAttendanceReqVo.getAttendanceActual() != null && updateShiftAttendanceReqVo.getAttendanceRequired() != null)
                && (!(updateShiftAttendanceReqVo.getAttendanceRequired() >= updateShiftAttendanceReqVo.getAttendanceActual())))
            throw exception(SHIFT_ATTENDANCE_REQUIRED_GREATER_OR_EQUAL_ACTUAL);
        //检查传入日期格式是否匹配 2023-08-07 修改不判空注意空指针
        if (StringUtils.isNotBlank(updateShiftAttendanceReqVo.getDate()) && !updateShiftAttendanceReqVo.getDate().matches("^([1-9]\\d{2,4})-(1[0-2]|0[1-9])-(0[1-9]|[1-2][0-9]|3[0-1])$"))
            throw exception(SHIFT_ATTENDANCE_DATE_FORMAT_NOT_MATCHED);

        Integer i = shiftAttendanceService.updateShiftAttendanceById(updateShiftAttendanceReqVo);
        //修改的考勤id是否存在
        if (i == -1)
            throw exception(SHIFT_ATTENDANCE_ID_NOT_EXIST);
        //若修改了应到人数或实到人数，需要前端同时传入应到人数和实到人数
        else if (i == -2)
            throw exception(SHIFT_ATTENDANCE_UPDATED_NEED_ACTUAL_AND_REQUIRED);
        return success(1).setMsg("修改成功");
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "根据id查看考勤")
    public CommonResult<SelectShiftAttendanceResVo> selectById(@PathVariable Long id) {
        SelectShiftAttendanceResVo selectShiftAttendanceResVo = shiftAttendanceService.selectShiftAttendanceById(id);
        //避免id不存在时的空指针
        if (selectShiftAttendanceResVo == null)
            throw exception(SHIFT_ATTENDANCE_ID_NOT_EXIST);
        return success(selectShiftAttendanceResVo);
    }

    @GetMapping("/findList")
    @Operation(summary = "查看考勤列表")
    public CommonResult<PageResult<SelectShiftAttendanceResVo>> selectList(SelectShiftAttendanceQuery selectShiftAttendanceQuery) {
        //检查传入日期格式是否匹配 2023-08-07 查询不判空注意空指针
        if (StringUtils.isNotBlank(selectShiftAttendanceQuery.getDate()) && !selectShiftAttendanceQuery.getDate().matches("^([1-9]\\d{2,4})-(1[0-2]|0[1-9])-(0[1-9]|[1-2][0-9]|3[0-1])$"))
            throw exception(SHIFT_ATTENDANCE_DATE_FORMAT_NOT_MATCHED);
        //分页查询时必须提供pageNo和pageSize (好像不用selectPage就不会报错了)
        if (selectShiftAttendanceQuery.getPageNo() == null || selectShiftAttendanceQuery.getPageSize() == null)
            throw exception(PLAN_PAGE_RESULT_NEED_PAGE_INFO);
        return success(shiftAttendanceService.selectShiftAttendanceList(selectShiftAttendanceQuery));
    }

}
