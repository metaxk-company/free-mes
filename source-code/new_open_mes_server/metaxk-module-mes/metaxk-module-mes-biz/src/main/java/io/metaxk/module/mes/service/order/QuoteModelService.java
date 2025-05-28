package io.metaxk.module.mes.service.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.QuoteModelExportVo;
import io.metaxk.module.mes.controller.admin.order.vo.QuoteModelQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.QuoteModel;

import java.util.Collection;
import java.util.List;


/**
 * @author 万界星空
 * @time 2023/7/17 15:04
 */
public interface QuoteModelService {

    /**
     * 新增客户报价单 -型号
     * @param orderQuoteModel
     * @return  Integer
     */
    Integer saveOrderQuoteModel(QuoteModel orderQuoteModel);


    /**
     * 删除客户报价单 -型号
     * @param ids
     * @return Integer
     */
    Integer removeOrderQuoteModel(List<Long> ids);

    /**
     * 修改客户报价单 -型号
     * @param orderQuoteModel
     * @return Integer
     */
    Integer updateOrderQuoteModel(QuoteModel orderQuoteModel);

    /**
     * 根据id查询客户报价单 -型号详情
     * @param id
     * @return  QuoteModel
     */
    QuoteModel findOrderQuoteModelById(Long id);

    /**
     *  客户报价单-型号条件分页查询
     * @param orderQuoteModelQueryVo
     * @return PageResult<QuoteModel>
     */
    PageResult<QuoteModel> findPage(QuoteModelQueryVo orderQuoteModelQueryVo);

    QuoteModel findQuoteModelByName(String model);

    List<QuoteModel> selectQuoteModel();

    List<QuoteModelExportVo> exportData();
}
