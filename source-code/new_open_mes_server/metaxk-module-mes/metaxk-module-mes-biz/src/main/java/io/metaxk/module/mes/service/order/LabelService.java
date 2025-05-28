package io.metaxk.module.mes.service.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.DetailedInventoryResponseVO;
import io.metaxk.module.mes.controller.admin.order.vo.LabelExportVo;
import io.metaxk.module.mes.controller.admin.order.vo.LabelQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.Label;
import io.metaxk.module.mes.dal.dataobject.order.OutboundRecord;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/27 15:32
 */
public interface LabelService {


    Integer saveLabel(Label label);


    Integer removeLabelByIds(List<Long> ids);


    Integer updateLabel(Label label);


    Label findLabelById(Long id);


    PageResult<Label> findPage(LabelQueryVo labelVo);


    Integer updateStatus(Long id,String status);

    List<Label> findLabelByStatus(String status);

    List<Label> listAll();

    List<LabelExportVo> listData();

    Label findByBarCode(String barCode);

    List<Label> findByPanHao(String panhao);

    PageResult<Label> findRepackagedListPage(LabelQueryVo labelVo);

    OutboundRecord countRepackage();

    List<Label> findByReelNumber(String panhao);

    List<DetailedInventoryResponseVO> detailedInventory(String lineType, String model, String spec);

}
