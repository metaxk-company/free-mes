package io.metaxk.module.mes.service.impl.wm;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.wm.vo.WareHouseLocationQueryVo;
import io.metaxk.module.mes.dal.dataobject.wm.WareHouseLocation;
import io.metaxk.module.mes.dal.mysql.wm.WareHouseLocationMapper;
import io.metaxk.module.mes.service.wm.WareHouseLocationService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * 库位 Service 实现类
 *
 * @author 万界星空
 */
@Service
public class WareHouseLocationServiceImpl implements WareHouseLocationService {

    @Resource
    private WareHouseLocationMapper wareHouseLocationMapper;


    @Override
    public Integer saveWareHouseLocation(WareHouseLocation wareHouseLocation) {
        return wareHouseLocationMapper.insert(wareHouseLocation);
    }

    @Override
    public Integer removeWareHouseLocationByIds(List<Long> ids) {
        return wareHouseLocationMapper.deleteBatchIds(ids);
    }

    @Override
    public WareHouseLocation findWareHouseLocationById(Long id) {
        return wareHouseLocationMapper.selectById(id);
    }

    @Override
    public Integer updateWareHouseLocation(WareHouseLocation wareHouseLocation) {
        return wareHouseLocationMapper.updateById(wareHouseLocation);
    }

    @Override
    public PageResult<WareHouseLocation> findPage(WareHouseLocationQueryVo wareHouseLocationQueryVo) {
        return wareHouseLocationMapper.findPage(wareHouseLocationQueryVo);
    }

    @Override
    public WareHouseLocation findWareHouseLocationByName(String locationName) {
        return wareHouseLocationMapper.selectOne(new LambdaQueryWrapperX<WareHouseLocation>().eq(WareHouseLocation::getLocationName,locationName));
    }


}
