package io.metaxk.module.infra.service.db;

import io.metaxk.framework.mybatis.core.util.JdbcUtils;
import io.metaxk.module.infra.controller.admin.db.vo.DataSourceConfigCreateReqVO;
import io.metaxk.module.infra.controller.admin.db.vo.DataSourceConfigUpdateReqVO;
import io.metaxk.module.infra.convert.db.DataSourceConfigConvert;
import io.metaxk.module.infra.dal.dataobject.db.DataSourceConfigDO;
import io.metaxk.module.infra.dal.mysql.db.DataSourceConfigMapper;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.module.infra.enums.ErrorCodeConstants.DATA_SOURCE_CONFIG_NOT_EXISTS;
import static io.metaxk.module.infra.enums.ErrorCodeConstants.DATA_SOURCE_CONFIG_NOT_OK;

/**
 * 数据源配置 Service 实现类
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Service
@Validated
public class DataSourceConfigServiceImpl implements DataSourceConfigService {

    @Resource
    private DataSourceConfigMapper dataSourceConfigMapper;

    @Resource
    private DynamicDataSourceProperties dynamicDataSourceProperties;

    @Override
    public Long createDataSourceConfig(DataSourceConfigCreateReqVO createReqVO) {
        DataSourceConfigDO dataSourceConfig = DataSourceConfigConvert.INSTANCE.convert(createReqVO);
        validateConnectionOK(dataSourceConfig);

        // 插入
        dataSourceConfigMapper.insert(dataSourceConfig);
        // 返回
        return dataSourceConfig.getId();
    }

    @Override
    public void updateDataSourceConfig(DataSourceConfigUpdateReqVO updateReqVO) {
        // 校验存在
        validateDataSourceConfigExists(updateReqVO.getId());
        DataSourceConfigDO updateObj = DataSourceConfigConvert.INSTANCE.convert(updateReqVO);
        validateConnectionOK(updateObj);

        // 更新
        dataSourceConfigMapper.updateById(updateObj);
    }

    @Override
    public void deleteDataSourceConfig(Long id) {
        // 校验存在
        validateDataSourceConfigExists(id);
        // 删除
        dataSourceConfigMapper.deleteById(id);
    }

    private void validateDataSourceConfigExists(Long id) {
        if (dataSourceConfigMapper.selectById(id) == null) {
            throw exception(DATA_SOURCE_CONFIG_NOT_EXISTS);
        }
    }

    @Override
    public DataSourceConfigDO getDataSourceConfig(Long id) {
        // 如果 id 为 0，默认为 master 的数据源
        if (Objects.equals(id, DataSourceConfigDO.ID_MASTER)) {
            return buildMasterDataSourceConfig();
        }
        // 从 DB 中读取
        return dataSourceConfigMapper.selectById(id);
    }

    @Override
    public List<DataSourceConfigDO> getDataSourceConfigList() {
        List<DataSourceConfigDO> result = dataSourceConfigMapper.selectList();
        // 补充 master 数据源
        result.add(0, buildMasterDataSourceConfig());
        return result;
    }

    private void validateConnectionOK(DataSourceConfigDO config) {
        boolean success = JdbcUtils.isConnectionOK(config.getUrl(), config.getUsername(), config.getPassword());
        if (!success) {
            throw exception(DATA_SOURCE_CONFIG_NOT_OK);
        }
    }

    private DataSourceConfigDO buildMasterDataSourceConfig() {
        String primary = dynamicDataSourceProperties.getPrimary();
        DataSourceProperty dataSourceProperty = dynamicDataSourceProperties.getDatasource().get(primary);
        return new DataSourceConfigDO().setId(DataSourceConfigDO.ID_MASTER).setName(primary)
                .setUrl(dataSourceProperty.getUrl())
                .setUsername(dataSourceProperty.getUsername())
                .setPassword(dataSourceProperty.getPassword());
    }

}
