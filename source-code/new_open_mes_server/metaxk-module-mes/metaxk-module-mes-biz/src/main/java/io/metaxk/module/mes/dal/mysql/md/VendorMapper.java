package io.metaxk.module.mes.dal.mysql.md;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.module.mes.controller.admin.md.vo.VendorQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.Vendor;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * 供应商 Mapper
 * @author 万界星空
 */
@Mapper
public interface VendorMapper extends BaseMapperX<Vendor> {


    /**
     * 供应商条件分页查询
     * @param reqVO
     * @return PageResult<Vendor>
     */
    default PageResult<Vendor> selectPage(VendorQueryVo reqVO) {
        LambdaQueryWrapperX<Vendor> queryWrapper = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotNull(reqVO.getVendorCode())){
            if(StringUtils.isNotBlank(reqVO.getVendorCode())){
            queryWrapper.like(Vendor::getVendorCode,reqVO.getVendorCode());
            }

            if(StringUtils.isNotBlank(reqVO.getVendorName())){
                queryWrapper.like(Vendor::getVendorName,reqVO.getVendorName());
            }
            if(StringUtils.isNotBlank(reqVO.getVendorNick())){
                queryWrapper.like(Vendor::getVendorNick,reqVO.getVendorNick());
            }
            if(StringUtils.isNotBlank(reqVO.getVendorEn())){
                queryWrapper.like(Vendor::getVendorEn,reqVO.getVendorEn());
            }
            if(StringUtils.isNotBlank(reqVO.getEnableFlag())){
                queryWrapper.eq(Vendor::getEnableFlag,reqVO.getEnableFlag());
            }

            if(StringUtils.isNotBlank(reqVO.getTel())){
                queryWrapper.like(Vendor::getTel,reqVO.getTel());
            }
            if(StringUtils.isNotBlank(reqVO.getVendorLevel())){
                queryWrapper.like(Vendor::getVendorLevel,reqVO.getVendorLevel());
            }

            if(StringUtils.isBlank(reqVO.getVendorCode()) && StringUtils.isBlank(reqVO.getVendorNick()) && StringUtils.isBlank(reqVO.getVendorName())
            && StringUtils.isBlank(reqVO.getVendorEn()) && StringUtils.isBlank(reqVO.getEnableFlag()) && StringUtils.isBlank(reqVO.getTel()) && StringUtils.isBlank(reqVO.getVendorLevel())){
                queryWrapper.isNotNull(Vendor::getId);
            }
        }
            return selectPage(reqVO,queryWrapper);
    }



}
