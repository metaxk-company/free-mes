package io.metaxk.module.mes.service.impl.order;

import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.dal.dataobject.order.OutboundItemLabel;
import io.metaxk.module.mes.dal.mysql.order.OutboundItemLabelMapper;
import io.metaxk.module.mes.service.order.OutboundItemLabelService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;



/**
 * @author 万界星空
 * @time 2023/7/31 14:12
 */
@Service
public class OutboundItemLabelServiceImpl implements OutboundItemLabelService {

    @Resource
    private OutboundItemLabelMapper outboundItemLabelMapper;


    @Override
    public List<OutboundItemLabel> findOutboundItemLabelList(Long id) {
        return outboundItemLabelMapper.findOutboundItemLabelList(id);
    }

    @Override
    public Integer removeByNum(String number) {
        return outboundItemLabelMapper.delete(new LambdaQueryWrapperX<OutboundItemLabel>().eq(OutboundItemLabel::getOutboundNumber,number));
    }

    @Override
    public Integer removeItemLabel(Long id, String number) {
     return  outboundItemLabelMapper.delete(new LambdaQueryWrapperX<OutboundItemLabel>().eq(OutboundItemLabel::getOutboundNumber, number).eq(OutboundItemLabel::getId, id));
    }

    @Override
    public OutboundItemLabel findByIdAndNumber(Long id, String number) {
        LambdaQueryWrapperX<OutboundItemLabel> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OutboundItemLabel::getId,id);
        queryWrapperX.eq(OutboundItemLabel::getOutboundNumber,number);
        return outboundItemLabelMapper.selectOne(queryWrapperX);
    }

    @Override
    public List<OutboundItemLabel> findOutboundItemLabeByNum(String saleItemNumber) {
        LambdaQueryWrapperX<OutboundItemLabel> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OutboundItemLabel::getSaleItemNumber,saleItemNumber);
        return outboundItemLabelMapper.selectList(queryWrapperX);
    }

    @Override
    public Integer removeBySaleNum(String saleItemNumber) {
        LambdaQueryWrapperX<OutboundItemLabel> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OutboundItemLabel::getSaleItemNumber,saleItemNumber);
        return outboundItemLabelMapper.delete(queryWrapperX);
    }

    @Override
    public OutboundItemLabel getOutboundItemLabel(OutboundItemLabel outboundItemLabel) {
        LambdaQueryWrapperX<OutboundItemLabel> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OutboundItemLabel::getBoxNumber,outboundItemLabel.getBoxNumber());
        queryWrapperX.eq(OutboundItemLabel::getStatus,outboundItemLabel.getStatus());
        return outboundItemLabelMapper.selectOne(queryWrapperX);
    }

    @Override
    public Integer removeByOutItemNum(String number) {
        LambdaQueryWrapperX<OutboundItemLabel> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OutboundItemLabel::getOutboundItemNumber,number);
        return outboundItemLabelMapper.delete(queryWrapperX);
    }

    @Override
    public Integer removeByOutboundNum(String number) {
        LambdaQueryWrapperX<OutboundItemLabel> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OutboundItemLabel::getOutboundNumber,number);
        return outboundItemLabelMapper.delete(queryWrapperX);
    }

    @Override
    public List<OutboundItemLabel> findOutboundItemLabeByOutNum(String number) {
        LambdaQueryWrapperX<OutboundItemLabel> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(OutboundItemLabel::getOutboundNumber,number);
        return outboundItemLabelMapper.selectList(queryWrapperX);
    }

    @Override
    public OutboundItemLabel findItemLabeByBarCode(String barCode) {
        return outboundItemLabelMapper.selectOne(new LambdaQueryWrapperX<OutboundItemLabel>().eq(OutboundItemLabel::getBarCode,barCode));
    }

    @Override
    public  List<OutboundItemLabel> findItemLabeByPanHao(String panhao) {
        return outboundItemLabelMapper.selectList(new LambdaQueryWrapperX<OutboundItemLabel>().eq(OutboundItemLabel::getPalletNumber,panhao));
    }

    @Override
    public List<OutboundItemLabel> findItemLabeListByBarCode(String barCode) {
        return outboundItemLabelMapper.selectList(new LambdaQueryWrapperX<OutboundItemLabel>().eq(OutboundItemLabel::getBarCode,barCode));
    }

    @Override
    public Integer removeOutItemByCode(String barCode) {
        return outboundItemLabelMapper.delete(new LambdaQueryWrapperX<OutboundItemLabel>().eq(OutboundItemLabel::getBarCode,barCode));
    }

    @Override
    public Integer removeOutItemByPanHao(String panhao) {
        return outboundItemLabelMapper.delete(new LambdaQueryWrapperX<OutboundItemLabel>().eq(OutboundItemLabel::getPalletNumber,panhao));
    }


}
