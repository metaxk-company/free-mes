package io.metaxk.module.infra.convert.db;

import java.util.*;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import io.metaxk.module.infra.controller.admin.db.vo.*;
import io.metaxk.module.infra.dal.dataobject.db.DataSourceConfigDO;

/**
 * 数据源配置 Convert
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Mapper
public interface DataSourceConfigConvert {

    DataSourceConfigConvert INSTANCE = Mappers.getMapper(DataSourceConfigConvert.class);

    DataSourceConfigDO convert(DataSourceConfigCreateReqVO bean);

    DataSourceConfigDO convert(DataSourceConfigUpdateReqVO bean);

    DataSourceConfigRespVO convert(DataSourceConfigDO bean);

    List<DataSourceConfigRespVO> convertList(List<DataSourceConfigDO> list);

}
