package io.metaxk.module.mes.service.wm;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.wm.vo.WareHouseQueryVo;
import io.metaxk.module.mes.dal.dataobject.wm.WareHouse;
import java.util.List;



/**
 * 仓库 Service
 * @author 万界星空MES
 */
public interface WareHouseService {



    Integer saveWareHouse(WareHouse wareHouse);

    Integer removeWareHouseByIds(List<Long> ids);

    WareHouse findWareHouseById(Long id);

    Integer updateWareHouse(WareHouse wareHouse);

    PageResult<WareHouse> findPage(WareHouseQueryVo wareHouseQueryVo);

    WareHouse findWareHouseByName(String warehouseName);

    List<WareHouse> listAll();
}
