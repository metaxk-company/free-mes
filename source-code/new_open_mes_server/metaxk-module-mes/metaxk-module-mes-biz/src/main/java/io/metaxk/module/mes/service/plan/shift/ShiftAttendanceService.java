package io.metaxk.module.mes.service.plan.shift;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.plan.shift.vo.query.SelectShiftAttendanceQuery;
import io.metaxk.module.mes.controller.admin.plan.shift.vo.request.InsertShiftAttendanceReqVo;
import io.metaxk.module.mes.controller.admin.plan.shift.vo.request.UpdateShiftAttendanceReqVo;
import io.metaxk.module.mes.controller.admin.plan.shift.vo.response.SelectShiftAttendanceResVo;

import java.util.List;

/**
 * io.metaxk.module.mes.dal.service.plan.shift
 *
 * @author 万界星空
 * @time 2023/8/3 17:25
 */

public interface ShiftAttendanceService {
    Integer saveShiftAttendance(InsertShiftAttendanceReqVo insertShiftAttendanceReqVo);

    Integer removeShiftAttendanceByIds(List<Long> ids);

    Integer updateShiftAttendanceById(UpdateShiftAttendanceReqVo updateShiftAttendanceReqVo);

    SelectShiftAttendanceResVo selectShiftAttendanceById(Long id);

    PageResult<SelectShiftAttendanceResVo> selectShiftAttendanceList(SelectShiftAttendanceQuery selectShiftAttendanceQuery);
}
