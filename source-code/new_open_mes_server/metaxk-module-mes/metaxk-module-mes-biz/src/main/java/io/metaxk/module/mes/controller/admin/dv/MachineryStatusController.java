package io.metaxk.module.mes.controller.admin.dv;


import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.dv.vo.MachineryStatusQueryVo;
import io.metaxk.module.mes.dal.dataobject.dv.MachineryStatus;
import io.metaxk.module.mes.service.dv.MachineryStatusService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.framework.common.pojo.CommonResult.success;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.MACHINERY_STATUS_EXIST;


/**
 * @author 万界星空
 * @time 2023/6/20 15:33
 */
@Tag(name = "管理后台 - 设备台账状态")
@RestController
@RequestMapping("/mes/dv/machinery/status")
public class MachineryStatusController {


    @Resource
    private MachineryStatusService  machineryStatusService;



    @GetMapping("/list")
    public CommonResult<PageResult<MachineryStatus>> findList(MachineryStatusQueryVo machineryStatusPageVO){
        PageResult<MachineryStatus> pageResult = machineryStatusService.findList(machineryStatusPageVO);
        return  success(pageResult);
    }




    @PostMapping("/save")
    public CommonResult<Integer> save(@RequestBody MachineryStatus machineryStatus){
      if(machineryStatusService.findByStatusName(machineryStatus.getStatusName()) != null){
          throw exception(MACHINERY_STATUS_EXIST);
      }
      return  success(machineryStatusService.save(machineryStatus)).setMsg("新增成功");
    }



    @DeleteMapping("/batch")
    public CommonResult<Integer>  batch(@RequestBody List<Long> ids){
        return  success(machineryStatusService.batch(ids)).setMsg("删除成功");
    }


    @GetMapping("/get/{id}")
    public CommonResult<MachineryStatus>  getById(@PathVariable Long id){
        return  success(machineryStatusService.getMachineryStatusById(id));
    }


    @PutMapping("/update")
    public CommonResult<Integer>  update(@RequestBody MachineryStatus machineryStatus){
        return  success(machineryStatusService.update(machineryStatus)).setMsg("修改成功");
    }




}
