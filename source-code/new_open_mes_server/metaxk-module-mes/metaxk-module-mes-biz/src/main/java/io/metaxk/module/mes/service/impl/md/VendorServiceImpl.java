package io.metaxk.module.mes.service.impl.md;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.md.vo.VendorExcelVo;
import io.metaxk.module.mes.controller.admin.md.vo.VendorQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.Vendor;
import io.metaxk.module.mes.dal.mysql.md.VendorMapper;
import io.metaxk.module.mes.service.md.VendorService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;


/**
 * 供应商 Service 实现类
 * @author 万界星空MES
 */
@Service
public class VendorServiceImpl implements VendorService {


    @Resource
    private VendorMapper vendorMapper;

    @Override
    public Integer updateVendor(Vendor vendor) {
      vendor.setId(vendor.getId());
      vendor.setUpdateTime(new Date());
      return vendorMapper.updateById(vendor);
    }


    @Override
    public Vendor getVendor(Long id) {
        return vendorMapper.selectById(id);
    }


    @Override
    public Integer insertVendor(Vendor vendor) {
        //同步数据到金蝶
        vendor.setCreateTime(new Date());
        return vendorMapper.insert(vendor);
    }



    @Override
    public Integer removeVendor(List<Long> ids) {
       return vendorMapper.deleteBatchIds(ids);
    }

    @Override
    public PageResult<Vendor> getVendorPage(VendorQueryVo pageVO) {
        return vendorMapper.selectPage(pageVO);
    }


    @Override
    public List<VendorExcelVo> listData() {
        List<Vendor> dictList = vendorMapper.selectList();
        ArrayList<VendorExcelVo> excelDictDTOList = new ArrayList<>(dictList.size());
        dictList.forEach(dict -> {
            VendorExcelVo excelDictDTO = new VendorExcelVo();
            BeanUtils.copyProperties(dict, excelDictDTO);
            if ("Y".equals(excelDictDTO.getEnableFlag())){
                excelDictDTO.setEnableFlag("启用");
            }else if("N".equals(excelDictDTO.getEnableFlag())){
                excelDictDTO.setEnableFlag("禁用");
            }

            if ("A".equals(excelDictDTO.getVendorLevel())){
                excelDictDTO.setVendorLevel("优质供应商");
            }else if("B".equals(excelDictDTO.getVendorLevel())){
                excelDictDTO.setVendorLevel("正常");
            }else if("C".equals(excelDictDTO.getVendorLevel())){
                excelDictDTO.setVendorLevel("重点关注");
            }else if("D".equals(excelDictDTO.getVendorLevel())){
                excelDictDTO.setVendorLevel("劣质供应商");
            }else if("E".equals(excelDictDTO.getVendorLevel())){
                excelDictDTO.setVendorLevel("黑名单");
            }


            excelDictDTOList.add(excelDictDTO);
        });
        return excelDictDTOList;
    }

    @Override
    public Vendor findVendorByCode(String vendorCode) {
        LambdaQueryWrapperX<Vendor> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Vendor::getVendorCode,vendorCode);
        return vendorMapper.selectOne(queryWrapperX);
    }

    @Override
    public Vendor findVendorByName(String vendorName) {
        LambdaQueryWrapperX<Vendor> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Vendor::getVendorName,vendorName);
        return vendorMapper.selectOne(queryWrapperX);
    }

    @Override
    public List<Vendor> listAll() {
        return vendorMapper.selectList();
    }

}
