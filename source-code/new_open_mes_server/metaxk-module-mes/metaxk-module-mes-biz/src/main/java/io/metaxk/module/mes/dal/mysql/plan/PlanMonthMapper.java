package io.metaxk.module.mes.dal.mysql.plan;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.plan.vo.query.SelectPlanMonthQuery;
import io.metaxk.module.mes.dal.dataobject.plan.PlanMonth;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * io.metaxk.module.mes.dal.mysql.plan
 *
 * @author 万界星空
 * @time 2023/8/3 17:23
 */

@Mapper
public interface PlanMonthMapper extends BaseMapperX<PlanMonth> {
    default PageResult<PlanMonth> pageQuery(SelectPlanMonthQuery selectPlanMonthQuery) {

        LambdaQueryWrapperX<PlanMonth> queryWrapperX = new LambdaQueryWrapperX<>();

        if (StringUtils.isNotBlank(selectPlanMonthQuery.getMonth()))
            queryWrapperX.eq(PlanMonth::getMonth, selectPlanMonthQuery.getMonth());

        if (StringUtils.isBlank(selectPlanMonthQuery.getMonth()))
            queryWrapperX.isNotNull(PlanMonth::getId);

        return selectPage(selectPlanMonthQuery, queryWrapperX);
    }

    //确认是否存在过相同的number
    @Select("select * from plan_month where number = #{number} and deleted = 1")
    PlanMonth selectOneFromDeleted(String number);
}
