package io.metaxk.module.mes.service.impl.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.order.vo.QuoteExportVo;
import io.metaxk.module.mes.controller.admin.order.vo.ReturnExcelVo;
import io.metaxk.module.mes.controller.admin.order.vo.ReturnsQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.Client;
import io.metaxk.module.mes.dal.dataobject.order.Quote;
import io.metaxk.module.mes.dal.dataobject.order.Returns;
import io.metaxk.module.mes.dal.dataobject.order.ReturnsItem;
import io.metaxk.module.mes.dal.mysql.md.ClientMapper;
import io.metaxk.module.mes.dal.mysql.order.ReturnsItemMapper;
import io.metaxk.module.mes.dal.mysql.order.ReturnsMapper;
import io.metaxk.module.mes.service.md.ClientService;
import io.metaxk.module.mes.service.order.ReturnsService;
import io.metaxk.module.mes.utils.AutoCodeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 万界星空
 * @time 2023/7/18 16:41
 */
@Service
public class ReturnsServiceImpl implements ReturnsService {

    @Resource
    private ReturnsMapper returnsMapper;

    @Resource
    private AutoCodeUtil autoCodeUtil;

    @Resource
    private ReturnsItemMapper returnsItemMapper;

    @Resource
    private ClientService clientService;

    @Override
    public Integer saveReturns(Returns returns) {
        BigDecimal totalPriceSum = BigDecimal.ZERO , totalWeightSum = BigDecimal.ZERO;
        for(ReturnsItem returnsItem: returns.getReturnsItemList()){
            ReturnsItem item = new ReturnsItem();
            BeanUtils.copyProperties(returnsItem,item);
            item.setTotalPrice(returnsItem.getWeight().multiply(returnsItem.getPrice()))
                              .setReturnNumber(returns.getNumber());
            returnsItemMapper.insert(item);

            totalPriceSum = totalPriceSum.add(item.getTotalPrice());
            totalWeightSum = totalWeightSum.add(item.getWeight());
        }
        returns.setTotalPrice(totalPriceSum).setWeight(totalWeightSum);
        Client client = clientService.findClientByCode(returns.getCustomerName());
        returns.setCustomerName(client.getClientName());
        return returnsMapper.insert(returns);
    }



    @Override
    public Returns findReturnById(Long id) {
        Returns returns = returnsMapper.selectById(id);
        List<ReturnsItem> returnsItemList = returnsItemMapper.selectReturnsItemByReturnNumber(returns.getNumber());
        returns.setReturnsItemList(returnsItemList);
        return returns;
    }

    @Override
    public Integer removeReturns(List<Long> ids) {
        return returnsMapper.deleteBatchIds(ids);
    }




    @Override
    public Integer updateReturns(Returns returns) {
        BigDecimal totalPriceSum = BigDecimal.ZERO,  totalWeightSum = BigDecimal.ZERO;
        Returns returnsDo = returnsMapper.selectById(returns.getId());
        returnsItemMapper.delete(new LambdaQueryWrapperX<ReturnsItem>().eq(ReturnsItem::getReturnNumber, returnsDo.getNumber()));

        for (ReturnsItem returnsItem : returns.getReturnsItemList()) {
            //修改的时候数组先执行删除方法在添加
            ReturnsItem item = new ReturnsItem();
            BeanUtils.copyProperties(returnsItem, item);
            item.setTotalPrice(returnsItem.getWeight().multiply(returnsItem.getPrice())).setReturnNumber(returns.getNumber());

            totalPriceSum = totalPriceSum.add(item.getTotalPrice());
            totalWeightSum = totalWeightSum.add(item.getWeight());
            returnsItemMapper.insert(item);
        }

        returns.setTotalPrice(totalPriceSum).setWeight(totalWeightSum);
        Client client = clientService.findClientByCode(returns.getCustomerName());
        returns.setCustomerName(client.getClientName());
        return returnsMapper.updateById(returns);
    }




    @Override
    public PageResult<Returns> findPage(ReturnsQueryVo returnsQueryVo) {
        return returnsMapper.findPage(returnsQueryVo);
    }

    @Override
    public List<ReturnExcelVo> exportData() {
        List<Returns> returnsList = returnsMapper.selectList();
        ArrayList<ReturnExcelVo> returnExcelVo = new ArrayList<>(returnsList.size());
        returnsList.forEach(dict -> {
            ReturnExcelVo excelDictDTO = new ReturnExcelVo();
            BeanUtils.copyProperties(dict, excelDictDTO);
            returnExcelVo.add(excelDictDTO);
        });
        return returnExcelVo;
    }


}
