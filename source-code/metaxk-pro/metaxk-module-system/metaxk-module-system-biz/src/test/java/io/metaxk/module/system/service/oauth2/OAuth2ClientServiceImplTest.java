package io.metaxk.module.system.service.oauth2;

import cn.hutool.core.map.MapUtil;
import io.metaxk.framework.common.enums.CommonStatusEnum;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.test.core.ut.BaseDbUnitTest;
import io.metaxk.module.system.controller.admin.oauth2.vo.client.OAuth2ClientCreateReqVO;
import io.metaxk.module.system.controller.admin.oauth2.vo.client.OAuth2ClientPageReqVO;
import io.metaxk.module.system.controller.admin.oauth2.vo.client.OAuth2ClientUpdateReqVO;
import io.metaxk.module.system.dal.dataobject.oauth2.OAuth2ClientDO;
import io.metaxk.module.system.dal.mysql.oauth2.OAuth2ClientMapper;
import io.metaxk.module.system.mq.producer.auth.OAuth2ClientProducer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Map;

import static io.metaxk.framework.common.util.object.ObjectUtils.cloneIgnoreId;
import static io.metaxk.framework.test.core.util.AssertUtils.assertPojoEquals;
import static io.metaxk.framework.test.core.util.AssertUtils.assertServiceException;
import static io.metaxk.framework.test.core.util.RandomUtils.*;
import static io.metaxk.module.system.enums.ErrorCodeConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

/**
* {@link OAuth2ClientServiceImpl} 的单元测试类
*
* @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
*/
@Import(OAuth2ClientServiceImpl.class)
public class OAuth2ClientServiceImplTest extends BaseDbUnitTest {

    @Resource
    private OAuth2ClientServiceImpl oauth2ClientService;

    @Resource
    private OAuth2ClientMapper oauth2ClientMapper;

    @MockBean
    private OAuth2ClientProducer oauth2ClientProducer;

    @Test
    public void testInitLocalCache() {
        // mock 数据
        OAuth2ClientDO clientDO1 = randomPojo(OAuth2ClientDO.class);
        oauth2ClientMapper.insert(clientDO1);
        OAuth2ClientDO clientDO2 = randomPojo(OAuth2ClientDO.class);
        oauth2ClientMapper.insert(clientDO2);

        // 调用
        oauth2ClientService.initLocalCache();
        // 断言 clientCache 缓存
        Map<String, OAuth2ClientDO> clientCache = oauth2ClientService.getClientCache();
        assertEquals(2, clientCache.size());
        assertPojoEquals(clientDO1, clientCache.get(clientDO1.getClientId()));
        assertPojoEquals(clientDO2, clientCache.get(clientDO2.getClientId()));
    }

    @Test
    public void testCreateOAuth2Client_success() {
        // 准备参数
        OAuth2ClientCreateReqVO reqVO = randomPojo(OAuth2ClientCreateReqVO.class,
                o -> o.setLogo(randomString()));

        // 调用
        Long oauth2ClientId = oauth2ClientService.createOAuth2Client(reqVO);
        // 断言
        assertNotNull(oauth2ClientId);
        // 校验记录的属性是否正确
        OAuth2ClientDO oAuth2Client = oauth2ClientMapper.selectById(oauth2ClientId);
        assertPojoEquals(reqVO, oAuth2Client);
        verify(oauth2ClientProducer).sendOAuth2ClientRefreshMessage();
    }

    @Test
    public void testUpdateOAuth2Client_success() {
        // mock 数据
        OAuth2ClientDO dbOAuth2Client = randomPojo(OAuth2ClientDO.class);
        oauth2ClientMapper.insert(dbOAuth2Client);// @Sql: 先插入出一条存在的数据
        // 准备参数
        OAuth2ClientUpdateReqVO reqVO = randomPojo(OAuth2ClientUpdateReqVO.class, o -> {
            o.setId(dbOAuth2Client.getId()); // 设置更新的 ID
            o.setLogo(randomString());
        });

        // 调用
        oauth2ClientService.updateOAuth2Client(reqVO);
        // 校验是否更新正确
        OAuth2ClientDO oAuth2Client = oauth2ClientMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, oAuth2Client);
        verify(oauth2ClientProducer).sendOAuth2ClientRefreshMessage();
    }

    @Test
    public void testUpdateOAuth2Client_notExists() {
        // 准备参数
        OAuth2ClientUpdateReqVO reqVO = randomPojo(OAuth2ClientUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> oauth2ClientService.updateOAuth2Client(reqVO), OAUTH2_CLIENT_NOT_EXISTS);
    }

    @Test
    public void testDeleteOAuth2Client_success() {
        // mock 数据
        OAuth2ClientDO dbOAuth2Client = randomPojo(OAuth2ClientDO.class);
        oauth2ClientMapper.insert(dbOAuth2Client);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbOAuth2Client.getId();

        // 调用
        oauth2ClientService.deleteOAuth2Client(id);
        // 校验数据不存在了
        assertNull(oauth2ClientMapper.selectById(id));
        verify(oauth2ClientProducer).sendOAuth2ClientRefreshMessage();
    }

    @Test
    public void testDeleteOAuth2Client_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> oauth2ClientService.deleteOAuth2Client(id), OAUTH2_CLIENT_NOT_EXISTS);
    }

    @Test
    public void testValidateClientIdExists_withId() {
        // mock 数据
        OAuth2ClientDO client = randomPojo(OAuth2ClientDO.class).setClientId("tudou");
        oauth2ClientMapper.insert(client);
        // 准备参数
        Long id = randomLongId();
        String clientId = "tudou";

        // 调用，不会报错
        assertServiceException(() -> oauth2ClientService.validateClientIdExists(id, clientId), OAUTH2_CLIENT_EXISTS);
    }

    @Test
    public void testValidateClientIdExists_noId() {
        // mock 数据
        OAuth2ClientDO client = randomPojo(OAuth2ClientDO.class).setClientId("tudou");
        oauth2ClientMapper.insert(client);
        // 准备参数
        String clientId = "tudou";

        // 调用，不会报错
        assertServiceException(() -> oauth2ClientService.validateClientIdExists(null, clientId), OAUTH2_CLIENT_EXISTS);
    }

    @Test
    public void testGetOAuth2Client() {
        // mock 数据
        OAuth2ClientDO clientDO = randomPojo(OAuth2ClientDO.class);
        oauth2ClientMapper.insert(clientDO);
        // 准备参数
        Long id = clientDO.getId();

        // 调用，并断言
        OAuth2ClientDO dbClientDO = oauth2ClientService.getOAuth2Client(id);
        assertPojoEquals(clientDO, dbClientDO);
    }

    @Test
    public void testGetOAuth2ClientPage() {
       // mock 数据
       OAuth2ClientDO dbOAuth2Client = randomPojo(OAuth2ClientDO.class, o -> { // 等会查询到
           o.setName("潜龙");
           o.setStatus(CommonStatusEnum.ENABLE.getStatus());
       });
       oauth2ClientMapper.insert(dbOAuth2Client);
       // 测试 name 不匹配
       oauth2ClientMapper.insert(cloneIgnoreId(dbOAuth2Client, o -> o.setName("凤凰")));
       // 测试 status 不匹配
       oauth2ClientMapper.insert(cloneIgnoreId(dbOAuth2Client, o -> o.setStatus(CommonStatusEnum.DISABLE.getStatus())));
       // 准备参数
       OAuth2ClientPageReqVO reqVO = new OAuth2ClientPageReqVO();
       reqVO.setName("龙");
       reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());

       // 调用
       PageResult<OAuth2ClientDO> pageResult = oauth2ClientService.getOAuth2ClientPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbOAuth2Client, pageResult.getList().get(0));
    }

    @Test
    public void testValidOAuthClientFromCache() {
        // mock 方法
        OAuth2ClientDO client = randomPojo(OAuth2ClientDO.class).setClientId("default")
                .setStatus(CommonStatusEnum.ENABLE.getStatus());
        OAuth2ClientDO client02 = randomPojo(OAuth2ClientDO.class).setClientId("disable")
                .setStatus(CommonStatusEnum.DISABLE.getStatus());
        Map<String, OAuth2ClientDO> clientCache = MapUtil.<String, OAuth2ClientDO>builder()
                .put(client.getClientId(), client)
                .put(client02.getClientId(), client02).build();
        oauth2ClientService.setClientCache(clientCache);

        // 调用，并断言
        assertServiceException(() -> oauth2ClientService.validOAuthClientFromCache(randomString(),
                null, null, null, null), OAUTH2_CLIENT_NOT_EXISTS);
        assertServiceException(() -> oauth2ClientService.validOAuthClientFromCache("disable",
                null, null, null, null), OAUTH2_CLIENT_DISABLE);
        assertServiceException(() -> oauth2ClientService.validOAuthClientFromCache("default",
                randomString(), null, null, null), OAUTH2_CLIENT_CLIENT_SECRET_ERROR);
        assertServiceException(() -> oauth2ClientService.validOAuthClientFromCache("default",
                null, randomString(), null, null), OAUTH2_CLIENT_AUTHORIZED_GRANT_TYPE_NOT_EXISTS);
        assertServiceException(() -> oauth2ClientService.validOAuthClientFromCache("default",
                null, null, Collections.singleton(randomString()), null), OAUTH2_CLIENT_SCOPE_OVER);
        assertServiceException(() -> oauth2ClientService.validOAuthClientFromCache("default",
                null, null, null, "test"), OAUTH2_CLIENT_REDIRECT_URI_NOT_MATCH, "test");
        // 成功调用（1：参数完整）
        OAuth2ClientDO result = oauth2ClientService.validOAuthClientFromCache(client.getClientId(), client.getSecret(),
                client.getAuthorizedGrantTypes().get(0), client.getScopes(), client.getRedirectUris().get(0));
        assertPojoEquals(client, result);
        // 成功调用（2：只有 clientId 参数）
        result = oauth2ClientService.validOAuthClientFromCache(client.getClientId());
        assertPojoEquals(client, result);
    }

}
