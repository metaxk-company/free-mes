package io.metaxk.module.mes.service.impl.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.order.vo.DetailedInventoryResponseVO;
import io.metaxk.module.mes.controller.admin.order.vo.LabelExportVo;
import io.metaxk.module.mes.controller.admin.order.vo.LabelQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.Label;
import io.metaxk.module.mes.dal.dataobject.order.OutboundRecord;
import io.metaxk.module.mes.dal.mysql.order.LabelMapper;
import io.metaxk.module.mes.service.order.LabelService;
import io.metaxk.module.mes.utils.BeanCopyUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;


/**
 * @author 万界星空
 * @time 2023/7/27 15:32
 */
@Service
public class LabelServiceImpl implements LabelService {


    @Resource
    private LabelMapper  labelMapper;


    @Override
    public Integer saveLabel(Label label) {
        return  labelMapper.insert(label) ;
    }



    @Override
    public Integer removeLabelByIds(List<Long> ids) {
        return labelMapper.deleteBatchIds(ids);
    }


    @Override
    public Integer updateLabel(Label label) {
        return labelMapper.updateById(label);
    }


    @Override
    public Label findLabelById(Long id) {
        return labelMapper.selectById(id);
    }


    @Override
    public PageResult<Label> findPage(LabelQueryVo labelVo) {
        return labelMapper.findPage(labelVo);
    }


    @Override
    public Integer updateStatus(Long id, String status) {
        Label label = labelMapper.selectById(id);
        label.setStatus(status);
        return labelMapper.updateById(label);
    }


    @Override
    public List<Label> findLabelByStatus(String status) {
        return labelMapper.findLabelByStatus(status);
    }



    @Override
    public List<Label> listAll() {
        return labelMapper.selectList();
    }


    @Override
    public List<LabelExportVo> listData() {
        return BeanCopyUtil.copyListProperties(this.listAll(), LabelExportVo::new);
    }



    @Override
    public Label findByBarCode(String barCode) {
        return labelMapper.selectOne(new LambdaQueryWrapperX<Label>().eq(Label::getBarCode,barCode));
    }


    @Override
    public  List<Label>  findByPanHao(String panhao) {
        LambdaQueryWrapperX<Label> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Label::getPalletNumber,panhao);
        return labelMapper.selectList(queryWrapperX);
    }

    @Override
    public PageResult<Label> findRepackagedListPage(LabelQueryVo labelVo) {
        return labelMapper.findRepackagedListPage(labelVo);
    }

    @Override
    public OutboundRecord countRepackage() {
        return labelMapper.countRepackage();
    }

    @Override
    public List<Label> findByReelNumber(String panhao) {
        return labelMapper.selectList(new LambdaQueryWrapperX<Label>().eq(Label::getPalletNumber,panhao));
    }

    @Override
    public List<DetailedInventoryResponseVO> detailedInventory(String lineType, String model, String spec) {
        return labelMapper.detailedInventory(lineType, model, spec);
    }

}
