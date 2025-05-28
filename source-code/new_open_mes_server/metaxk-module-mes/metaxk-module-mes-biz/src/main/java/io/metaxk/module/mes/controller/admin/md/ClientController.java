package io.metaxk.module.mes.controller.admin.md;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.md.vo.ClientExcelVo;
import io.metaxk.module.mes.controller.admin.md.vo.ClientQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.Client;
import io.metaxk.module.mes.service.md.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
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
 * @time 2023/6/20 15:33
 */
@Tag(name = "管理后台 - 客户管理")
@RestController
@RequestMapping("/mes/md/client")
public class ClientController {



    @Resource
    private ClientService clientService;



    @GetMapping("/list")
    @Operation(summary = "客户条件分页查询")
    @PreAuthorize("@ss.hasPermission('md:client:list')")
    public CommonResult<PageResult<Client>>  getClientPage( ClientQueryVo clientPage) {
        PageResult<Client> pageResult = clientService.getClientPage(clientPage);
        return success(pageResult);
    }





    @PostMapping("/save")
    @Operation(summary = "新增客户管理")
    @PreAuthorize("@ss.hasPermission('md:client:save')")
    public CommonResult<Integer> save(@RequestBody Client client)
    {
       if(clientService.findClientByCode(client.getClientCode()) != null){
           throw exception(CLIENT_CODE_EXIST);
       }
        if(clientService.findClientByName(client.getClientName()) != null){
            throw exception(CLIENT_NAME_EXIST);
        }
        return success(clientService.insertClient(client)).setMsg("新增成功");
    }








    @PutMapping("/update")
    @Operation(summary = "修改客户管理")
    @PreAuthorize("@ss.hasPermission('md:client:update')")
    public CommonResult<Integer> updateClient(@RequestBody Client client) {
        return success( clientService.updatesClient(client)).setMsg("修改成功");
    }




    @DeleteMapping("/batch")
    @Operation(summary = "批量删除客户管理")
    @PreAuthorize("@ss.hasPermission('md:client:beatch')")
    public  CommonResult<Integer>  batch(@RequestBody List<Long> ids){
        return success(clientService.deleteClientByIds(ids)).setMsg("删除成功");
    }



    @GetMapping("/get/{id}")
    @Operation(summary = "客户管理详情")
    @PreAuthorize("@ss.hasPermission('md:client:get')")
    public CommonResult<Client>  getClient(@PathVariable Long id) {
        return success(clientService.getClient(id));
    }



    @GetMapping("/export")
    @Operation(summary = "客户管理导出")
    @PreAuthorize("@ss.hasPermission('md:client:export')")
    public void export(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("客户管理", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xls");
            SimpleColumnWidthStyleStrategy styleStrategy = new SimpleColumnWidthStyleStrategy(20);
            EasyExcel.write(response.getOutputStream(), ClientExcelVo.class).registerWriteHandler(styleStrategy).sheet("客户管理").doWrite(clientService.listData());
        } catch (IOException e) {
            throw exception(EXPORT_DATA_ERROR);
        }
    }



    @GetMapping("/listAll")
    @Operation(summary = "查詢所有客户信息")
    public CommonResult<List<Client>> listAll() {
        List<Client> list = clientService.listAll();
        return success(list);
    }

    @GetMapping("/getOrderNumberByName")
    @Operation(summary = "通过客户名称带出订单号")
    public CommonResult<String> getOrderNumberByName(String clientName) {
        return success(clientService.getOrderNumberByName(clientName));
    }

}
