package io.metaxk.module.mes.service.dv;


import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.dv.vo.MachineryExcelVo;
import io.metaxk.module.mes.controller.admin.dv.vo.MachineryQueryVo;
import io.metaxk.module.mes.dal.dataobject.dv.Machinery;

/**
 * 设备 Service 接口
 * @author 万界星空
 */
public interface MachineryService {


    /**
     * 新增设备台账
     * @param machinery
     * @return Integer
     */
    Integer createMachinery( Machinery machinery);

    /**
     * 修改设备台账
     * @param machinery
     * @return Integer
     */
    Integer updateMachinery( Machinery machinery);

    /**
     * 删除设备台账
     * @param ids
     * @return Integer
     */
  Integer deleteMachinery(List<Long> ids);


    /**
     * 根据id查询设备台账
     * @param id
     * @return Machinery
     */
    Machinery getMachinery(Long id);



    /**
     * 设备台账条件分页查询
     * @param page
     * @return  PageResult<Machinery>
     */
    PageResult<Machinery> getMachineryPage(MachineryQueryVo page);

    /**
     * 设备台账导出
     * @return List<MachineryExcelVO>
     */
    List<MachineryExcelVo> listData();

    /**
     * 根据设备类型id查询设备台账
     * @param machineryTypeIds
     * @return  List<Machinery>
     */
    List<Machinery> selectByMachineryTypeId(Long machineryTypeIds);


    /**
     * 查询设备状态
     * @return List<Machinery>
     */
    List<Machinery> findMachineryStatus();

    /**
     * 查询在线设备
     * @return Long
     */
    Long findOnLineMachinery();

    /**
     * 查询离线设备
     * @return Long
     */
    Long findOfflineMachinery();

    /**
     * 查询故障设备
     * @return Long
     */
    Long findFaultMachinery();

    /**
     * 根据设备编号，名称查询设备台账
     * @param machineryCode
     * @param machineryName
     * @return Machinery
     */
    Machinery findByCodeAndName(String machineryCode, String machineryName);

    /**
     * 根据设备编号查询设备台账
     * @param machineryCode
     * @return Machinery
     */
    Machinery findMachineryByCode(String machineryCode);

    List<Machinery> findMachineryByIds(List<Long> ids);
}
