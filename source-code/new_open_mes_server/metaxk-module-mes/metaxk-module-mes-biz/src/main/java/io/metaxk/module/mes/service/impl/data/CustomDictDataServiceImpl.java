package io.metaxk.module.mes.service.impl.data;

import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.dal.dataobject.data.CustomDictData;
import io.metaxk.module.mes.dal.mysql.data.CustomDictDataMapper;
import io.metaxk.module.mes.service.data.CustomDictDataService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;


/**
 * 数据字典 Service Impl
 * @author 万界星空
 */
@Service
public class CustomDictDataServiceImpl implements CustomDictDataService {

    @Resource
    private CustomDictDataMapper customDictDataMapper;


    @Override
    public List<CustomDictData> findListData() {
        LambdaQueryWrapperX<CustomDictData> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(CustomDictData::getDictType,"reason_for_suspension");
        return customDictDataMapper.selectList(queryWrapperX);
    }

    @Override
    public List<CustomDictData> listLineType() {
        return customDictDataMapper.selectList(new LambdaQueryWrapperX<CustomDictData>().eq(CustomDictData::getDictType,"lineType"));
    }

    @Override
    public List<CustomDictData> listCategory() {
        return customDictDataMapper.selectList(new LambdaQueryWrapperX<CustomDictData>().eq(CustomDictData::getDictType,"category"));
    }

    @Override
    public List<CustomDictData> listKind() {
        return customDictDataMapper.selectList(new LambdaQueryWrapperX<CustomDictData>().eq(CustomDictData::getDictType,"kind"));
    }

    @Override
    public CustomDictData findOutBoundLimit() {
        return customDictDataMapper.selectOne(new LambdaQueryWrapperX<CustomDictData>().eq(CustomDictData::getDictType,"out_bound_limit"));
    }


}
