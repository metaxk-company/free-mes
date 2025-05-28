package io.metaxk.module.mes.service.md;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.md.vo.ColorExportVo;
import io.metaxk.module.mes.controller.admin.md.vo.ColorQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.Color;

import java.util.List;

/**
 * 颜色Service
 * @author 万界星空MES
 */
public interface ColorService {

    /**
     * 新增颜色
     * @param color
     * @return Integer
     */
    Integer saveColor(Color color);

    /**
     * 批量删除颜色
     * @param ids
     * @return Integer
     */
    Integer removeColorByIds(List<Long> ids);

    /**
     * 修改颜色
     * @param color
     * @return Integer
     */
    Integer updateColor(Color color);

    /**
     * 根据id查询颜色详情
     * @param id
     * @return Color
     */
    Color findColorById(Long id);

    /**
     * 颜色条件分页查询
     * @param colorQueryVo
     * @return PageResult<Color>
     */
    PageResult<Color> findPage(ColorQueryVo colorQueryVo);

    /**
     * 根据名称查询颜色
     * @param name
     * @return Color
     */
    Color findColorByName(String name);

    List<Color> listAll();

    List<ColorExportVo> listData();
}
