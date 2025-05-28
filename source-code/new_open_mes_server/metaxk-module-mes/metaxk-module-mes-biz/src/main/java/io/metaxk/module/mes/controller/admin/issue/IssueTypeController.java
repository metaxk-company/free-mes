package io.metaxk.module.mes.controller.admin.issue;

import io.metaxk.framework.common.exception.ErrorCode;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.issue.vo.IssueTypeQueryVo;
import io.metaxk.module.mes.dal.dataobject.issue.IssueType;
import io.metaxk.module.mes.service.issue.IssueTypeService;
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

@Tag(name = "管理后台 - 问题管理设置 - 类型")
@RestController
@RequestMapping("/mes/issue/type")
public class IssueTypeController {

    @Resource
    private IssueTypeService issueTypeService;

    @PostMapping("/save")
    public CommonResult<Integer> save(@RequestBody @Valid IssueType issueType, BindingResult results){
        //验空
        if (results.hasErrors())
            throw  exception(new ErrorCode(1000, Objects.requireNonNull(results.getFieldError()).getDefaultMessage()));
        if (issueTypeService.findByName(issueType.getName()) != null) {
            throw exception(1001, "数据库中已经存在，不可以重复添加");
        }
        return success(issueTypeService.saveIssueType(issueType)).setMsg("新增成功");
    }

    @DeleteMapping("/batch")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids){
        return success(issueTypeService.removeIssueType(ids)).setMsg("删除成功");
    }

    @PutMapping("/update")
    public CommonResult<Integer> update(@RequestBody IssueType issueType){
        return success(issueTypeService.updateIssueType(issueType)).setMsg("修改成功");
    }

    @GetMapping("/find/{id}")
    public CommonResult<IssueType> findIssueTypeById(@PathVariable Long id){
        return success(issueTypeService.findIssueTypeById(id));
    }

    @GetMapping("/list")
    public CommonResult<PageResult<IssueType>> list(IssueTypeQueryVo issueTypeQueryVo){
        return success(issueTypeService.findPage(issueTypeQueryVo));
    }

    @GetMapping("/findByName/{name}")
    public CommonResult<IssueType> findByName(@PathVariable String name){
        return success(issueTypeService.findByName(name));
    }

    /*@GetMapping("/allTypeNameWithModeNameList")
    public CommonResult<List<TypeNameAndModeNameListResponseVO>> selectAllTypeNameWithModeNameList(){
        return success(issueTypeService.selectAllTypeNameWithModeNameList());
    }*/

}
