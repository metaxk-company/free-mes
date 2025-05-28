package io.metaxk.module.mes.service.impl.qc;

import com.alibaba.excel.EasyExcel;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.qc.vo.WayExcelVo;
import io.metaxk.module.mes.controller.admin.qc.vo.WayQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.Way;
import io.metaxk.module.mes.dal.mysql.qc.WayMapper;
import io.metaxk.module.mes.service.qc.WayService;
import io.metaxk.module.mes.controller.admin.qc.vo.WayExcelListener;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;



/**
 * @author 万界星空
 * @time 2023/7/6 10:54
 */
@Service
public class WayServiceImpl implements WayService {

    @Resource
    private WayMapper inspectWayMapper;

    @Override
    public Integer saveInspectMethod(Way inspectMethod) {
        return inspectWayMapper.insert(inspectMethod);
    }

    @Override
    public Integer removeInspectMethod(List<Long> ids) {
        return inspectWayMapper.deleteBatchIds(ids);
    }

    @Override
    public Way findMethodById(Long id) {
        return inspectWayMapper.selectById(id);
    }

    @Override
    public Integer updateInspectMethod(Way inspectMethod) {
        return inspectWayMapper.updateById(inspectMethod);
    }

    @Override
    public PageResult<Way> findPage(WayQueryVo inspectMethodPageReqVO) {
        return inspectWayMapper.findPage(inspectMethodPageReqVO);
    }

    @Override
    public void importExcelData(InputStream inputStream) {
        EasyExcel.read(inputStream, WayExcelVo.class,new WayExcelListener(inspectWayMapper)).sheet().doRead();
    }

    @Override
    public Way findMethodByName(String inspectName) {
        LambdaQueryWrapperX<Way> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Way::getInspectName,inspectName);
        return inspectWayMapper.selectOne(queryWrapperX);
    }


}
