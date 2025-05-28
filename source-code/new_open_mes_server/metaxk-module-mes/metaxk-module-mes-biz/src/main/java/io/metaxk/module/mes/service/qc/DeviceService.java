package io.metaxk.module.mes.service.qc;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.qc.vo.DeviceQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.Device;

import java.io.InputStream;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/6 11:17
 */
public interface DeviceService {


    Integer saveInspectDevice(Device inspectDevice);


    Integer removeInspectDevice(List<Long> ids);

    Device findDeviceById(Long id);

    Integer updateInspectDevice(Device inspectDevice);

    PageResult<Device> findPage(DeviceQueryVo inspectDevicePageReqVO);

    void importExcelData(InputStream inputStream);

    Device findDeviceByName(String deviceName);

    Device findDeviceByCode(String code);

    List<Device> listAll();

}
