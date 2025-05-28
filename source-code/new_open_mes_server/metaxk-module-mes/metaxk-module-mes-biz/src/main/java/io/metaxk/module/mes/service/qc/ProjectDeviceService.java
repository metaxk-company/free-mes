package io.metaxk.module.mes.service.qc;


import io.metaxk.module.mes.dal.dataobject.qc.ProjectDevice;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/11 21:25
 */
public interface ProjectDeviceService {

    /**
     * 新增检测器具
     * @param projectDevice
     * @return Integer
     */
    Integer saveProjectDevice(ProjectDevice projectDevice);

    /**
     * 根据检测项编号查询检测器具
     * @param projectCode
     * @return List<ProjectDevice>
     */
    List<ProjectDevice> findProjectDeviceByCode(String projectCode);

    /**
     * 根据检测项编号删除检测项中的器具
     * @param projectCode
     * @return Integer
     */
    Integer removeProjectDeviceByCode(String projectCode);


    /**
     * 根据器具编号删除检测项中的器具
     * @param code
     * @return  Integer
     */
    Integer removeDeviceByDeviceCode(String code,String projectCode);


}
