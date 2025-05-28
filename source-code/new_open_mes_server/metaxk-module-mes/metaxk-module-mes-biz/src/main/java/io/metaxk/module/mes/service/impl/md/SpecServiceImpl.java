package io.metaxk.module.mes.service.impl.md;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.md.vo.NormQueryVo;
import io.metaxk.module.mes.controller.admin.md.vo.SpecExportVo;
import io.metaxk.module.mes.dal.dataobject.md.Spec;
import io.metaxk.module.mes.dal.mysql.md.SpecMapper;
import io.metaxk.module.mes.service.md.SpecService;
import io.metaxk.module.mes.utils.BeanCopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * 规格ServiceImpl
 * @author 万界星空
 */
@Service
public class SpecServiceImpl implements SpecService {

    @Resource
    private SpecMapper specMapper;


    @Override
    public Integer saveSpec(Spec spec) {
        spec.setCreateTime(new Date());
        return specMapper.insert(spec);
    }



    @Override
    public Integer removeSpecByIds(List<Long> ids) {
        return specMapper.deleteBatchIds(ids);
    }


    @Override
    public Integer updateSpec(Spec norm) {
        return specMapper.updateById(norm);
    }


    @Override
    public Spec findSpecById(Long id) {
        return specMapper.selectById(id);
    }


    @Override
    public PageResult<Spec> findPage(NormQueryVo normQueryVo) {
        return specMapper.findPage(normQueryVo);
    }


    @Override
    public Spec findSpecByName(String name) {
        return specMapper.selectOne(new LambdaQueryWrapperX<Spec>().eq(Spec::getName,name));
    }

    @Override
    public List<Spec> listAll() {
        return specMapper.selectList();
    }

    @Override
    public List<SpecExportVo> listData() {
        return BeanCopyUtil.copyListProperties(this.listAll(), SpecExportVo::new);
    }


}
