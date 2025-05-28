package io.metaxk.module.mes.service.impl.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.order.vo.SemiLabelAllExportVo;
import io.metaxk.module.mes.controller.admin.order.vo.SemiLabelExportVo;
import io.metaxk.module.mes.controller.admin.order.vo.SemiLabelQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.Item;
import io.metaxk.module.mes.dal.dataobject.order.SemiLabel;
import io.metaxk.module.mes.dal.dataobject.order.SemiLabelItem;
import io.metaxk.module.mes.dal.mysql.md.ItemMapper;
import io.metaxk.module.mes.dal.mysql.order.SemiLabelItemMapper;
import io.metaxk.module.mes.dal.mysql.order.SemiLabelMapper;
import io.metaxk.module.mes.service.order.SemiLabelService;
import io.metaxk.module.mes.utils.BeanCopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * @author 万界星空
 * @time 2023/8/9 17:06
 */
@Service
public class SemiLabelServiceImpl implements SemiLabelService {

    @Resource
    private SemiLabelMapper semiLabelMapper;

    @Resource
    private SemiLabelItemMapper semiLabelItemMapper;

    @Resource
    private ItemMapper itemMapper;






    @Override
    public PageResult<SemiLabel> findPage(SemiLabelQueryVo semiLabelQueryVo) {
        return semiLabelMapper.findPage(semiLabelQueryVo);
    }

    @Override
    public Integer saveSemiLabel(SemiLabel semiLabel) {
        //状态0：待入库1：已入库2：已出库默认为0
        semiLabel.setStatus("0");
        semiLabel.setCreateTime(new Date());
        List<SemiLabelItem> semiLabelItemList = semiLabel.getSemiLabelItemList();
        for (SemiLabelItem semiLabelItem:semiLabelItemList){
            semiLabelItem.setSemiNumber(semiLabel.getNumber());
            //新加---需要产品的编号，分类型号线别以及单位信息
            LambdaQueryWrapperX<Item> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(Item::getModel,semiLabelItem.getModel());
            queryWrapperX.eq(Item::getSpec,semiLabelItem.getSpec());
            queryWrapperX.eq(Item::getLineType,semiLabelItem.getLineType());
            Item item = itemMapper.selectOne(queryWrapperX);
            semiLabelItem.setItemCode(item.getItemCode()).setItemName(item.getItemName()).setItemType(item.getItemTypeName()).setUnitOfMeasure(item.getUnitOfMeasure());
            semiLabelItemMapper.insert(semiLabelItem);
        }
        return semiLabelMapper.insert(semiLabel);
    }

    @Override
    public Integer updateSemiLabel(SemiLabel semiLabel) {
        SemiLabel semiLabelDo = semiLabelMapper.selectById(semiLabel.getId());
        semiLabelItemMapper.delete(new LambdaQueryWrapperX<SemiLabelItem>().eq(SemiLabelItem::getSemiNumber, semiLabelDo.getNumber()));

        List<SemiLabelItem> semiLabelItemList = semiLabel.getSemiLabelItemList();
        for (SemiLabelItem semiLabelItem:semiLabelItemList){
            semiLabelItem.setSemiNumber(semiLabel.getNumber());
            semiLabelItemMapper.insert(semiLabelItem);
        }
        semiLabel.setUpdateTime(new Date());
        return semiLabelMapper.updateById(semiLabel);
    }

    @Override
    public Integer removeSemiLabelByIds(List<Long> ids) {
        return semiLabelMapper.deleteBatchIds(ids);
    }

    @Override
    public SemiLabel findSemiLabelById(Long id) {
        SemiLabel semiLabel = semiLabelMapper.selectById(id);
        List<SemiLabelItem> semiLabelItemList = semiLabelItemMapper.selectSemiLabelItemByNumber(semiLabel.getNumber());
        semiLabel.setSemiLabelItemList(semiLabelItemList);
        return semiLabel;
    }

    @Override
    public List<SemiLabel> listAll() {
        return semiLabelMapper.selectList();
    }

    @Override
    public List<SemiLabelExportVo> listData() {
        return BeanCopyUtil.copyListProperties(this.listAll(), SemiLabelExportVo::new);
    }

    @Override
    public List<SemiLabelAllExportVo> listAllDataByIds(List<Integer> ids) {
        return semiLabelMapper.exportAllByIds(ids);
    }
}
