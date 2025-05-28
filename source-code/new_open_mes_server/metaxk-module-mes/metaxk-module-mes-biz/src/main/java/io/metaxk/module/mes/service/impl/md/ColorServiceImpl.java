package io.metaxk.module.mes.service.impl.md;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.md.vo.ColorExportVo;
import io.metaxk.module.mes.controller.admin.md.vo.ColorQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.Color;
import io.metaxk.module.mes.dal.mysql.md.ColorMapper;
import io.metaxk.module.mes.service.md.ColorService;
import io.metaxk.module.mes.utils.BeanCopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * 盘号ServiceImpl
 * @author 万界星空
 */
@Service
public class ColorServiceImpl implements ColorService {

    @Resource
    private ColorMapper colorMapper;


    @Override
    public Integer saveColor(Color color) {
        return colorMapper.insert(color);
    }

    @Override
    public Integer removeColorByIds(List<Long> ids) {
        return colorMapper.deleteBatchIds(ids);
    }

    @Override
    public Integer updateColor(Color color) {
        return colorMapper.updateById(color);
    }

    @Override
    public Color findColorById(Long id) {
        return colorMapper.selectById(id);
    }

    @Override
    public PageResult<Color> findPage(ColorQueryVo colorQueryVo) {
        return colorMapper.findPage(colorQueryVo);
    }

    @Override
    public Color findColorByName(String name) {
        return colorMapper.selectOne(new LambdaQueryWrapperX<Color>().eq(Color::getName,name));
    }

    @Override
    public List<Color> listAll() {
        return colorMapper.selectList();
    }

    @Override
    public List<ColorExportVo> listData() {
        return BeanCopyUtil.copyListProperties(this.listAll(), ColorExportVo::new);
    }

}
