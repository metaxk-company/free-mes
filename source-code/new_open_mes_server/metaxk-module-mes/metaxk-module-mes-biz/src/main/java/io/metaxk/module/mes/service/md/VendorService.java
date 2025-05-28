package io.metaxk.module.mes.service.md;

import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.md.vo.VendorExcelVo;
import io.metaxk.module.mes.controller.admin.md.vo.VendorQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.Vendor;

/**
 * 供应商 Service 接口
 * @author 万界星空
 */
public interface VendorService {

     /**
      * 新增供应商
      * @param createReqVO
      * @return  Integer
      */
     Integer insertVendor(Vendor createReqVO);


     /**
      * 修改供应商
      * @param updateReqVO
      * @return Integer
      */
     Integer updateVendor( Vendor updateReqVO);


     /**
      * 删除供应商
      * @param ids
      * @return Integer
      */
     Integer removeVendor(List<Long> ids);


     /**
      * 根据id查询供应商
      * @param id
      * @return Vendor
      */
     Vendor getVendor(Long id);


     /**
      * 供应商条件分页查询
      * @param pageVO
      * @return PageResult<Vendor>
      */
     PageResult<Vendor> getVendorPage(VendorQueryVo pageVO);

     /**
      * 导出供应商
      * @return List<VendorExcelVO>
      */
     List<VendorExcelVo> listData();

     /**
      * 根据供应商编号查询供应商
      * @param vendorCode
      * @return Vendor
      */
     Vendor findVendorByCode(String vendorCode);

     /**
      * 根据供应商名称查询供应商
      * @param vendorName
      * @return Vendor
      */
     Vendor findVendorByName(String vendorName);

     /**
      * 查询所有供应商信息
      * @return
      */
     List<Vendor> listAll();
}
