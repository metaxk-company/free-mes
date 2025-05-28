package io.metaxk.module.mes.service.pro;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.pro.vo.TemporaryWorkHoursExcelVo;
import io.metaxk.module.mes.controller.admin.pro.vo.TemporaryWorkHoursVo;
import io.metaxk.module.mes.dal.dataobject.pro.TemporaryWorkHours;
import java.util.List;

/**
 * 临时工时 Service 接口
 * @author 万界星空
 */
public interface TemporaryWorkHoursService {


    /**
     * 临时工时条件分页查询
     * @param temporaryWorkHoursVO
     * @return PageResult<TemporaryWorkHours>
     */
    PageResult<TemporaryWorkHours> list(TemporaryWorkHoursVo temporaryWorkHoursVO);

    /**
     * 新增临时工时
     * @param temporaryWorkHours
     * @return Integer
     */
    Integer save(TemporaryWorkHours temporaryWorkHours);


    /**
     * 删除临时工时
     * @param ids
     * @return Integer
     */
    Integer removeByIds(List<Long> ids);


    /**
     * 根据id查询临时工时
     * @param id
     * @return TemporaryWorkHours
     */
    TemporaryWorkHours findTemporaryWorkHoursById(Long id);


    /**
     * 修改临时工时
     * @param temporaryWorkHours
     * @return Integer
     */
    Integer update(TemporaryWorkHours temporaryWorkHours);

    /**
     * 导出临时工时
     * @param ids
     * @return List<TemporaryWorkHoursExcelVO>
     */
    List<TemporaryWorkHoursExcelVo> listData(List<Long> ids);
}
