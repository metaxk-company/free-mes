package io.metaxk.module.mes.controller.admin.dept;

import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.module.mes.service.dept.SysDeptService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static io.metaxk.framework.common.pojo.CommonResult.success;

/**
 * @author 万界星空
 * @time 2023/7/7 15:02
 */
@Tag(name = "管理后台 - 部门")
@RestController
@RequestMapping("/mes/system/dept")
public class SysDeptController {



    @Resource
    private SysDeptService  sysDeptService;


    @GetMapping("/findList")
    public CommonResult  list(){
        return success(sysDeptService.findDeptList());
    }


}


