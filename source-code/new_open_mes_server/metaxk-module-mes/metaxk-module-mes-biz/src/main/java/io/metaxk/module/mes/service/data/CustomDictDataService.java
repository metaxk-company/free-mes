package io.metaxk.module.mes.service.data;

import io.metaxk.module.mes.dal.dataobject.data.CustomDictData;
import java.util.List;

/**
 * 数据字典 Service 接口
 * @author 万界星空
 */
public interface CustomDictDataService {


    /**
     * 暂停原因数据字典列表
     *
     * @return List<CustomDictData>
     */
    List<CustomDictData> findListData();

    /**
     * 线别数据字典列表
     *
     * @return List<CustomDictData>
     */
    List<CustomDictData> listLineType();


    /**
     * 品类数据字典列表
     *
     * @return List<CustomDictData>
     */
    List<CustomDictData> listCategory();

    /**
     * 类别数据字典列表
     *
     * @return List<CustomDictData>
     */
    List<CustomDictData> listKind();


    CustomDictData findOutBoundLimit();
}
