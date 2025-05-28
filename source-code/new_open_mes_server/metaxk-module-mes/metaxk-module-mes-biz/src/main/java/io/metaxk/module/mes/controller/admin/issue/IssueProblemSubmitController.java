package io.metaxk.module.mes.controller.admin.issue;

import io.metaxk.framework.common.exception.ErrorCode;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.issue.vo.IssueProblemSubmitQueryVo;
import io.metaxk.module.mes.dal.dataobject.issue.IssueProblemSubmit;
import io.metaxk.module.mes.service.issue.IssueProblemSubmitService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.success;

/**
 * io.metaxk.module.mes.controller.admin.issue
 *
 * @author 万界星空
 * @time 2023/7/27 09:45
 */

@Tag(name = "管理后台 - 问题管理设置 - 问题提交")
@RestController
@RequestMapping("/mes/issue/problemSubmit")
public class IssueProblemSubmitController {

    @Resource
    private IssueProblemSubmitService issueProblemSubmitService;

    @PostMapping("/save")
    public CommonResult<Integer> save(@RequestBody @Valid IssueProblemSubmit issueProblemSubmit, BindingResult results){
        //验空
        if (results.hasErrors())
            throw  exception(new ErrorCode(1000, Objects.requireNonNull(results.getFieldError()).getDefaultMessage()));
        return success(issueProblemSubmitService.saveIssueProblemSubmit(issueProblemSubmit)).setMsg("新增成功");
    }

    @DeleteMapping("/batch")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids){
        return success(issueProblemSubmitService.removeIssueProblemSubmit(ids)).setMsg("删除成功");
    }

    @PutMapping("/update")
    public CommonResult<Integer> update(@RequestBody IssueProblemSubmit issueProblemSubmit){
        return success(issueProblemSubmitService.updateIssueProblemSubmit(issueProblemSubmit)).setMsg("修改成功");
    }

    @GetMapping("/find/{id}")
    public CommonResult<IssueProblemSubmit> findIssueProblemSubmitById(@PathVariable Long id){
        return success(issueProblemSubmitService.findIssueProblemSubmitById(id));
    }

    @GetMapping("/list")
    public CommonResult<PageResult<IssueProblemSubmit>> list(IssueProblemSubmitQueryVo issueProblemSubmitQueryVo){
        return success(issueProblemSubmitService.findPage(issueProblemSubmitQueryVo));
    }

    @PutMapping("/check/{id}")
    public CommonResult<Integer> update(@PathVariable Integer id){
        return success(issueProblemSubmitService.updateIssueProblemSubmit(new IssueProblemSubmit().setId(id).setStatus("已关闭"))).setMsg("修改成功");
    }

}
