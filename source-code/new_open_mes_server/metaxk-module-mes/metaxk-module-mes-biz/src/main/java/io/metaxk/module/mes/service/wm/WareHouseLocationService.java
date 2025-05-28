package io.metaxk.module.mes.service.wm;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.wm.vo.WareHouseLocationQueryVo;
import io.metaxk.module.mes.dal.dataobject.wm.WareHouseLocation;

import java.util.List;

/**
 * 库位 Service
 * @author 万界星空
 */
public interface WareHouseLocationService {



    Integer saveWareHouseLocation(WareHouseLocation wareHouseLocation);

    Integer removeWareHouseLocationByIds(List<Long> ids);

    WareHouseLocation findWareHouseLocationById(Long id);

    Integer updateWareHouseLocation(WareHouseLocation wareHouseLocation);

    PageResult<WareHouseLocation> findPage(WareHouseLocationQueryVo wareHouseLocationQueryVo);

    WareHouseLocation findWareHouseLocationByName(String locationName);


}
