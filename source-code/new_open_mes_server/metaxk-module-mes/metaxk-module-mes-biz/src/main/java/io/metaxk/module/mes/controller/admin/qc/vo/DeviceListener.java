package io.metaxk.module.mes.controller.admin.qc.vo;

import com.alibaba.excel.event.AnalysisEventListener;
import io.metaxk.module.mes.dal.dataobject.qc.Device;
import io.metaxk.module.mes.dal.mysql.dept.SysDeptMapper;
import io.metaxk.module.mes.dal.mysql.md.WorkShopMapper;
import io.metaxk.module.mes.dal.mysql.pro.ProcessMapper;
import io.metaxk.module.mes.dal.mysql.qc.DeviceMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.excel.context.AnalysisContext;
import org.springframework.beans.BeanUtils;
import java.util.ArrayList;
import java.util.List;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.*;

/**
 * @author 万界星空
 * @time 2023/7/7 15:16
 */
@Slf4j
@NoArgsConstructor
public class DeviceListener extends AnalysisEventListener<DeviceExcelVo> {


    private static final int BATCH_COUNT = 3000;

    List<DeviceExcelVo> list = new ArrayList<>();

    private DeviceMapper inspectDeviceMapper;

    private WorkShopMapper workshopMapper;

    private ProcessMapper processMapper;


    private SysDeptMapper sysDeptMapper;


    public DeviceListener(DeviceMapper inspectDeviceMapper, WorkShopMapper workshopMapper, ProcessMapper processMapper, SysDeptMapper sysDeptMapper) {
        this.inspectDeviceMapper = inspectDeviceMapper;
        this.workshopMapper = workshopMapper;
        this.processMapper = processMapper;
        this.sysDeptMapper = sysDeptMapper;
    }







    @Override
    public void invoke(DeviceExcelVo data, AnalysisContext context) {
        log.info("解析到一条记录: {}", data);
        list.add(data);
        if (list.size() >= BATCH_COUNT) {
            saveData();
            list.clear();
        }
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        log.info("所有数据解析完成！");
    }



    private void saveData() {
        log.info("{}条数据，开始存储数据库！", list.size());
        for (int rowIndex = 0; rowIndex < list.size(); rowIndex++) {
            DeviceExcelVo inspectDeviceExcelVO = list.get(rowIndex);

            Device inspectDevice = new Device();
            BeanUtils.copyProperties(inspectDeviceExcelVO,inspectDevice);

            if (workshopMapper.findWorkshopByName(inspectDevice.getWorkshop()) == null) {
               throw exception(IMPORT_DATA_WORKSHOP_NAME_ERROR);
            }

            if (sysDeptMapper.findDeptByName(inspectDevice.getDepartment()) == null) {
                throw exception(IMPORT_DATA_DEPT_NAME_ERROR);
            }

//            if (processMapper.findProcessByName(inspectDevice.getProcess()) == null) {
//                int errorRowIndex = rowIndex + 1;
//                log.error("第{}行的工作车间填写错误，请检查Excel中第{}列的数据。", errorRowIndex, errorRowIndex);
//                throw exception(IMPORT_DATA_PROCESS_NAME_ERROR);
//            }
              inspectDeviceMapper.insert(inspectDevice);
        }
        log.info("存储数据库成功！");
    }
}
