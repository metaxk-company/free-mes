package io.metaxk.module.mes.service.impl.wm;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.wm.vo.WareHouseQueryVo;
import io.metaxk.module.mes.dal.dataobject.wm.WareHouse;
import io.metaxk.module.mes.dal.mysql.wm.WareHouseMapper;
import io.metaxk.module.mes.service.wm.WareHouseService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 仓库 Service 实现类
 * @author 万界星空
 */
@Service
public class WareHouseServiceImpl implements WareHouseService {

    @Resource
    private WareHouseMapper warehouseMapper;


    @Override
    public Integer saveWareHouse(WareHouse wareHouse) {
        return warehouseMapper.insert(wareHouse);
    }



    @Override
    public Integer removeWareHouseByIds(List<Long> ids) {
        return warehouseMapper.deleteBatchIds(ids);
    }

    @Override
    public WareHouse findWareHouseById(Long id) {
        return warehouseMapper.selectById(id);
    }

    @Override
    public Integer updateWareHouse(WareHouse wareHouse) {
        return warehouseMapper.updateById(wareHouse);
    }

    @Override
    public PageResult<WareHouse> findPage(WareHouseQueryVo wareHouseQueryVo) {
        return warehouseMapper.findPage(wareHouseQueryVo);
    }

    @Override
    public WareHouse findWareHouseByName(String warehouseName) {
        return warehouseMapper.selectOne(new LambdaQueryWrapperX<WareHouse>().eq(WareHouse::getWarehouseName,warehouseName));
    }

    @Override
    public List<WareHouse> listAll() {
        return warehouseMapper.selectList();
    }


}
