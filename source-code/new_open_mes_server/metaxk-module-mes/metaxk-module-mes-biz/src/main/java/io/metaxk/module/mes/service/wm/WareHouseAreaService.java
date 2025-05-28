package io.metaxk.module.mes.service.wm;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.wm.vo.WareHouseAreaQueryVo;
import io.metaxk.module.mes.dal.dataobject.wm.WareHouseArea;
import java.util.List;

/**
 * 库区 Service
 * @author 万界星空MES
 */
public interface WareHouseAreaService {


    Integer saveWareHouseArea(WareHouseArea wareHouseArea);

    Integer removeWareHouseAreaByIds(List<Long> ids);

    WareHouseArea findWareHouseAreaById(Long id);

    Integer updateWareHouseArea( WareHouseArea wareHouseArea);

    PageResult<WareHouseArea> findPage(WareHouseAreaQueryVo wareHouseAreaQueryVo);

    WareHouseArea findWareHouseAreaByName(String areaName);
}
