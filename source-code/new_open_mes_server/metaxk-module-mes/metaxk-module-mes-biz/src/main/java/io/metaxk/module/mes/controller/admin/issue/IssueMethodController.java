package io.metaxk.module.mes.controller.admin.issue;

import io.metaxk.framework.common.exception.ErrorCode;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.issue.vo.IssueMethodQueryVo;
import io.metaxk.module.mes.dal.dataobject.issue.IssueMethod;
import io.metaxk.module.mes.service.issue.IssueMethodService;
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

@Tag(name = "管理后台 - 问题管理设置 - 模式")
@RestController
@RequestMapping("/mes/issue/method")
public class IssueMethodController {

    @Resource
    private IssueMethodService issueMethodService;

    @PostMapping("/save")
    public CommonResult<Integer> save(@RequestBody @Valid IssueMethod issueMethod, BindingResult results){
        //验空
        if (results.hasErrors())
            throw  exception(new ErrorCode(1000, Objects.requireNonNull(results.getFieldError()).getDefaultMessage()));
        if (issueMethodService.findByName(issueMethod.getName()) != null) {
            throw exception(1001, "数据库中已经存在，不可以重复添加");
        }
        return success(issueMethodService.saveIssueMethod(issueMethod)).setMsg("新增成功");
    }

    @DeleteMapping("/batch")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids){
        return success(issueMethodService.removeIssueMethod(ids)).setMsg("删除成功");
    }

    @PutMapping("/update")
    public CommonResult<Integer> update(@RequestBody IssueMethod issueMethod){
        return success(issueMethodService.updateIssueMethod(issueMethod)).setMsg("修改成功");
    }

    @GetMapping("/find/{id}")
    public CommonResult<IssueMethod> findIssueMethodById(@PathVariable Long id){
        return success(issueMethodService.findIssueMethodById(id));
    }

    @GetMapping("/list")
    public CommonResult<PageResult<IssueMethod>> list(IssueMethodQueryVo issueMethodQueryVo){
        return success(issueMethodService.findPage(issueMethodQueryVo));
    }

}
