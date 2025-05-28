package io.metaxk.module.mes.service.md;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.md.vo.PanHaoExportVo;
import io.metaxk.module.mes.controller.admin.md.vo.PanHaoQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.PanHao;

import java.util.List;


/**
 * 盘号Service
 * @author 万界星空
 */
public interface PanHaoService {

    /**
     * 新增盘号
     * @param panHao
     * @return Integer
     */
    Integer savePanHao(PanHao panHao);

    /**
     * 批量删除盘号
     * @param ids
     * @return Integer
     */
    Integer removePanHaoByIds(List<Long> ids);

    /**
     * 修改盘号
     * @param panHao
     * @return Integer
     */
    Integer updatePanHao(PanHao panHao);


    /**
     * 盘号条件分页查询
     * @param panHaoQueryVo
     * @return PageResult<PanHao>
     */
    PageResult<PanHao> findPage(PanHaoQueryVo panHaoQueryVo);

    /**
     * 根据编号查询盘号
     * @param number
     * @return PanHao
     */
    PanHao findPanHaoByNum(String number);

    List<PanHao> listAll();

    List<PanHaoExportVo> listData();

}
