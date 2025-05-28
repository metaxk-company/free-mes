package io.metaxk.module.mes.controller.admin.plan;

import io.metaxk.framework.common.exception.ErrorCode;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.plan.vo.query.SelectPlanDayQuery;
import io.metaxk.module.mes.controller.admin.plan.vo.request.InsertPlanDayReqVo;
import io.metaxk.module.mes.controller.admin.plan.vo.request.UpdatePlanDayReqVo;
import io.metaxk.module.mes.controller.admin.plan.vo.response.SelectPlanDayResVo;
import io.metaxk.module.mes.service.plan.PlanDayService;
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
 * io.metaxk.module.mes.controller.admin.plan
 *
 * @author 万界星空
 * @time 2023/8/3 17:34
 */

@Tag(name = "管理后台 - 日计划")
@RestController
@RequestMapping("/mes/plan/day")
public class PlanDayController {

    @Resource
    private PlanDayService planDayService;

    @PostMapping("/save")
    @Operation(summary = "保存日计划")
    public CommonResult<Integer> save(@RequestBody @Valid InsertPlanDayReqVo insertPlanDayReqVo, BindingResult results) {
        //验空
        if (results.hasErrors())
            throw  exception(new ErrorCode(1000, Objects.requireNonNull(results.getFieldError()).getDefaultMessage()));
        //检查传入日期格式是否匹配 07
        if (!insertPlanDayReqVo.getDay().matches("^(0[1-9]|[1-2][0-9]|3[0-1])$"))
            throw exception(PLAN_DAY_DAY_FORMAT_NOT_MATCHED);
        //计划数量不能为负
        if (insertPlanDayReqVo.getPlanQty() < 0)
            throw exception(PLAN_DAY_QTY_NOT_NEGATIVE);

        Integer i = planDayService.savePlanDay(insertPlanDayReqVo);
        //日计划编号已存在
        if (i == -1)
            throw exception(PLAN_DAY_NUMBER_EXIST);
        //关联的月计划不存在
        else if (i == -2)
            throw exception(PLAN_MONTH_NUMBER_NOT_EXIST);

        return success(i).setMsg("新增成功");
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除日计划")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids) {
        if (ids.isEmpty())
            throw exception(PLAN_BATCH_NEED_ID);

        Integer i = planDayService.removePlanDayByIds(ids);

        //删除列表中所有的PlanDay对应的PlanMonth必须是一样的
        if (i == -1)
            throw exception(PLAN_DAY_MONTH_NUMBER_NOT_MATCH);

        return success(i).setMsg("删除成功");
    }

    @PutMapping("/update")
    @Operation(summary = "修改日计划")
    public CommonResult<Integer> update(@RequestBody UpdatePlanDayReqVo updatePlanDayReqVo) {
        //检查传入日期格式是否匹配 07
        if (StringUtils.isNotBlank(updatePlanDayReqVo.getDay()) && !updatePlanDayReqVo.getDay().matches("^(0[1-9]|[1-2][0-9]|3[0-1])$"))
            throw exception(PLAN_DAY_DAY_FORMAT_NOT_MATCHED);
        //计划数量不能为负
        if (updatePlanDayReqVo.getPlanQty() != null && updatePlanDayReqVo.getPlanQty() < 0)
            throw exception(PLAN_DAY_QTY_NOT_NEGATIVE);

        Integer i = planDayService.updatePlanDayById(updatePlanDayReqVo);
        //修改的日计划id不存在
        if (i == -1)
            throw exception(PLAN_DAY_ID_NOT_EXIST);
        return success(i).setMsg("修改成功");
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "根据id查看日计划详情")
    public CommonResult<SelectPlanDayResVo> selectById(@PathVariable Long id) {
        SelectPlanDayResVo selectPlanDayResVo = planDayService.selectPlanDayById(id);
        //避免id不存在时的空指针
        if (selectPlanDayResVo == null)
            throw exception(PLAN_DAY_ID_NOT_EXIST);
        return success(selectPlanDayResVo);
    }

    @GetMapping("/findList")
    @Operation(summary = "查看日计划列表")
    public CommonResult<PageResult<SelectPlanDayResVo>> selectList(SelectPlanDayQuery selectPlanDayQuery) {
        //检查传入日期格式是否匹配 07
        if (StringUtils.isNotBlank(selectPlanDayQuery.getDay()) && !selectPlanDayQuery.getDay().matches("^(0[1-9]|[1-2][0-9]|3[0-1])$"))
            throw exception(PLAN_DAY_DAY_FORMAT_NOT_MATCHED);
        //分页查询时必须提供pageNo和pageSize (好像不用selectPage就不会报错了)
        if (selectPlanDayQuery.getPageNo() == null || selectPlanDayQuery.getPageSize() == null)
            throw exception(PLAN_PAGE_RESULT_NEED_PAGE_INFO);
        return success(planDayService.selectPlanDayList(selectPlanDayQuery));
    }

}
