package io.metaxk.module.mes.service.impl.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.dv.vo.MachineryExcelVo;
import io.metaxk.module.mes.controller.admin.order.vo.QuoteModelExportVo;
import io.metaxk.module.mes.controller.admin.order.vo.QuoteModelQueryVo;
import io.metaxk.module.mes.dal.dataobject.dv.Machinery;
import io.metaxk.module.mes.dal.dataobject.order.QuoteModel;
import io.metaxk.module.mes.dal.mysql.order.QuoteModelMapper;
import io.metaxk.module.mes.service.order.QuoteModelService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/17 15:05
 */
@Service
public class QuoteModelServiceImpl implements QuoteModelService {


    @Resource
    private QuoteModelMapper quoteModelMapper;



    @Override
    public Integer saveOrderQuoteModel(QuoteModel orderQuoteModel) {
        orderQuoteModel.setCreateTime(new Date());
        return quoteModelMapper.insert(orderQuoteModel);
    }



    @Override
    public Integer removeOrderQuoteModel(List<Long> ids) {
        return quoteModelMapper.deleteBatchIds(ids);
    }



    @Override
    public Integer updateOrderQuoteModel(QuoteModel orderQuoteModel) {
        orderQuoteModel.setUpdateTime(new Date());
        return quoteModelMapper.updateById(orderQuoteModel);
    }



    @Override
    public QuoteModel findOrderQuoteModelById(Long id) {
        return quoteModelMapper.selectById(id);
    }



    @Override
    public PageResult<QuoteModel> findPage(QuoteModelQueryVo orderQuoteModelQueryVo) {
        return quoteModelMapper.findPage(orderQuoteModelQueryVo);
    }

    @Override
    public QuoteModel findQuoteModelByName(String model) {
        return quoteModelMapper.selectOne(new LambdaQueryWrapperX<QuoteModel>().eq(QuoteModel::getModel,model));
    }

    @Override
    public List<QuoteModel> selectQuoteModel() {
        return quoteModelMapper.selectList();
    }

    @Override
    public List<QuoteModelExportVo> exportData() {
        List<QuoteModel> quoteModelList = quoteModelMapper.selectList();
        ArrayList<QuoteModelExportVo> quoteModelExportVo = new ArrayList<>(quoteModelList.size());
        quoteModelList.forEach(dict -> {
            QuoteModelExportVo excelDictDTO = new QuoteModelExportVo();
            BeanUtils.copyProperties(dict, excelDictDTO);
            quoteModelExportVo.add(excelDictDTO);
        });
        return quoteModelExportVo;
    }

}
