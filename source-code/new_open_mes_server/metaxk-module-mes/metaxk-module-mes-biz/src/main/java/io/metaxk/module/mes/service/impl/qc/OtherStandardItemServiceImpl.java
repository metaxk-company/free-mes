package io.metaxk.module.mes.service.impl.qc;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.dal.dataobject.qc.OtherStandardItem;
import io.metaxk.module.mes.dal.dataobject.qc.ReceiveStandardItem;
import io.metaxk.module.mes.dal.mysql.qc.OtherStandardItemMapper;
import io.metaxk.module.mes.service.qc.OtherStandardItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/8/19 10:28
 */
@Service
public class OtherStandardItemServiceImpl implements OtherStandardItemService {

    @Resource
    private OtherStandardItemMapper otherStandardItemMapper;


    @Override
    public Integer saveOtherStandardItem(OtherStandardItem otherStandardItem) {
        return otherStandardItemMapper.insert(otherStandardItem);
    }

    @Override
    public Integer deleteOtherStandardItem(String otherStandardNumber) {
        LambdaQueryWrapperX<OtherStandardItem> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eq(OtherStandardItem::getOtherStandardNumber,otherStandardNumber);
        return otherStandardItemMapper.delete(lambdaQueryWrapperX);
    }

    @Override
    public Integer removeOtherStandardItemByCode(String deviceCode, String otherStandardNumber) {
        LambdaQueryWrapperX<OtherStandardItem> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eq(OtherStandardItem::getDevice,deviceCode);
        lambdaQueryWrapperX.eq(OtherStandardItem::getOtherStandardNumber,otherStandardNumber);
        return otherStandardItemMapper.delete(lambdaQueryWrapperX);
    }

    @Override
    public List<OtherStandardItem> findOtherStandardItemByCode(String otherStandardNumber) {
        LambdaQueryWrapper<OtherStandardItem> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OtherStandardItem::getOtherStandardNumber,otherStandardNumber);
        return otherStandardItemMapper.selectList(lambdaQueryWrapper);
    }


}
