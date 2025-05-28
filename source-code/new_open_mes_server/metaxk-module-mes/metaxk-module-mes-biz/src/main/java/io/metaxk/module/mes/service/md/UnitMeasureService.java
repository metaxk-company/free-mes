package io.metaxk.module.mes.service.md;

import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.md.vo.UnitMeasureExcelVo;
import io.metaxk.module.mes.controller.admin.md.vo.UnitMeasureQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.UnitMeasure;

/**
 * 单位 Service 接口
 * @author 万界星空
 */
public interface UnitMeasureService {


    /**
     * 新增单位
     * @param measure
     * @return Integer
     */
    Integer createUnitMeasure( UnitMeasure measure);

    /**
     * 修改单位
     * @param measure
     * @return Integer
     */
    Integer updateUnitMeasure( UnitMeasure measure);

    /**
     * 根据id查询单位
     * @param id
     * @return UnitMeasure
     */
    UnitMeasure getUnitMeasure(Long id);


    /**
     * 单位条件分页查询
     * @param pageReqVO
     * @return  PageResult<UnitMeasure>
     */
    PageResult<UnitMeasure> getUnitMeasurePage(UnitMeasureQueryVo pageReqVO);


    /**
     * 根据编号查询单位
     * @param unitCode
     * @return UnitMeasure
     */
    UnitMeasure selectMdUnitConversionNameByCode(String unitCode);

    /**
     * 删除单位
     * @param ids
     * @return Integer
     */
    Integer removeUnitMeasures(List<Long> ids);

    /**
     * 导出单位
     * @return List<UnitMeasureExcelVO>
     */
    List<UnitMeasureExcelVo> listData();

    /**
     * 根据单位编号查询单位
     * @param measureCode
     * @return  List<UnitMeasure>
     */
    List<UnitMeasure> findMeasureNameByMeasureCode(String measureCode);

    /**
     * 单位列表
     * @param
     * @return  List<UnitMeasure>
     */
    List<UnitMeasure> selectMdUnitMeasureList();

    /**
     * 根据单位查询计量单位
     * @param unitOfMeasure
     * @return UnitMeasure
     */
    UnitMeasure selectByUnitOfMeasure(String unitOfMeasure);

    List<UnitMeasure> getEnableList();
}
