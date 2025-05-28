package io.metaxk.module.mes.service.impl.wm;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.wm.vo.WareHouseAreaQueryVo;
import io.metaxk.module.mes.dal.dataobject.wm.WareHouseArea;
import io.metaxk.module.mes.dal.mysql.wm.WareHouseAreaMapper;
import io.metaxk.module.mes.service.wm.WareHouseAreaService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;


/**
 * 库区 Service
 * @author 万界星空
 */
@Service
public class WareHouseAreaServiceImpl implements WareHouseAreaService {

    @Resource
    private WareHouseAreaMapper wareHouseAreaMapper;


    @Override
    public Integer saveWareHouseArea(WareHouseArea wareHouseArea) {
        return wareHouseAreaMapper.insert(wareHouseArea);
    }

    @Override
    public Integer removeWareHouseAreaByIds(List<Long> ids) {
        return wareHouseAreaMapper.deleteBatchIds(ids);
    }

    @Override
    public WareHouseArea findWareHouseAreaById(Long id) {
        return wareHouseAreaMapper.selectById(id);
    }

    @Override
    public Integer updateWareHouseArea(WareHouseArea wareHouseArea) {
        return wareHouseAreaMapper.updateById(wareHouseArea);
    }

    @Override
    public PageResult<WareHouseArea> findPage(WareHouseAreaQueryVo wareHouseAreaQueryVo) {
        return wareHouseAreaMapper.findPage(wareHouseAreaQueryVo);
    }

    @Override
    public WareHouseArea findWareHouseAreaByName(String areaName) {
        return wareHouseAreaMapper.selectOne(new LambdaQueryWrapperX<WareHouseArea>().eq(WareHouseArea::getAreaName,areaName));
    }
}
