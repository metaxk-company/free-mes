package io.metaxk.module.system.convert.tenant;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.system.controller.admin.tenant.vo.packages.TenantPackageCreateReqVO;
import io.metaxk.module.system.controller.admin.tenant.vo.packages.TenantPackageRespVO;
import io.metaxk.module.system.controller.admin.tenant.vo.packages.TenantPackageSimpleRespVO;
import io.metaxk.module.system.controller.admin.tenant.vo.packages.TenantPackageUpdateReqVO;
import io.metaxk.module.system.dal.dataobject.tenant.TenantPackageDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 租户套餐 Convert
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Mapper
public interface TenantPackageConvert {

    TenantPackageConvert INSTANCE = Mappers.getMapper(TenantPackageConvert.class);

    TenantPackageDO convert(TenantPackageCreateReqVO bean);

    TenantPackageDO convert(TenantPackageUpdateReqVO bean);

    TenantPackageRespVO convert(TenantPackageDO bean);

    List<TenantPackageRespVO> convertList(List<TenantPackageDO> list);

    PageResult<TenantPackageRespVO> convertPage(PageResult<TenantPackageDO> page);

    List<TenantPackageSimpleRespVO> convertList02(List<TenantPackageDO> list);

}
