package io.metaxk.module.mes.service.qc;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.qc.vo.WayQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.Way;

import java.io.InputStream;
import java.util.List;


/**
 * 检测方式
 * @author 万界星空
 * @time 2023/7/6 10:54
 */
public interface WayService {

    /**
     *  新增检测方式
     * @param inspectMethod
     * @return Integer
     */
    Integer saveInspectMethod(Way inspectMethod);

    /**
     * 根据id删除
     * @param ids
     * @return Integer
     */
    Integer removeInspectMethod(List<Long> ids);

    /**
     * 根据id查询检测方式详情
     * @param id
     * @return Way
     */
    Way findMethodById(Long id);

    /**
     *  修改检测方式
     * @param inspectMethod
     * @return  Integer
     */
    Integer updateInspectMethod(Way inspectMethod);

    PageResult<Way> findPage(WayQueryVo inspectMethodPageReqVO);


    void importExcelData(InputStream inputStream);

    Way findMethodByName(String inspectName);
}
