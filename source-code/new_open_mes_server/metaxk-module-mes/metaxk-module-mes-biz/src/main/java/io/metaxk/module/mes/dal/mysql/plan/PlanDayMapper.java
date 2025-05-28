package io.metaxk.module.mes.dal.mysql.plan;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.plan.vo.query.SelectPlanDayQuery;
import io.metaxk.module.mes.dal.dataobject.plan.PlanDay;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * io.metaxk.module.mes.dal.mysql.plan
 *
 * @author 万界星空
 * @time 2023/8/3 17:24
 */

@Mapper
public interface PlanDayMapper extends BaseMapperX<PlanDay> {

    default PageResult<PlanDay> pageQuery(SelectPlanDayQuery selectPlanDayQuery) {
        LambdaQueryWrapperX<PlanDay> queryWrapperX = new LambdaQueryWrapperX<>();

        if (StringUtils.isNotBlank(selectPlanDayQuery.getDay()))
            queryWrapperX.eq(PlanDay::getDay, selectPlanDayQuery.getDay());

        if (StringUtils.isBlank(selectPlanDayQuery.getDay()))
            queryWrapperX.isNotNull(PlanDay::getId);

        return selectPage(selectPlanDayQuery, queryWrapperX);
    }

    //确认是否存在过相同的number
    @Select("select * from plan_day where number = #{number} and deleted = 1")
    PlanDay selectOneFromDeleted(String number);
}
