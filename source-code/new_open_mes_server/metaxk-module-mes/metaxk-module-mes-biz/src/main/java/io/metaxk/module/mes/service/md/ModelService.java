package io.metaxk.module.mes.service.md;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.md.vo.ModelExportVo;
import io.metaxk.module.mes.controller.admin.md.vo.ModelQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.Model;

import java.util.List;

/**
 * 型号Service
 * @author 万界星空
 */
public interface ModelService {

    /**
     * 新增型号
     * @param model
     * @return Integer
     */
    Integer saveModel(Model model);

    /**
     * 批量删除型号
     * @param ids
     * @return Integer
     */
    Integer removeModelByIds(List<Long> ids);

    /**
     * 修改型号
     * @param model
     * @return Integer
     */
    Integer updateModel(Model model);

    /**
     * 根据id查询型号详情
     * @param id
     * @return Model
     */
    Model findModelById(Long id);

    /**
     * 型号条件分页查询
     * @param modelQueryVo
     * @return PageResult<Model>
     */
    PageResult<Model> findPage(ModelQueryVo modelQueryVo);

    /**
     * 根据名称查询型号
     * @param name
     * @return Model
     */
    Model findModelByName(String name);


    List<Model> listAll();

    List<ModelExportVo> listData();
}
