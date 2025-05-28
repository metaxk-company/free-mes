package io.metaxk.module.system.service.mail;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.test.core.ut.BaseDbUnitTest;
import io.metaxk.module.system.controller.admin.mail.vo.account.MailAccountCreateReqVO;
import io.metaxk.module.system.controller.admin.mail.vo.account.MailAccountPageReqVO;
import io.metaxk.module.system.controller.admin.mail.vo.account.MailAccountUpdateReqVO;
import io.metaxk.module.system.dal.dataobject.mail.MailAccountDO;
import io.metaxk.module.system.dal.mysql.mail.MailAccountMapper;
import io.metaxk.module.system.mq.producer.mail.MailProducer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static io.metaxk.framework.common.util.object.ObjectUtils.cloneIgnoreId;
import static io.metaxk.framework.test.core.util.AssertUtils.assertPojoEquals;
import static io.metaxk.framework.test.core.util.AssertUtils.assertServiceException;
import static io.metaxk.framework.test.core.util.RandomUtils.*;
import static io.metaxk.module.system.enums.ErrorCodeConstants.MAIL_ACCOUNT_NOT_EXISTS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
* {@link MailAccountServiceImpl} 的单元测试类
*
* @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
*/
@Import(MailAccountServiceImpl.class)
public class MailAccountServiceImplTest extends BaseDbUnitTest {

    @Resource
    private MailAccountServiceImpl mailAccountService;

    @Resource
    private MailAccountMapper mailAccountMapper;

    @MockBean
    private MailTemplateService mailTemplateService;
    @MockBean
    private MailProducer mailProducer;

    @Test
    public void testInitLocalCache() {
        MailAccountDO accountDO1 = randomPojo(MailAccountDO.class);
        mailAccountMapper.insert(accountDO1);
        MailAccountDO accountDO02 = randomPojo(MailAccountDO.class);
        mailAccountMapper.insert(accountDO02);

        // 调用
        mailAccountService.initLocalCache();
        // 断言 mailAccountCache 缓存
        Map<Long, MailAccountDO> mailAccountCache = mailAccountService.getMailAccountCache();
        assertPojoEquals(accountDO1, mailAccountCache.get(accountDO1.getId()));
        assertPojoEquals(accountDO02, mailAccountCache.get(accountDO02.getId()));
    }

    @Test
    public void testCreateMailAccount_success() {
        // 准备参数
        MailAccountCreateReqVO reqVO = randomPojo(MailAccountCreateReqVO.class, o -> o.setMail(randomEmail()));

        // 调用
        Long mailAccountId = mailAccountService.createMailAccount(reqVO);
        // 断言
        assertNotNull(mailAccountId);
        // 校验记录的属性是否正确
        MailAccountDO mailAccount = mailAccountMapper.selectById(mailAccountId);
        assertPojoEquals(reqVO, mailAccount);
        verify(mailProducer).sendMailAccountRefreshMessage();
    }

    @Test
    public void testUpdateMailAccount_success() {
        // mock 数据
        MailAccountDO dbMailAccount = randomPojo(MailAccountDO.class);
        mailAccountMapper.insert(dbMailAccount);// @Sql: 先插入出一条存在的数据
        // 准备参数
        MailAccountUpdateReqVO reqVO = randomPojo(MailAccountUpdateReqVO.class, o -> {
            o.setId(dbMailAccount.getId()); // 设置更新的 ID
            o.setMail(randomEmail());
        });

        // 调用
        mailAccountService.updateMailAccount(reqVO);
        // 校验是否更新正确
        MailAccountDO mailAccount = mailAccountMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, mailAccount);
        verify(mailProducer).sendMailAccountRefreshMessage();
    }

    @Test
    public void testUpdateMailAccount_notExists() {
        // 准备参数
        MailAccountUpdateReqVO reqVO = randomPojo(MailAccountUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> mailAccountService.updateMailAccount(reqVO), MAIL_ACCOUNT_NOT_EXISTS);
    }

    @Test
    public void testDeleteMailAccount_success() {
        // mock 数据
        MailAccountDO dbMailAccount = randomPojo(MailAccountDO.class);
        mailAccountMapper.insert(dbMailAccount);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbMailAccount.getId();
        // mock 方法（无关联模版）
        when(mailTemplateService.countByAccountId(eq(id))).thenReturn(0L);

        // 调用
        mailAccountService.deleteMailAccount(id);
       // 校验数据不存在了
       assertNull(mailAccountMapper.selectById(id));
        verify(mailProducer).sendMailAccountRefreshMessage();
    }

    @Test
    public void testGetMailAccountFromCache() {
        // mock 数据
        MailAccountDO dbMailAccount = randomPojo(MailAccountDO.class);
        mailAccountMapper.insert(dbMailAccount);// @Sql: 先插入出一条存在的数据
        mailAccountService.initLocalCache();
        // 准备参数
        Long id = dbMailAccount.getId();

        // 调用
        MailAccountDO mailAccount = mailAccountService.getMailAccountFromCache(id);
        // 断言
        assertPojoEquals(dbMailAccount, mailAccount);
    }

    @Test
    public void testDeleteMailAccount_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> mailAccountService.deleteMailAccount(id), MAIL_ACCOUNT_NOT_EXISTS);
    }

    @Test
    public void testGetMailAccountPage() {
       // mock 数据
       MailAccountDO dbMailAccount = randomPojo(MailAccountDO.class, o -> { // 等会查询到
           o.setMail("768@qq.com");
           o.setUsername("metaxk");
       });
       mailAccountMapper.insert(dbMailAccount);
       // 测试 mail 不匹配
       mailAccountMapper.insert(cloneIgnoreId(dbMailAccount, o -> o.setMail("788@qq.com")));
       // 测试 username 不匹配
       mailAccountMapper.insert(cloneIgnoreId(dbMailAccount, o -> o.setUsername("tudou")));
       // 准备参数
       MailAccountPageReqVO reqVO = new MailAccountPageReqVO();
       reqVO.setMail("768");
       reqVO.setUsername("metaxk");

       // 调用
       PageResult<MailAccountDO> pageResult = mailAccountService.getMailAccountPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbMailAccount, pageResult.getList().get(0));
    }

    @Test
    public void testGetMailAccount() {
        // mock 数据
        MailAccountDO dbMailAccount = randomPojo(MailAccountDO.class);
        mailAccountMapper.insert(dbMailAccount);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbMailAccount.getId();

        // 调用
        MailAccountDO mailAccount = mailAccountService.getMailAccount(id);
        // 断言
        assertPojoEquals(dbMailAccount, mailAccount);
    }

    @Test
    public void testGetMailAccountList() {
        // mock 数据
        MailAccountDO dbMailAccount01 = randomPojo(MailAccountDO.class);
        mailAccountMapper.insert(dbMailAccount01);
        MailAccountDO dbMailAccount02 = randomPojo(MailAccountDO.class);
        mailAccountMapper.insert(dbMailAccount02);
        // 准备参数

        // 调用
        List<MailAccountDO> list = mailAccountService.getMailAccountList();
        // 断言
        assertEquals(2, list.size());
        assertPojoEquals(dbMailAccount01, list.get(0));
        assertPojoEquals(dbMailAccount02, list.get(1));
    }

}
