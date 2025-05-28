package io.metaxk.module.mes.controller.admin.user;

import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.user.vo.UserPageReqVo;
import io.metaxk.module.mes.dal.dataobject.user.SysUser;
import io.metaxk.module.mes.service.user.SysUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import static io.metaxk.framework.common.pojo.CommonResult.success;

/**
 * @author 万界星空
 * @time 2023/6/27 17:55
 */
@Tag(name = "管理后台 - 用户")
@RestController
@RequestMapping("/mes/system/user")
public class SysUserController {

    @Resource
    private SysUserService userService;



    @GetMapping("/list")
    public CommonResult<PageResult<SysUser>> list(UserPageReqVo userPageReqVO){
        PageResult<SysUser> pageResult =  userService.selectPage(userPageReqVO);
        return success(pageResult);
    }




}
