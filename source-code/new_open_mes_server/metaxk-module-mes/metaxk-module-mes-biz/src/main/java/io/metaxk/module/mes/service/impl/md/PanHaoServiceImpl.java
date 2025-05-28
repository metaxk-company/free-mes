package io.metaxk.module.mes.service.impl.md;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.md.vo.PanHaoExportVo;
import io.metaxk.module.mes.controller.admin.md.vo.PanHaoQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.PanHao;
import io.metaxk.module.mes.dal.mysql.md.PanHaoMapper;
import io.metaxk.module.mes.service.md.PanHaoService;
import io.metaxk.module.mes.utils.BeanCopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 盘号ServiceImpl
 *
 * @author 万界星空MES
 */
@Service
public class PanHaoServiceImpl implements PanHaoService {

    @Resource
    private PanHaoMapper panHaoMapper;


    @Override
    public Integer savePanHao(PanHao panHao) {
        return panHaoMapper.insert(panHao);
    }

    @Override
    public Integer removePanHaoByIds(List<Long> ids) {
        return panHaoMapper.deleteBatchIds(ids);
    }

    @Override
    public Integer updatePanHao(PanHao panHao) {
        return panHaoMapper.updateById(panHao);
    }


    @Override
    public PageResult<PanHao> findPage(PanHaoQueryVo panHaoQueryVo) {
        return panHaoMapper.findPage(panHaoQueryVo);
    }

    @Override
    public PanHao findPanHaoByNum(String number) {
        return panHaoMapper.selectOne(new LambdaQueryWrapperX<PanHao>().eq(PanHao::getNumber,number));
    }

    @Override
    public List<PanHao> listAll() {
        return panHaoMapper.selectList();
    }

    @Override
    public List<PanHaoExportVo> listData() {
        return BeanCopyUtil.copyListProperties(this.listAll(), PanHaoExportVo::new);
    }

}
