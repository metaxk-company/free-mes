package io.metaxk.module.mes.service.impl.pro;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.metaxk.framework.file.core.client.FileClientFactory;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.common.UserConstants;
import io.metaxk.module.mes.controller.admin.md.vo.RouteProcessVo;
import io.metaxk.module.mes.controller.admin.pro.vo.ProWorkorderVo;
import io.metaxk.module.mes.controller.admin.pro.vo.WorkorderExcelVo;
import io.metaxk.module.mes.controller.admin.pro.vo.WorkOrderQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.*;
import io.metaxk.module.mes.dal.dataobject.pro.*;
import io.metaxk.module.mes.dal.dataobject.pro.Process;
import io.metaxk.module.mes.dal.mysql.md.*;
import io.metaxk.module.mes.dal.mysql.pro.*;
import io.metaxk.module.mes.service.pro.WorkOrderService;
import io.metaxk.module.mes.utils.BarcodeUtil;
import io.metaxk.module.mes.utils.FileUtils;
import io.metaxk.module.mes.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import org.springframework.web.multipart.MultipartFile;



/**
 * 生产工单 Service 实现类
 * @author 万界星空MES
 */
@Service
public class WorkOrderServiceImpl implements WorkOrderService {


    @Resource
    private WorkOrderMapper workorderMapper;

    @Resource
    private ItemMapper itemMapper;

    @Resource
    private ItemTypeMapper itemTypeMapper;


    @Resource
    private RouteProductMapper routeProductMapper;


    @Resource
    private UnitMeasureMapper unitMeasureMapper;


    @Resource
    private WorkShopMapper workshopMapper;

    @Resource
    protected ProcessMapper processMapper;

    @Resource
    private RouteMapper routeMapper;


    @Resource
    private WorkStationMapper workstationMapper;


    @Resource
    private WorkOrderBomMapper workorderBomMapper;

    @Resource
    private RouteProcessMapper routeProcessMapper;




    /**
     * 文件上传服务
     */
    @Resource
    private FileClientFactory fileClientFactory;




    @Override
    public WorkOrder getWorkorder(Long id) {
        return workorderMapper.selectById(id);
    }



    @Override
    public PageResult<WorkOrder> getWorkOrderPage(WorkOrderQueryVo pageReqVO) {
        return workorderMapper.selectPage(pageReqVO);
    }




    @Override
    public List<ProWorkorderVo> synchronizeOrders(ProWorkorderVo proWorkorderVo) {
        return workorderMapper.synchronizeOrders(proWorkorderVo);
    }


    @Override
    public List<WorkorderExcelVo> listData() {
        List<WorkOrder> dictList = workorderMapper.selectList();
        ArrayList<WorkorderExcelVo> excelDictDTOList = new ArrayList<>(dictList.size());
        dictList.forEach(dict -> {
            WorkorderExcelVo excelDictDTO = new WorkorderExcelVo();
            BeanUtils.copyProperties(dict, excelDictDTO);
            excelDictDTOList.add(excelDictDTO);
        });
        return excelDictDTOList;
    }


    @Override
    public String checkWorkorderCodeUnique(WorkOrder createReqVO) {
        WorkOrder workorder = workorderMapper.checkWorkorderCodeUnique(createReqVO);
        Long workOrderId = createReqVO.getId() == null ? -1L : createReqVO.getId();
        if (StringUtils.isNotNull(workorder) && workorder.getId().longValue() != workOrderId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }




    @Override
    public void saveWorkOrder(WorkOrder workorder) {
        workorderMapper.insert(workorder);
    }





    @Override
    public WorkOrder selectProWorkorderByWorkorderId(Long workorderId) {
        QueryWrapper<WorkOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", workorderId);
        return workorderMapper.selectOne(queryWrapper);
    }

    @Override
    public Integer updateProWorkorder(WorkOrder workorder) {
        workorder.setUpdateTime(new Date());
        return workorderMapper.updateById(workorder);
    }

    @Override
    public Integer removeWorkOrderByWorkOrderIds(List<Long> workorderIds) {
        return workorderMapper.deleteBatchIds(workorderIds);
    }



    @Override
    public List<WorkOrder> selectProWorkorderList(WorkOrder proWorkorder) {
         return workorderMapper.selectList();
    }


    /**
     * 获取工序Url(二维码)
     */
    @Override
    public String generateProcessUrl(String taskCode, String processCode, String processName, String barcodeFormat) {
        File buf = BarcodeUtil.generateProcessUrl(taskCode, processCode, processName, barcodeFormat,
                "./tmp/barcode/" + processCode + ".png");
        MultipartFile file = FileUtils.getMultipartFile(buf);
        String fileName = null;
        try {
           // fileName = FileUploadUtils.uploadMinio(file);
            //调用平台自带的文件上传服务，new Long(18) 是文件配置ID
            fileName = fileClientFactory.getFileClient(new Long(18)).upload(file.getBytes(), IdUtil.fastSimpleUUID() + ".jpg", "image/jpeg");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //删除掉临时文件
            if (buf != null && buf.exists()) {
                FileUtils.deleteFile(buf.getAbsolutePath());
            }
        }
        return fileName;
    }


    /**
     * 获取任务Url(二维码)
     */
    @Override
    public String generateTaskUrl(String taskCode, String itemCode, String unitOfMeasure, String workorderCodes, String itemName, String barcodeFormat) {
        File buf = BarcodeUtil.generateBarCodeTaskUrl(taskCode, itemCode, unitOfMeasure, workorderCodes, itemName, barcodeFormat,
                "./tmp/barcode/" + taskCode + ".png");
        MultipartFile file = FileUtils.getMultipartFile(buf);
        String fileName = null;
        try {
            //fileName = FileUploadUtils.uploadMinio(file);
            //调用平台自带的文件上传服务，new Long(18) 是文件配置ID
            fileName = fileClientFactory.getFileClient(new Long(18)).upload(file.getBytes(), IdUtil.fastSimpleUUID() + ".jpg", "image/jpeg");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //删除掉临时文件
            if (buf != null && buf.exists()) {
                FileUtils.deleteFile(buf.getAbsolutePath());
            }
        }
        return fileName;
    }


    /**
     *  同步订单添加
     */
    @Override
    public void syncOrders() throws IOException, ParseException {
        //调用第三方接口方法
        String response =  this.callProductionOrderAPI();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        JsonNode rows = jsonNode.get("rows");

        List<UnitMeasure> list = unitMeasureMapper.selectList();
        String firstUnitName = null;
        if (!list.isEmpty()) {
             firstUnitName = list.get(0).getMeasureName();
        }
        for (JsonNode row : rows) {
            String workorderCode = row.get("workorderCode").asText();
            String workorderName = row.get("workorderName").asText();
            String orderSource = row.get("orderSource").asText();
            String productId = row.get("productId").asText();
            String productCode = row.get("productCode").asText();
            String productName = row.get("productName").asText();
            String itemOrProduct = row.get("itemOrProduct").asText();
            String quantity = row.get("quantity").asText();
            String status = row.get("status").asText();
            String itemTypeId = row.get("itemTypeId").asText();
            String itemTypeCode = row.get("itemTypeCode").asText();
            String itemTypeName = row.get("itemTypeName").asText();


            String startTime = row.get("startTime").asText();
            String requestDate = row.get("requestDate").asText();



            String workshopCode = row.get("workshopCode").asText();
            String workshopName = row.get("workshopName").asText();
            String processCode = row.get("processCode").asText();
            String processName = row.get("processName").asText();
            String routeCode = row.get("routeCode").asText();
            String routeName = row.get("routeName").asText();

            String workstationCode = row.get("workstationCode").asText();
            String workstationName = row.get("workstationName").asText();
            //数据类型转换
            Long productIds = Long.parseLong(productId);
            BigDecimal quantitys = BigDecimal.valueOf(Double.parseDouble(quantity));
            BigDecimal quantitybd = new BigDecimal(quantity);

            if (status.equals("PREPARE")) {
                WorkOrder Workorder = workorderMapper.selectProWorkorderByWorkorderCode(workorderCode);
                if (Workorder == null) {
                    WorkOrder proWorkorder = new WorkOrder();
                    proWorkorder.setWorkorderCode(workorderCode);
                    proWorkorder.setProductId(productIds);
                    proWorkorder.setProductCode(productCode);
                    proWorkorder.setWorkorderName(workorderName);
                    proWorkorder.setProductName(productName);
                    proWorkorder.setQuantity(quantitys);
                    proWorkorder.setUnitOfMeasure(firstUnitName);
                    proWorkorder.setOrderSource(orderSource);
                    proWorkorder.setAttr1("1");
                    workorderMapper.insert(proWorkorder);
                }

             WorkOrder WorkorderExist = workorderMapper.selectProWorkorderByWorkorderCode(workorderCode);
             WorkOrderBom workorderBom = workorderBomMapper.selectByIdAndCodeAndName(WorkorderExist.getId(),productId,productCode,productName);
              if(workorderBom == null){
                 WorkOrderBom workorderBomDO = new WorkOrderBom();
                 workorderBomDO.setWorkorderId(WorkorderExist.getId());
                 workorderBomDO.setItemId(Long.valueOf(productId));
                 workorderBomDO.setItemCode(productCode);
                 workorderBomDO.setItemName(productName);
                 workorderBomDO.setUnitOfMeasure(firstUnitName);
                 workorderBomDO.setItemOrProduct(itemOrProduct);
                 workorderBomDO.setQuantity(quantitybd);
                 workorderBomMapper.insert(workorderBomDO);
             }

                Item item = itemMapper.findByIdAndItemCodeAndItemNameAndSpc(productId,productCode,productName);
                Long prod =  Long.parseLong(productId);
                Long typeId =  Long.parseLong(itemTypeId);
                if(item == null){
                    Item mdItem = new Item();
                    mdItem.setId(prod);
                    mdItem.setItemCode(productCode);
                    mdItem.setItemName(productName);
                    mdItem.setUnitOfMeasure(firstUnitName);
                    mdItem.setItemOrProduct(itemOrProduct);
                  //  mdItem.setCreateTime(new Date());
                    mdItem.setItemTypeId(typeId);
                    itemMapper.insert(mdItem);

                }
                ItemType itemType = itemTypeMapper.findByTypeIdAndTypeCodeAndTypeName(itemTypeId,itemTypeCode,itemTypeName);
                if(itemType == null){
                    ItemType itemType1 = new ItemType();
                    itemType1.setId(typeId);
                    itemType1.setItemTypeCode(itemTypeCode);
                    itemType1.setItemTypeName(itemTypeName);
                    itemType1.setParentTypeId(200L);
                    itemType1.setItemOrProduct(itemOrProduct);
                    itemType1.setAncestors(0 +","+ 200);
                    itemTypeMapper.insert(itemType1);
                }

                WorkShop workshop =  workshopMapper.selectByCodeAndName(workshopCode,workshopName);
                if(workshop == null){
                    WorkShop workshopDO = new WorkShop();
                    workshopDO.setWorkshopCode(workshopCode);
                    workshopDO.setWorkshopName(workshopName);
                    workshopDO.setEnableFlag("Y");
                    workshopMapper.insert(workshopDO);
                }

               Process process = processMapper.selectByCodeAndName(processCode,processName);
                if(process == null){
                    Process processDO = new Process();
                    processDO.setProcessCode(processCode);
                    processDO.setProcessName(processName);
                    processDO.setEnableFlag("Y");
                    processMapper.insert(processDO);
                }

              WorkShop workshopExist =  workshopMapper.selectByCodeAndName(workshopCode,workshopName);
              WorkStation workstation =  workstationMapper.selectByCodeAndName(workstationCode,workstationName);
              Process processExist = processMapper.selectByCodeAndName(processCode,processName);
              if(workstation == null){
                  WorkStation workstationDO = new WorkStation();
                  workstationDO.setWorkstationCode(workstationCode);
                  workstationDO.setWorkstationName(workstationName);
                 workstationDO.setWorkshopId(workshopExist.getId());
                  workstationDO.setWorkshopCode(workshopExist.getWorkshopCode());
                  workstationDO.setWorkshopName(workshopExist.getWorkshopName());
                  workstationDO.setProcessId(processExist.getId());
                  workstationDO.setProcessCode(processExist.getProcessCode());
                  workstationDO.setProcessName(processExist.getProcessName());
                  workstationDO.setEnableFlag("Y");
                  workstationMapper.insert(workstationDO);
              }


               Route route =  routeMapper.selectByCodeAndName(routeCode,routeName);
               if(route == null){
                   Route routeDO = new Route();
                   routeDO.setRouteCode(routeCode);
                   routeDO.setRouteName(routeName);
                   routeDO.setEnableFlag("Y");
                   routeMapper.insert(routeDO);
               }
                Route routeExist =  routeMapper.selectByCodeAndName(routeCode,routeName);
                RouteProduct routeProduct = routeProductMapper.findByTypeIdAndTypeCodeAndTypeName(productId,productCode,productName);
                //取出工艺的编号
                if (routeProduct == null ) {
                    RouteProduct routeProduct2 = new RouteProduct();
                    routeProduct2.setRouteId(routeExist.getId());
                    routeProduct2.setItemId(prod);
                    routeProduct2.setItemCode(productCode);
                    routeProduct2.setItemName(productName);
                    routeProduct2.setUnitOfMeasure(firstUnitName);
                    routeProductMapper.insert(routeProduct2);
                    System.out.println("========数据同步成功=========");
                }
            }
        }
    }

    /**
     * 同步订单
     */
    @Override
    public List<WorkOrder> synchronizeOrdersList(ProWorkorderVo proWorkorderVo) {
        return workorderMapper.synchronizeOrdersListAll(proWorkorderVo);
    }


    @Override
    public WorkOrder selectByWorkOrderId(Long workorderId) {
        return workorderMapper.selectById(workorderId);
    }

    @Override
    public List<WorkOrder> findWorkerOrderAll() {
        return workorderMapper.selectList();
    }

    @Override
    public WorkOrder findWorkOrderCode(String workOrderCode) {
        LambdaQueryWrapperX<WorkOrder> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(WorkOrder::getWorkorderCode,workOrderCode);
        return workorderMapper.selectOne(queryWrapperX);
    }

    @Override
    public WorkOrder findWorkOrderById(Long id) {
        return workorderMapper.selectById(id);
    }

    @Override
    public Map<String, Object> findRouteProcess(String itemCode) {
        List<RouteProduct> routeProductList  = routeProductMapper.findRouteByItemCode(itemCode);
        ArrayList<RouteProcessVo> arrayList = new ArrayList<>();
        for(RouteProduct routeProduct:routeProductList){
            List<Route> routeList =  routeMapper.findRouteById(routeProduct.getRouteId());
            for(Route route:routeList){
                RouteProcessVo routeProcessVo = new RouteProcessVo();
                routeProcessVo.setRouteName(route.getRouteName());
                List<RouteProcess> routeProcessList =   routeProcessMapper.findByRouteId(route.getId());
                for(RouteProcess routeProcess:routeProcessList){
                  //  RouteProcessVo processVo = new RouteProcessVo();
                    routeProcessVo.setProcessName(routeProcess.getProcessName());
                }
                arrayList.add(routeProcessVo);
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("arrayList",arrayList);
        return map;
    }





    /**
     * 调用第三方接口返回数据
     */
    public String callProductionOrderAPI() throws IOException {
        URL url = new URL("https://1c3b783f-290f-46f1-96a7-84adc925f67e-8080-public.devstudio.aliyuncs.com/test/test/list");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6ImQyN2Q2ODVmLWU5YjItNDMzZi05ZjdlLTg4Mjg2YThjYTA2MSJ9.lKgaJ9A3XZaNeMndfDddInz5uqQyg7JM_ZbQMVeW2sCXJ7JAv6wI00oN3xVZbIS8nqAvBQaS7wv_zBZ5GLRDMQ");
        con.setRequestProperty("Accept", "application/json");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
