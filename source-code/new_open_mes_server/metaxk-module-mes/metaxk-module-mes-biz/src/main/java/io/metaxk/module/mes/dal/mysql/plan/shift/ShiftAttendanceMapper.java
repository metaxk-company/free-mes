package io.metaxk.module.mes.dal.mysql.plan.shift;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.plan.shift.vo.query.SelectShiftAttendanceQuery;
import io.metaxk.module.mes.dal.dataobject.plan.shift.ShiftAttendance;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * io.metaxk.module.mes.dal.mysql.plan.shift
 *
 * @author 万界星空
 * @time 2023/8/3 17:25
 */

@Mapper
public interface ShiftAttendanceMapper extends BaseMapperX<ShiftAttendance> {

    default PageResult<ShiftAttendance> pageQuery(SelectShiftAttendanceQuery selectShiftAttendanceQuery) {
        LambdaQueryWrapperX<ShiftAttendance> queryWrapperX = new LambdaQueryWrapperX<>();

        if (StringUtils.isNotBlank(selectShiftAttendanceQuery.getDate()))
            queryWrapperX.eq(ShiftAttendance::getDate, selectShiftAttendanceQuery.getDate());

        if (StringUtils.isBlank(selectShiftAttendanceQuery.getDate()))
            queryWrapperX.isNotNull(ShiftAttendance::getId);

        return selectPage(selectShiftAttendanceQuery, queryWrapperX);
    }

    //确认是否存在过相同的number
    @Select("select * from plan_shift_attendance where number = #{number} and deleted = 1")
    ShiftAttendance selectOneFromDeleted(String number);
}
