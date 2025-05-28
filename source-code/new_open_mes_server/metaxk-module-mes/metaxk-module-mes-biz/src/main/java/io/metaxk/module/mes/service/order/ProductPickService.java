package io.metaxk.module.mes.service.order;


import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.ProductPickAllExportVo;
import io.metaxk.module.mes.controller.admin.order.vo.ProductPickExportVo;
import io.metaxk.module.mes.controller.admin.order.vo.ProductPickQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.ProductPick;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/21 14:12
 */
public interface ProductPickService {


    PageResult<ProductPick> findPage(ProductPickQueryVo productPickQueryVo);

    Integer saveProductPick(ProductPick productPick);

    ProductPick findProductPickById(Long id);

    Integer removeProductPick(List<Long> ids);

    Integer updateProductPick(ProductPick productPick);

    List<ProductPick> listAll();

    List<ProductPickExportVo> listData();

    List<ProductPickAllExportVo> listAllDataByIds(List<Integer> ids);
}
