package io.metaxk.module.infra.service.db;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.crypto.symmetric.AES;
import io.metaxk.framework.mybatis.core.type.EncryptTypeHandler;
import io.metaxk.framework.mybatis.core.util.JdbcUtils;
import io.metaxk.framework.test.core.ut.BaseDbUnitTest;
import io.metaxk.module.infra.controller.admin.db.vo.DataSourceConfigCreateReqVO;
import io.metaxk.module.infra.controller.admin.db.vo.DataSourceConfigUpdateReqVO;
import io.metaxk.module.infra.dal.dataobject.db.DataSourceConfigDO;
import io.metaxk.module.infra.dal.mysql.db.DataSourceConfigMapper;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.List;

import static io.metaxk.framework.test.core.util.AssertUtils.assertPojoEquals;
import static io.metaxk.framework.test.core.util.AssertUtils.assertServiceException;
import static io.metaxk.framework.test.core.util.RandomUtils.randomLongId;
import static io.metaxk.framework.test.core.util.RandomUtils.randomPojo;
import static io.metaxk.module.infra.enums.ErrorCodeConstants.DATA_SOURCE_CONFIG_NOT_EXISTS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

/**
 * {@link DataSourceConfigServiceImpl} 的单元测试类
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Import(DataSourceConfigServiceImpl.class)
public class DataSourceConfigServiceImplTest extends BaseDbUnitTest {

    @Resource
    private DataSourceConfigServiceImpl dataSourceConfigService;

    @Resource
    private DataSourceConfigMapper dataSourceConfigMapper;

    @MockBean
    private AES aes;

    @MockBean
    private DynamicDataSourceProperties dynamicDataSourceProperties;

    @BeforeEach
    public void setUp() {
        // mock 一个空实现的 StringEncryptor，避免 EncryptTypeHandler 报错
        ReflectUtil.setFieldValue(EncryptTypeHandler.class, "aes", aes);
        when(aes.encryptBase64(anyString())).then((Answer<String>) invocation -> invocation.getArgument(0));
        when(aes.decryptStr(anyString())).then((Answer<String>) invocation -> invocation.getArgument(0));

        // mock DynamicDataSourceProperties
        when(dynamicDataSourceProperties.getPrimary()).thenReturn("primary");
        when(dynamicDataSourceProperties.getDatasource()).thenReturn(MapUtil.of("primary",
                new DataSourceProperty().setUrl("http://localhost:3306").setUsername("metaxk").setPassword("tudou")));
    }

    @Test
    public void testCreateDataSourceConfig_success() {
        try (MockedStatic<JdbcUtils> databaseUtilsMock = mockStatic(JdbcUtils.class)) {
            // 准备参数
            DataSourceConfigCreateReqVO reqVO = randomPojo(DataSourceConfigCreateReqVO.class);
            // mock 方法
            databaseUtilsMock.when(() -> JdbcUtils.isConnectionOK(eq(reqVO.getUrl()),
                    eq(reqVO.getUsername()), eq(reqVO.getPassword()))).thenReturn(true);

            // 调用
            Long dataSourceConfigId = dataSourceConfigService.createDataSourceConfig(reqVO);
            // 断言
            assertNotNull(dataSourceConfigId);
            // 校验记录的属性是否正确
            DataSourceConfigDO dataSourceConfig = dataSourceConfigMapper.selectById(dataSourceConfigId);
            assertPojoEquals(reqVO, dataSourceConfig);
        }
    }

    @Test
    public void testUpdateDataSourceConfig_success() {
        try (MockedStatic<JdbcUtils> databaseUtilsMock = mockStatic(JdbcUtils.class)) {
            // mock 数据
            DataSourceConfigDO dbDataSourceConfig = randomPojo(DataSourceConfigDO.class);
            dataSourceConfigMapper.insert(dbDataSourceConfig);// @Sql: 先插入出一条存在的数据
            // 准备参数
            DataSourceConfigUpdateReqVO reqVO = randomPojo(DataSourceConfigUpdateReqVO.class, o -> {
                o.setId(dbDataSourceConfig.getId()); // 设置更新的 ID
            });
            // mock 方法
            databaseUtilsMock.when(() -> JdbcUtils.isConnectionOK(eq(reqVO.getUrl()),
                    eq(reqVO.getUsername()), eq(reqVO.getPassword()))).thenReturn(true);

            // 调用
            dataSourceConfigService.updateDataSourceConfig(reqVO);
            // 校验是否更新正确
            DataSourceConfigDO dataSourceConfig = dataSourceConfigMapper.selectById(reqVO.getId()); // 获取最新的
            assertPojoEquals(reqVO, dataSourceConfig);
        }
    }

    @Test
    public void testUpdateDataSourceConfig_notExists() {
        // 准备参数
        DataSourceConfigUpdateReqVO reqVO = randomPojo(DataSourceConfigUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> dataSourceConfigService.updateDataSourceConfig(reqVO), DATA_SOURCE_CONFIG_NOT_EXISTS);
    }

    @Test
    public void testDeleteDataSourceConfig_success() {
        // mock 数据
        DataSourceConfigDO dbDataSourceConfig = randomPojo(DataSourceConfigDO.class);
        dataSourceConfigMapper.insert(dbDataSourceConfig);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbDataSourceConfig.getId();

        // 调用
        dataSourceConfigService.deleteDataSourceConfig(id);
        // 校验数据不存在了
        assertNull(dataSourceConfigMapper.selectById(id));
    }

    @Test
    public void testDeleteDataSourceConfig_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> dataSourceConfigService.deleteDataSourceConfig(id), DATA_SOURCE_CONFIG_NOT_EXISTS);
    }

    @Test // 测试使用 password 查询，可以查询到数据
    public void testSelectPassword() {
        // mock 数据
        DataSourceConfigDO dbDataSourceConfig = randomPojo(DataSourceConfigDO.class);
        dataSourceConfigMapper.insert(dbDataSourceConfig);// @Sql: 先插入出一条存在的数据

        // 调用
        DataSourceConfigDO result = dataSourceConfigMapper.selectOne(DataSourceConfigDO::getPassword,
                EncryptTypeHandler.encrypt(dbDataSourceConfig.getPassword()));
        assertPojoEquals(dbDataSourceConfig, result);
    }

    @Test
    public void testGetDataSourceConfig_master() {
        // 准备参数
        Long id = 0L;
        // mock 方法

        // 调用
        DataSourceConfigDO dataSourceConfig = dataSourceConfigService.getDataSourceConfig(id);
        // 断言
        assertEquals(id, dataSourceConfig.getId());
        assertEquals("primary", dataSourceConfig.getName());
        assertEquals("http://localhost:3306", dataSourceConfig.getUrl());
        assertEquals("metaxk", dataSourceConfig.getUsername());
        assertEquals("tudou", dataSourceConfig.getPassword());
    }

    @Test
    public void testGetDataSourceConfig_normal() {
        // mock 数据
        DataSourceConfigDO dbDataSourceConfig = randomPojo(DataSourceConfigDO.class);
        dataSourceConfigMapper.insert(dbDataSourceConfig);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbDataSourceConfig.getId();

        // 调用
        DataSourceConfigDO dataSourceConfig = dataSourceConfigService.getDataSourceConfig(id);
        // 断言
        assertPojoEquals(dbDataSourceConfig, dataSourceConfig);
    }

    @Test
    public void testGetDataSourceConfigList() {
        // mock 数据
        DataSourceConfigDO dbDataSourceConfig = randomPojo(DataSourceConfigDO.class);
        dataSourceConfigMapper.insert(dbDataSourceConfig);// @Sql: 先插入出一条存在的数据
        // 准备参数

        // 调用
        List<DataSourceConfigDO> dataSourceConfigList = dataSourceConfigService.getDataSourceConfigList();
        // 断言
        assertEquals(2, dataSourceConfigList.size());
        // master
        assertEquals(0L, dataSourceConfigList.get(0).getId());
        assertEquals("primary", dataSourceConfigList.get(0).getName());
        assertEquals("http://localhost:3306", dataSourceConfigList.get(0).getUrl());
        assertEquals("metaxk", dataSourceConfigList.get(0).getUsername());
        assertEquals("tudou", dataSourceConfigList.get(0).getPassword());
        // normal
        assertPojoEquals(dbDataSourceConfig, dataSourceConfigList.get(1));
    }

}
