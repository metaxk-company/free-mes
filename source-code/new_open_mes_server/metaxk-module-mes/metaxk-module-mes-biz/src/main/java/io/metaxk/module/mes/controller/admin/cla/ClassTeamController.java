package io.metaxk.module.mes.controller.admin.cla;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.cla.vo.ClaTeamExcelVo;
import io.metaxk.module.mes.controller.admin.cla.vo.ClaTeamQueryVo;
import io.metaxk.module.mes.dal.dataobject.cla.ClassMember;
import io.metaxk.module.mes.dal.dataobject.cla.ClassTeam;
import io.metaxk.module.mes.dal.dataobject.user.SysUser;
import io.metaxk.module.mes.service.cla.ClassMemberService;
import io.metaxk.module.mes.service.cla.ClassTeamService;
import io.metaxk.module.mes.service.user.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.*;


/**
 * @author 万界星空
 * @time 2023/6/26 9:44
 */
@Tag(name = "管理后台 - 班组设置")
@RestController
@RequestMapping("/mes/cal/team")
public class ClassTeamController {


    @Resource
    private ClassTeamService calTeamService;

    @Resource
    private ClassMemberService  classMemberService;


    @Resource
    private SysUserService sysUserService;



    @PostMapping("/save")
    public CommonResult<Integer> save(@RequestBody ClassTeam calTeam){
     if( calTeamService.findCalTeamByCode(calTeam.getTeamCode()) != null){
        throw exception(CAL_TEAM__CODE_EXIST);
     }
     if( calTeamService.findCalTeamByName(calTeam.getTeamName()) != null){
         throw exception(CAL_TEAM__NAME_EXIST);
     }
        return success(calTeamService.saveCalTeam(calTeam)).setMsg("新增成功");
    }




    @Operation(summary = "班组设置删除")
    @DeleteMapping("/batch")
    public CommonResult<Integer> batch(@RequestBody List<Long> ids){
        for(Long id:ids){
            ClassTeam classTeam = calTeamService.findCalTeamById(id);
            List<ClassMember> memberList = classMemberService.findMemberByCode(classTeam.getTeamCode());
            for(ClassMember member:memberList){
                //获取班组人员id
                Long memberId = member.getId();
                SysUser sysUser = sysUserService.findUserById(memberId);
                sysUser.setJoinTeam("N");
                sysUserService.updateSysUser(sysUser);

                classMemberService.removeById(member.getId());
            }
        }
        return success(calTeamService.removeCalTeamByIds(ids)).setMsg("删除成功");
    }





    @PutMapping("/update")
    public  CommonResult<Integer> update(@RequestBody ClassTeam calTeam){
        return  success(calTeamService.updateCalTeam(calTeam)).setMsg("修改成功");
    }




    @GetMapping("/list")
    public CommonResult<PageResult<ClassTeam>> list(ClaTeamQueryVo calTeamPageReqVO){
       PageResult<ClassTeam> pageResult = calTeamService.getCalTeamPage(calTeamPageReqVO);
       return  success(pageResult);
    }




    @GetMapping("/get/{id}")
    public CommonResult<ClassTeam> getCalTeam(@PathVariable Long id){
        return  success(calTeamService.findCalTeamById(id));
    }




    @GetMapping("/find/{code}")
    public CommonResult<ClassTeam> findCalTeamByCode(@PathVariable String code){
        return  success(calTeamService.findCalTeamByCode(code));
    }





    @GetMapping("/findMember/{code}")
    public  CommonResult<List<ClassMember> >  findMember(@PathVariable String code){
      List<ClassMember> classMemberList =  classMemberService.findMemberByCode(code);
      return success(classMemberList);
    }




    @GetMapping("/export")
    @Operation(summary = "班组导出")
    public void export(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("班组", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), ClaTeamExcelVo.class).registerWriteHandler(styleStrategy).sheet("班组管理").doWrite(calTeamService.exportData());
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }




}
