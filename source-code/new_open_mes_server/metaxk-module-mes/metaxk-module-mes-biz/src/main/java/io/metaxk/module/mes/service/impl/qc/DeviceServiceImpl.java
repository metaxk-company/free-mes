package io.metaxk.module.mes.service.impl.qc;

import com.alibaba.excel.EasyExcel;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.qc.vo.DeviceListener;
import io.metaxk.module.mes.controller.admin.qc.vo.DeviceExcelVo;
import io.metaxk.module.mes.controller.admin.qc.vo.DeviceQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.Device;
import io.metaxk.module.mes.dal.mysql.dept.SysDeptMapper;
import io.metaxk.module.mes.dal.mysql.md.WorkShopMapper;
import io.metaxk.module.mes.dal.mysql.pro.ProcessMapper;
import io.metaxk.module.mes.dal.mysql.qc.DeviceMapper;
import io.metaxk.module.mes.service.qc.DeviceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/6 11:17
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    @Resource
    private DeviceMapper deviceMapper;

    @Resource
    private WorkShopMapper workshopMapper;
    @Resource
    private ProcessMapper processMapper;

    @Resource
    private SysDeptMapper sysDeptMapper;


    @Override
    public Integer saveInspectDevice(Device inspectDevice) {
        return deviceMapper.insert(inspectDevice);
    }

    @Override
    public Integer removeInspectDevice(List<Long> ids) {
        return deviceMapper.deleteBatchIds(ids);
    }

    @Override
    public Device findDeviceById(Long id) {
        return deviceMapper.selectById(id);
    }

    @Override
    public Integer updateInspectDevice(Device inspectDevice) {
        return deviceMapper.updateById(inspectDevice);
    }

    @Override
    public PageResult<Device> findPage(DeviceQueryVo inspectDevicePageReqVO) {
        return deviceMapper.findPage(inspectDevicePageReqVO);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void importExcelData(InputStream inputStream) {
        EasyExcel.read(inputStream, DeviceExcelVo.class,new DeviceListener(deviceMapper,workshopMapper,processMapper,sysDeptMapper)).sheet().doRead();
    }

    @Override
    public Device findDeviceByName(String deviceName) {
        LambdaQueryWrapperX<Device> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Device::getDeviceName,deviceName);
        return deviceMapper.selectOne(queryWrapperX);
    }

    @Override
    public Device findDeviceByCode(String code) {
        LambdaQueryWrapperX<Device> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Device::getDeviceCode,code);
        return deviceMapper.selectOne(queryWrapperX);
    }

    @Override
    public List<Device> listAll() {
        return deviceMapper.selectList();
    }


}
