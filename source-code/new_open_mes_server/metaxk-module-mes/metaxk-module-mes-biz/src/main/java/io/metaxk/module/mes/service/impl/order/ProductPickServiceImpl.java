package io.metaxk.module.mes.service.impl.order;


import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.common.UserConstants;
import io.metaxk.module.mes.controller.admin.order.vo.ProductPickAllExportVo;
import io.metaxk.module.mes.controller.admin.order.vo.ProductPickExportVo;
import io.metaxk.module.mes.controller.admin.order.vo.ProductPickQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.ProductPick;
import io.metaxk.module.mes.dal.dataobject.order.ProductPickItem;
import io.metaxk.module.mes.dal.mysql.order.ProductPickItemMapper;
import io.metaxk.module.mes.dal.mysql.order.ProductPickMapper;
import io.metaxk.module.mes.service.order.ProductPickService;
import io.metaxk.module.mes.utils.AutoCodeUtil;
import io.metaxk.module.mes.utils.BeanCopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author 万界星空
 * @time 2023/7/21 14:12
 */
@Service
public class ProductPickServiceImpl implements ProductPickService {


    @Resource
    private ProductPickMapper productPickMapper;

    @Resource
    private ProductPickItemMapper productPickItemMapper;

    @Resource
    private AutoCodeUtil autoCodeUtil;

    @Override
    public PageResult<ProductPick> findPage(ProductPickQueryVo productPickQueryVo) {
        return productPickMapper.findPage(productPickQueryVo);
    }

    @Override
    public Integer saveProductPick(ProductPick productPick) {
        productPick.setNumber(autoCodeUtil.genSerialCode(UserConstants.PRODUCT_PICK_CODE,null));
        for(ProductPickItem productPickItem: productPick.getProductPickItemList()){
            ProductPickItem item = new ProductPickItem();
            item.setPickNumber(productPick.getNumber()).setItemCode(productPickItem.getItemCode()).setItemName(productPickItem.getItemName()).setModel(productPickItem.getModel())
                    .setSpec(productPickItem.getSpec()).setKind(productPickItem.getKind()).setUnitOfMeasure(productPickItem.getUnitOfMeasure())
                    .setPurchasePrice(productPickItem.getPurchasePrice()).setQuantity(productPickItem.getQuantity())
                    .setNoIncludTax(item.getPurchasePrice().multiply( item.getQuantity()))
                    .setIncludTax(item.getPurchasePrice().multiply(item.getQuantity()));
                   productPickItemMapper.insert(item);
        }
        return productPickMapper.insert(productPick);
    }



    @Override
    public ProductPick findProductPickById(Long id) {
        return productPickMapper.selectById(id);
    }



    @Override
    public Integer removeProductPick(List<Long> ids) {
        return productPickMapper.deleteBatchIds(ids);
    }



    @Override
    public Integer updateProductPick(ProductPick productPick) {
        ProductPick productPickDo = productPickMapper.selectById(productPick.getId());
        productPickItemMapper.delete(new LambdaQueryWrapperX<ProductPickItem>().eq(ProductPickItem::getPickNumber,productPickDo.getNumber()));
        for(ProductPickItem productPickItem: productPick.getProductPickItemList()){
            ProductPickItem item = new ProductPickItem();
            item.setPickNumber(productPick.getNumber()).setItemCode(productPickItem.getItemCode()).setItemName(productPickItem.getItemName()).setModel(productPickItem.getModel())
                    .setSpec(productPickItem.getSpec()).setKind(productPickItem.getKind()).setUnitOfMeasure(productPickItem.getUnitOfMeasure())
                    .setPurchasePrice(productPickItem.getPurchasePrice()).setQuantity(productPickItem.getQuantity())
                    .setNoIncludTax(item.getPurchasePrice().multiply( item.getQuantity()))
                    .setIncludTax(item.getPurchasePrice().multiply(item.getQuantity()));
            productPickItemMapper.insert(item);
        }
        return productPickMapper.updateById(productPick);
    }

    @Override
    public List<ProductPick> listAll() {
        return productPickMapper.selectList();
    }

    @Override
    public List<ProductPickExportVo> listData() {
        return BeanCopyUtil.copyListProperties(this.listAll(), ProductPickExportVo::new);
    }

    @Override
    public List<ProductPickAllExportVo> listAllDataByIds(List<Integer> ids) {
        return productPickMapper.exportAllByIds(ids);
    }
}
