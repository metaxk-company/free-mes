package io.metaxk.module.mes.service.impl.md;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.md.vo.ModelExportVo;
import io.metaxk.module.mes.controller.admin.md.vo.ModelQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.Model;
import io.metaxk.module.mes.dal.mysql.md.ModelMapper;
import io.metaxk.module.mes.service.md.ModelService;
import io.metaxk.module.mes.utils.BeanCopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;



/**
 * 型号ServiceImpl
 * @author 万界星空
 */
@Service
public class ModelServiceImpl implements ModelService {

    @Resource
    private ModelMapper modelMapper;

    @Override
    public Integer saveModel(Model model) {
        return modelMapper.insert(model);
    }

    @Override
    public Integer removeModelByIds(List<Long> ids) {
        return modelMapper.deleteBatchIds(ids);
    }

    @Override
    public Integer updateModel(Model model) {
        return modelMapper.updateById(model);
    }

    @Override
    public Model findModelById(Long id) {
        return modelMapper.selectById(id);
    }

    @Override
    public PageResult<Model> findPage(ModelQueryVo modelQueryVo) {
        return modelMapper.findPage(modelQueryVo);
    }

    @Override
    public Model findModelByName(String name) {
        return modelMapper.selectOne(new LambdaQueryWrapperX<Model>().eq(Model::getName,name));
    }

    @Override
    public List<Model> listAll() {
        return modelMapper.selectList();
    }

    @Override
    public List<ModelExportVo> listData() {
        return BeanCopyUtil.copyListProperties(this.listAll(), ModelExportVo::new);
    }
}
