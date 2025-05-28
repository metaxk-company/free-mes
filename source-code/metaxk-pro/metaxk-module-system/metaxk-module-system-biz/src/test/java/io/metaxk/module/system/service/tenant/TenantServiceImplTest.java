package io.metaxk.module.system.service.tenant;

import io.metaxk.framework.common.enums.CommonStatusEnum;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.tenant.config.TenantProperties;
import io.metaxk.framework.tenant.core.context.TenantContextHolder;
import io.metaxk.framework.test.core.ut.BaseDbUnitTest;
import io.metaxk.module.system.controller.admin.tenant.vo.tenant.TenantCreateReqVO;
import io.metaxk.module.system.controller.admin.tenant.vo.tenant.TenantExportReqVO;
import io.metaxk.module.system.controller.admin.tenant.vo.tenant.TenantPageReqVO;
import io.metaxk.module.system.controller.admin.tenant.vo.tenant.TenantUpdateReqVO;
import io.metaxk.module.system.dal.dataobject.permission.MenuDO;
import io.metaxk.module.system.dal.dataobject.permission.RoleDO;
import io.metaxk.module.system.dal.dataobject.tenant.TenantDO;
import io.metaxk.module.system.dal.dataobject.tenant.TenantPackageDO;
import io.metaxk.module.system.dal.mysql.tenant.TenantMapper;
import io.metaxk.module.system.enums.permission.RoleCodeEnum;
import io.metaxk.module.system.enums.permission.RoleTypeEnum;
import io.metaxk.module.system.service.permission.MenuService;
import io.metaxk.module.system.service.permission.PermissionService;
import io.metaxk.module.system.service.permission.RoleService;
import io.metaxk.module.system.service.tenant.handler.TenantInfoHandler;
import io.metaxk.module.system.service.tenant.handler.TenantMenuHandler;
import io.metaxk.module.system.service.user.AdminUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.metaxk.framework.common.util.collection.SetUtils.asSet;
import static io.metaxk.framework.common.util.date.LocalDateTimeUtils.buildBetweenTime;
import static io.metaxk.framework.common.util.date.LocalDateTimeUtils.buildTime;
import static io.metaxk.framework.common.util.object.ObjectUtils.cloneIgnoreId;
import static io.metaxk.framework.test.core.util.AssertUtils.assertPojoEquals;
import static io.metaxk.framework.test.core.util.AssertUtils.assertServiceException;
import static io.metaxk.framework.test.core.util.RandomUtils.*;
import static io.metaxk.module.system.dal.dataobject.tenant.TenantDO.PACKAGE_ID_SYSTEM;
import static io.metaxk.module.system.enums.ErrorCodeConstants.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * {@link TenantServiceImpl} 的单元测试类
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Import(TenantServiceImpl.class)
public class TenantServiceImplTest extends BaseDbUnitTest {

    @Resource
    private TenantServiceImpl tenantService;

    @Resource
    private TenantMapper tenantMapper;

    @MockBean
    private TenantProperties tenantProperties;
    @MockBean
    private TenantPackageService tenantPackageService;
    @MockBean
    private AdminUserService userService;
    @MockBean
    private RoleService roleService;
    @MockBean
    private MenuService menuService;
    @MockBean
    private PermissionService permissionService;

    @BeforeEach
    public void setUp() {
        // 清理租户上下文
        TenantContextHolder.clear();
    }

    @Test
    public void testGetTenantIdList() {
        // mock 数据
        TenantDO tenant = randomPojo(TenantDO.class, o -> o.setId(1L));
        tenantMapper.insert(tenant);

        // 调用，并断言业务异常
        List<Long> result = tenantService.getTenantIdList();
        assertEquals(Collections.singletonList(1L), result);
    }

    @Test
    public void testValidTenant_notExists() {
        assertServiceException(() -> tenantService.validTenant(randomLongId()), TENANT_NOT_EXISTS);
    }

    @Test
    public void testValidTenant_disable() {
        // mock 数据
        TenantDO tenant = randomPojo(TenantDO.class, o -> o.setId(1L).setStatus(CommonStatusEnum.DISABLE.getStatus()));
        tenantMapper.insert(tenant);

        // 调用，并断言业务异常
        assertServiceException(() -> tenantService.validTenant(1L), TENANT_DISABLE, tenant.getName());
    }

    @Test
    public void testValidTenant_expired() {
        // mock 数据
        TenantDO tenant = randomPojo(TenantDO.class, o -> o.setId(1L).setStatus(CommonStatusEnum.ENABLE.getStatus())
                .setExpireTime(buildTime(2020, 2, 2)));
        tenantMapper.insert(tenant);

        // 调用，并断言业务异常
        assertServiceException(() -> tenantService.validTenant(1L), TENANT_EXPIRE, tenant.getName());
    }

    @Test
    public void testValidTenant_success() {
        // mock 数据
        TenantDO tenant = randomPojo(TenantDO.class, o -> o.setId(1L).setStatus(CommonStatusEnum.ENABLE.getStatus())
                .setExpireTime(LocalDateTime.now().plusDays(1)));
        tenantMapper.insert(tenant);

        // 调用，并断言业务异常
        tenantService.validTenant(1L);
    }

    @Test
    public void testCreateTenant() {
        // mock 套餐 100L
        TenantPackageDO tenantPackage = randomPojo(TenantPackageDO.class, o -> o.setId(100L));
        when(tenantPackageService.validTenantPackage(eq(100L))).thenReturn(tenantPackage);
        // mock 角色 200L
        when(roleService.createRole(argThat(role -> {
            assertEquals(RoleCodeEnum.TENANT_ADMIN.getName(), role.getName());
            assertEquals(RoleCodeEnum.TENANT_ADMIN.getCode(), role.getCode());
            assertEquals(0, role.getSort());
            assertEquals("系统自动生成", role.getRemark());
            return true;
        }), eq(RoleTypeEnum.SYSTEM.getType()))).thenReturn(200L);
        // mock 用户 300L
        when(userService.createUser(argThat(user -> {
            assertEquals("metaxk", user.getUsername());
            assertEquals("yuanma", user.getPassword());
            assertEquals("万界星空科技", user.getNickname());
            assertEquals("15601691300", user.getMobile());
            return true;
        }))).thenReturn(300L);

        // 准备参数
        TenantCreateReqVO reqVO = randomPojo(TenantCreateReqVO.class, o -> {
            o.setContactName("万界星空科技");
            o.setContactMobile("15601691300");
            o.setPackageId(100L);
            o.setStatus(randomCommonStatus());
            o.setDomain("https://www.metaxk.io");
            o.setUsername("metaxk");
            o.setPassword("yuanma");
        });

        // 调用
        Long tenantId = tenantService.createTenant(reqVO);
        // 断言
        assertNotNull(tenantId);
        // 校验记录的属性是否正确
        TenantDO tenant = tenantMapper.selectById(tenantId);
        assertPojoEquals(reqVO, tenant);
        assertEquals(300L, tenant.getContactUserId());
        // verify 分配权限
        verify(permissionService).assignRoleMenu(eq(200L), same(tenantPackage.getMenuIds()));
        // verify 分配角色
        verify(permissionService).assignUserRole(eq(300L), eq(singleton(200L)));
    }

    @Test
    public void testUpdateTenant_success() {
        // mock 数据
        TenantDO dbTenant = randomPojo(TenantDO.class, o -> o.setStatus(randomCommonStatus()));
        tenantMapper.insert(dbTenant);// @Sql: 先插入出一条存在的数据
        // 准备参数
        TenantUpdateReqVO reqVO = randomPojo(TenantUpdateReqVO.class, o -> {
            o.setId(dbTenant.getId()); // 设置更新的 ID
            o.setStatus(randomCommonStatus());
            o.setDomain(randomString());
        });

        // mock 套餐
        TenantPackageDO tenantPackage = randomPojo(TenantPackageDO.class,
                o -> o.setMenuIds(asSet(200L, 201L)));
        when(tenantPackageService.validTenantPackage(eq(reqVO.getPackageId()))).thenReturn(tenantPackage);
        // mock 所有角色
        RoleDO role100 = randomPojo(RoleDO.class, o -> o.setId(100L).setCode(RoleCodeEnum.TENANT_ADMIN.getCode()));
        role100.setTenantId(dbTenant.getId());
        RoleDO role101 = randomPojo(RoleDO.class, o -> o.setId(101L));
        role101.setTenantId(dbTenant.getId());
        when(roleService.getRoleListByStatus(isNull())).thenReturn(asList(role100, role101));
        // mock 每个角色的权限
        when(permissionService.getRoleMenuIds(eq(101L))).thenReturn(asSet(201L, 202L));

        // 调用
        tenantService.updateTenant(reqVO);
        // 校验是否更新正确
        TenantDO tenant = tenantMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, tenant);
        // verify 设置角色权限
        verify(permissionService).assignRoleMenu(eq(100L), eq(asSet(200L, 201L)));
        verify(permissionService).assignRoleMenu(eq(101L), eq(asSet(201L)));
    }

    @Test
    public void testUpdateTenant_notExists() {
        // 准备参数
        TenantUpdateReqVO reqVO = randomPojo(TenantUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> tenantService.updateTenant(reqVO), TENANT_NOT_EXISTS);
    }

    @Test
    public void testUpdateTenant_system() {
        // mock 数据
        TenantDO dbTenant = randomPojo(TenantDO.class, o -> o.setPackageId(PACKAGE_ID_SYSTEM));
        tenantMapper.insert(dbTenant);// @Sql: 先插入出一条存在的数据
        // 准备参数
        TenantUpdateReqVO reqVO = randomPojo(TenantUpdateReqVO.class, o -> {
            o.setId(dbTenant.getId()); // 设置更新的 ID
        });

        // 调用，校验业务异常
        assertServiceException(() -> tenantService.updateTenant(reqVO), TENANT_CAN_NOT_UPDATE_SYSTEM);
    }

    @Test
    public void testDeleteTenant_success() {
        // mock 数据
        TenantDO dbTenant = randomPojo(TenantDO.class,
                o -> o.setStatus(randomCommonStatus()));
        tenantMapper.insert(dbTenant);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbTenant.getId();

        // 调用
        tenantService.deleteTenant(id);
        // 校验数据不存在了
        assertNull(tenantMapper.selectById(id));
    }

    @Test
    public void testDeleteTenant_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> tenantService.deleteTenant(id), TENANT_NOT_EXISTS);
    }

    @Test
    public void testDeleteTenant_system() {
        // mock 数据
        TenantDO dbTenant = randomPojo(TenantDO.class, o -> o.setPackageId(PACKAGE_ID_SYSTEM));
        tenantMapper.insert(dbTenant);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbTenant.getId();

        // 调用, 并断言异常
        assertServiceException(() -> tenantService.deleteTenant(id), TENANT_CAN_NOT_UPDATE_SYSTEM);
    }

    @Test
    public void testGetTenant() {
        // mock 数据
        TenantDO dbTenant = randomPojo(TenantDO.class);
        tenantMapper.insert(dbTenant);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbTenant.getId();

        // 调用
        TenantDO result = tenantService.getTenant(id);
        // 校验存在
        assertPojoEquals(result, dbTenant);
    }

    @Test
    public void testGetTenantPage() {
        // mock 数据
        TenantDO dbTenant = randomPojo(TenantDO.class, o -> { // 等会查询到
            o.setName("万界星空科技");
            o.setContactName("星空斗士");
            o.setContactMobile("15601691300");
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
            o.setCreateTime(buildTime(2020, 12, 12));
        });
        tenantMapper.insert(dbTenant);
        // 测试 name 不匹配
        tenantMapper.insert(cloneIgnoreId(dbTenant, o -> o.setName(randomString())));
        // 测试 contactName 不匹配
        tenantMapper.insert(cloneIgnoreId(dbTenant, o -> o.setContactName(randomString())));
        // 测试 contactMobile 不匹配
        tenantMapper.insert(cloneIgnoreId(dbTenant, o -> o.setContactMobile(randomString())));
        // 测试 status 不匹配
        tenantMapper.insert(cloneIgnoreId(dbTenant, o -> o.setStatus(CommonStatusEnum.DISABLE.getStatus())));
        // 测试 createTime 不匹配
        tenantMapper.insert(cloneIgnoreId(dbTenant, o -> o.setCreateTime(buildTime(2021, 12, 12))));
        // 准备参数
        TenantPageReqVO reqVO = new TenantPageReqVO();
        reqVO.setName("万界星空科技");
        reqVO.setContactName("斗士");
        reqVO.setContactMobile("1560");
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        reqVO.setCreateTime(buildBetweenTime(2020, 12, 1, 2020, 12, 24));

        // 调用
        PageResult<TenantDO> pageResult = tenantService.getTenantPage(reqVO);
        // 断言
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
        assertPojoEquals(dbTenant, pageResult.getList().get(0));
    }

    @Test
    public void testGetTenantList() {
        // mock 数据
        TenantDO dbTenant = randomPojo(TenantDO.class, o -> { // 等会查询到
            o.setName("万界星空科技");
            o.setContactName("星空斗士");
            o.setContactMobile("15601691300");
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
            o.setCreateTime(buildTime(2020, 12, 12));
        });
        tenantMapper.insert(dbTenant);
        // 测试 name 不匹配
        tenantMapper.insert(cloneIgnoreId(dbTenant, o -> o.setName(randomString())));
        // 测试 contactName 不匹配
        tenantMapper.insert(cloneIgnoreId(dbTenant, o -> o.setContactName(randomString())));
        // 测试 contactMobile 不匹配
        tenantMapper.insert(cloneIgnoreId(dbTenant, o -> o.setContactMobile(randomString())));
        // 测试 status 不匹配
        tenantMapper.insert(cloneIgnoreId(dbTenant, o -> o.setStatus(CommonStatusEnum.DISABLE.getStatus())));
        // 测试 createTime 不匹配
        tenantMapper.insert(cloneIgnoreId(dbTenant, o -> o.setCreateTime(buildTime(2021, 12, 12))));
        // 准备参数
        TenantExportReqVO reqVO = new TenantExportReqVO();
        reqVO.setName("万界星空科技");
        reqVO.setContactName("斗士");
        reqVO.setContactMobile("1560");
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        reqVO.setCreateTime(buildBetweenTime(2020, 12, 1, 2020, 12, 24));

        // 调用
        List<TenantDO> list = tenantService.getTenantList(reqVO);
        // 断言
        assertEquals(1, list.size());
        assertPojoEquals(dbTenant, list.get(0));
    }

    @Test
    public void testGetTenantByName() {
        // mock 数据
        TenantDO dbTenant = randomPojo(TenantDO.class, o -> o.setName("万界星空科技"));
        tenantMapper.insert(dbTenant);// @Sql: 先插入出一条存在的数据

        // 调用
        TenantDO result = tenantService.getTenantByName("万界星空科技");
        // 校验存在
        assertPojoEquals(result, dbTenant);
    }

    @Test
    public void testGetTenantListByPackageId() {
        // mock 数据
        TenantDO dbTenant1 = randomPojo(TenantDO.class, o -> o.setPackageId(1L));
        tenantMapper.insert(dbTenant1);// @Sql: 先插入出一条存在的数据
        TenantDO dbTenant2 = randomPojo(TenantDO.class, o -> o.setPackageId(2L));
        tenantMapper.insert(dbTenant2);// @Sql: 先插入出一条存在的数据

        // 调用
        List<TenantDO> result = tenantService.getTenantListByPackageId(1L);
        assertEquals(1, result.size());
        assertPojoEquals(dbTenant1, result.get(0));
    }

    @Test
    public void testGetTenantCountByPackageId() {
        // mock 数据
        TenantDO dbTenant1 = randomPojo(TenantDO.class, o -> o.setPackageId(1L));
        tenantMapper.insert(dbTenant1);// @Sql: 先插入出一条存在的数据
        TenantDO dbTenant2 = randomPojo(TenantDO.class, o -> o.setPackageId(2L));
        tenantMapper.insert(dbTenant2);// @Sql: 先插入出一条存在的数据

        // 调用
        Long count = tenantService.getTenantCountByPackageId(1L);
        assertEquals(1, count);
    }

    @Test
    public void testHandleTenantInfo_disable() {
        // 准备参数
        TenantInfoHandler handler = mock(TenantInfoHandler.class);
        // mock 禁用
        when(tenantProperties.getEnable()).thenReturn(false);

        // 调用
        tenantService.handleTenantInfo(handler);
        // 断言
        verify(handler, never()).handle(any());
    }

    @Test
    public void testHandleTenantInfo_success() {
        // 准备参数
        TenantInfoHandler handler = mock(TenantInfoHandler.class);
        // mock 未禁用
        when(tenantProperties.getEnable()).thenReturn(true);
        // mock 租户
        TenantDO dbTenant = randomPojo(TenantDO.class);
        tenantMapper.insert(dbTenant);// @Sql: 先插入出一条存在的数据
        TenantContextHolder.setTenantId(dbTenant.getId());

        // 调用
        tenantService.handleTenantInfo(handler);
        // 断言
        verify(handler).handle(argThat(argument -> {
            assertPojoEquals(dbTenant, argument);
            return true;
        }));
    }

    @Test
    public void testHandleTenantMenu_disable() {
        // 准备参数
        TenantMenuHandler handler = mock(TenantMenuHandler.class);
        // mock 禁用
        when(tenantProperties.getEnable()).thenReturn(false);

        // 调用
        tenantService.handleTenantMenu(handler);
        // 断言
        verify(handler, never()).handle(any());
    }

    @Test // 系统租户的情况
    public void testHandleTenantMenu_system() {
        // 准备参数
        TenantMenuHandler handler = mock(TenantMenuHandler.class);
        // mock 未禁用
        when(tenantProperties.getEnable()).thenReturn(true);
        // mock 租户
        TenantDO dbTenant = randomPojo(TenantDO.class, o -> o.setPackageId(PACKAGE_ID_SYSTEM));
        tenantMapper.insert(dbTenant);// @Sql: 先插入出一条存在的数据
        TenantContextHolder.setTenantId(dbTenant.getId());
        // mock 菜单
        when(menuService.getMenuList()).thenReturn(Arrays.asList(randomPojo(MenuDO.class, o -> o.setId(100L)),
                        randomPojo(MenuDO.class, o -> o.setId(101L))));

        // 调用
        tenantService.handleTenantMenu(handler);
        // 断言
        verify(handler).handle(asSet(100L, 101L));
    }

    @Test // 普通租户的情况
    public void testHandleTenantMenu_normal() {
        // 准备参数
        TenantMenuHandler handler = mock(TenantMenuHandler.class);
        // mock 未禁用
        when(tenantProperties.getEnable()).thenReturn(true);
        // mock 租户
        TenantDO dbTenant = randomPojo(TenantDO.class, o -> o.setPackageId(200L));
        tenantMapper.insert(dbTenant);// @Sql: 先插入出一条存在的数据
        TenantContextHolder.setTenantId(dbTenant.getId());
        // mock 菜单
        when(tenantPackageService.getTenantPackage(eq(200L))).thenReturn(randomPojo(TenantPackageDO.class,
                o -> o.setMenuIds(asSet(100L, 101L))));

        // 调用
        tenantService.handleTenantMenu(handler);
        // 断言
        verify(handler).handle(asSet(100L, 101L));
    }
}
