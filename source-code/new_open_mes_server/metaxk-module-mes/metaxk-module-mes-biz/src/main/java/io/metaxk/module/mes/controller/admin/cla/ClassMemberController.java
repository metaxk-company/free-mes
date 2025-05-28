package io.metaxk.module.mes.controller.admin.cla;

import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.cla.vo.ClaMemberQueryVo;
import io.metaxk.module.mes.dal.dataobject.cla.ClassMember;
import io.metaxk.module.mes.dal.dataobject.user.SysUser;
import io.metaxk.module.mes.service.cla.ClassMemberService;
import io.metaxk.module.mes.service.user.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import static io.metaxk.framework.common.pojo.CommonResult.success;


/**
 * @author 万界星空
 * @time 2023/6/27 16:08
 */
@Tag(name = "管理后台 - 班组人员")
@RestController
@RequestMapping("/mes/cal/member")
public class ClassMemberController {



    @Resource
    private ClassMemberService classMemberService;

    @Resource
    private SysUserService sysUserService;



    @PostMapping("/save")
    public CommonResult save(@RequestBody List<ClassMember> classMemberList){
         boolean hasDuplicates = false;
          for(ClassMember classMember:classMemberList){
            if(classMemberService.findMemberByCodeAndName(classMember.getPersonCode(),classMember.getUserName()) != null){
                hasDuplicates = true;
                break;
            }
          }
            for(ClassMember classMember:classMemberList){
                SysUser sysUser =   sysUserService.findUserByIdAndName(classMember.getPersonCode(),classMember.getUserName());
                sysUser.setId(classMember.getId());
                sysUser.setJoinTeam("Y");
                sysUserService.updateSysUser(sysUser);
            }
            if (!hasDuplicates) {
                classMemberService.saveClassMember(classMemberList);
                return success(null).setMsg("新增成功");
            }
         return  success(null);
    }





    @GetMapping("/find")
    public CommonResult<PageResult<ClassMember>> findTeamCode(ClaMemberQueryVo classMemberPageReqVO){
        PageResult<ClassMember> pageResult = classMemberService.selectClassMemberByCode(classMemberPageReqVO);
        return success(pageResult);
    }





    @DeleteMapping("/batch")
    @Operation(summary = "移除班组成员")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids){
        for(Long id:ids){
            SysUser sysUser = sysUserService.findUserById(id);
            sysUser.setJoinTeam("N");
            sysUserService.updateSysUser(sysUser);
        }
        return success(classMemberService.removeByPeopleId(ids)).setMsg("移除人员成功");
    }










}
