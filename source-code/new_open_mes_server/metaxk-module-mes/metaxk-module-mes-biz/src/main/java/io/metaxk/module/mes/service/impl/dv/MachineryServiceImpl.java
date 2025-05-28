package io.metaxk.module.mes.service.impl.dv;


import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.dv.vo.MachineryExcelVo;
import io.metaxk.module.mes.controller.admin.dv.vo.MachineryQueryVo;
import io.metaxk.module.mes.dal.dataobject.dv.Machinery;
import io.metaxk.module.mes.dal.dataobject.dv.MachineryType;
import io.metaxk.module.mes.dal.dataobject.md.WorkShop;
import io.metaxk.module.mes.dal.mysql.dv.MachineryMapper;
import io.metaxk.module.mes.dal.mysql.dv.MachineryTypeMapper;
import io.metaxk.module.mes.dal.mysql.md.WorkShopMapper;
import io.metaxk.module.mes.service.dv.MachineryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.WORKSHOP_NO_EXIST_DELTED;


/**
 * 设备 Service 实现类
 * @author 万界星空MES
 */
@Service
public class MachineryServiceImpl implements MachineryService {

    @Resource
    private MachineryMapper machineryMapper;

    @Resource
    private MachineryTypeMapper machineryTypeMapper;

    @Resource
    private WorkShopMapper workshopMapper;

    @Override
    public Integer createMachinery(Machinery machinery) {
        MachineryType machineryType = machineryTypeMapper.selectByTypeId(machinery.getMachineryTypeId());
        machinery.setMachineryTypeCode(machineryType.getMachineryTypeCode());
        machinery.setMachineryTypeName(machineryType.getMachineryTypeName());
        WorkShop workshopDO = workshopMapper.selectById(machinery.getWorkshopId());
        machinery.setWorkshopCode(workshopDO.getWorkshopCode());
        machinery.setWorkshopName(workshopDO.getWorkshopName());

        machinery.setCreateTime(new Date());
        return   machineryMapper.insert(machinery);

    }

    @Override
    public Integer updateMachinery(Machinery machinery) {
        //修改所属分类
        MachineryType machineryType = machineryTypeMapper.selectByTypeId(machinery.getMachineryTypeId());
        machinery.setMachineryTypeCode(machineryType.getMachineryTypeCode());
        machinery.setMachineryTypeName(machineryType.getMachineryTypeName());
        //修改所属车间
        WorkShop workshopDO = workshopMapper.selectByShopId(machinery.getWorkshopId());
        if(workshopDO == null){
            throw exception(WORKSHOP_NO_EXIST_DELTED);
        }
        machinery.setWorkshopCode(workshopDO.getWorkshopCode());
        machinery.setWorkshopName(workshopDO.getWorkshopName());
        //设置开始时间与修改时间
        Machinery machineryDO = machineryMapper.selectById(machinery.getId());
        machinery.setCreateTime(machineryDO.getCreateTime());
        machinery.setUpdateTime(new Date());
        return  machineryMapper.updateById(machinery);
    }








    @Override
    public Integer deleteMachinery(List<Long> ids) {
      return  machineryMapper.deleteBatchIds(ids);
    }


    @Override
    public Machinery getMachinery(Long id) {
        return machineryMapper.selectById(id);
    }



    @Override
    public PageResult<Machinery> getMachineryPage(MachineryQueryVo page) {
        return machineryMapper.selectPage(page);
    }



    @Override
    public List<MachineryExcelVo> listData() {
        List<Machinery> dictList = machineryMapper.selectList();
        ArrayList<MachineryExcelVo> excelDictDTOList = new ArrayList<>(dictList.size());
        dictList.forEach(dict -> {
            MachineryExcelVo excelDictDTO = new MachineryExcelVo();
            BeanUtils.copyProperties(dict, excelDictDTO);
            excelDictDTOList.add(excelDictDTO);
        });
        return excelDictDTOList;
    }



    @Override
    public List<Machinery> selectByMachineryTypeId(Long machineryTypeIds) {
        LambdaQueryWrapperX<Machinery> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(Machinery::getMachineryTypeId,machineryTypeIds);
        return machineryMapper.selectList(queryWrapper);
    }

    @Override
    public List<Machinery> findMachineryStatus() {
        return machineryMapper.selectList();
    }

    @Override
    public Long findOnLineMachinery() {
        LambdaQueryWrapperX<Machinery> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Machinery::getStatus,"在线");
        return machineryMapper.selectCount(queryWrapperX);
    }

    @Override
    public Long findOfflineMachinery() {
        LambdaQueryWrapperX<Machinery> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Machinery::getStatus,"离线");
        return machineryMapper.selectCount(queryWrapperX);
    }

    @Override
    public Long findFaultMachinery() {
        LambdaQueryWrapperX<Machinery> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Machinery::getStatus,"故障");
        return machineryMapper.selectCount(queryWrapperX);
    }

    @Override
    public Machinery findByCodeAndName(String machineryCode, String machineryName) {
        LambdaQueryWrapperX<Machinery> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Machinery::getMachineryCode,machineryCode);
        queryWrapperX.eq(Machinery::getMachineryName,machineryName);
        return machineryMapper.selectOne(queryWrapperX);
    }

    @Override
    public Machinery findMachineryByCode(String machineryCode) {
        LambdaQueryWrapperX<Machinery> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Machinery::getMachineryCode,machineryCode);
        return machineryMapper.selectOne(queryWrapperX);
    }

    @Override
    public List<Machinery> findMachineryByIds(List<Long> ids) {
        return machineryMapper.selectBatchIds(ids);
    }


}
