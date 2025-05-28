package io.metaxk.module.system.service.sms;

import cn.hutool.core.map.MapUtil;
import io.metaxk.framework.common.core.KeyValue;
import io.metaxk.framework.common.enums.CommonStatusEnum;
import io.metaxk.framework.common.enums.UserTypeEnum;
import io.metaxk.framework.sms.core.client.SmsClient;
import io.metaxk.framework.sms.core.client.SmsClientFactory;
import io.metaxk.framework.sms.core.client.SmsCommonResult;
import io.metaxk.framework.sms.core.client.dto.SmsReceiveRespDTO;
import io.metaxk.framework.sms.core.client.dto.SmsSendRespDTO;
import io.metaxk.framework.test.core.ut.BaseMockitoUnitTest;
import io.metaxk.module.system.dal.dataobject.sms.SmsChannelDO;
import io.metaxk.module.system.dal.dataobject.sms.SmsTemplateDO;
import io.metaxk.module.system.dal.dataobject.user.AdminUserDO;
import io.metaxk.module.system.mq.message.sms.SmsSendMessage;
import io.metaxk.module.system.mq.producer.sms.SmsProducer;
import io.metaxk.module.system.service.member.MemberService;
import io.metaxk.module.system.service.user.AdminUserService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.hutool.core.util.RandomUtil.randomEle;
import static io.metaxk.framework.test.core.util.AssertUtils.assertServiceException;
import static io.metaxk.framework.test.core.util.RandomUtils.*;
import static io.metaxk.module.system.enums.ErrorCodeConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class SmsSendServiceImplTest extends BaseMockitoUnitTest {

    @InjectMocks
    private SmsSendServiceImpl smsService;

    @Mock
    private AdminUserService adminUserService;
    @Mock
    private MemberService memberService;
    @Mock
    private SmsChannelService smsChannelService;
    @Mock
    private SmsTemplateService smsTemplateService;
    @Mock
    private SmsLogService smsLogService;
    @Mock
    private SmsProducer smsProducer;

    @Mock
    private SmsClientFactory smsClientFactory;

    @Test
    public void testSendSingleSmsToAdmin() {
        // 准备参数
        Long userId = randomLongId();
        String templateCode = randomString();
        Map<String, Object> templateParams = MapUtil.<String, Object>builder().put("code", "1234")
                .put("op", "login").build();
        // mock adminUserService 的方法
        AdminUserDO user = randomPojo(AdminUserDO.class, o -> o.setMobile("15601691300"));
        when(adminUserService.getUser(eq(userId))).thenReturn(user);

        // mock SmsTemplateService 的方法
        SmsTemplateDO template = randomPojo(SmsTemplateDO.class, o -> {
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
            o.setContent("验证码为{code}, 操作为{op}");
            o.setParams(Lists.newArrayList("code", "op"));
        });
        when(smsTemplateService.getSmsTemplateByCodeFromCache(eq(templateCode))).thenReturn(template);
        String content = randomString();
        when(smsTemplateService.formatSmsTemplateContent(eq(template.getContent()), eq(templateParams)))
                .thenReturn(content);
        // mock SmsChannelService 的方法
        SmsChannelDO smsChannel = randomPojo(SmsChannelDO.class, o -> o.setStatus(CommonStatusEnum.ENABLE.getStatus()));
        when(smsChannelService.getSmsChannel(eq(template.getChannelId()))).thenReturn(smsChannel);
        // mock SmsLogService 的方法
        Long smsLogId = randomLongId();
        when(smsLogService.createSmsLog(eq(user.getMobile()), eq(userId), eq(UserTypeEnum.ADMIN.getValue()), eq(Boolean.TRUE), eq(template),
                eq(content), eq(templateParams))).thenReturn(smsLogId);

        // 调用
        Long resultSmsLogId = smsService.sendSingleSmsToAdmin(null, userId, templateCode, templateParams);
        // 断言
        assertEquals(smsLogId, resultSmsLogId);
        // 断言调用
        verify(smsProducer).sendSmsSendMessage(eq(smsLogId), eq(user.getMobile()),
                eq(template.getChannelId()), eq(template.getApiTemplateId()),
                eq(Lists.newArrayList(new KeyValue<>("code", "1234"), new KeyValue<>("op", "login"))));
    }

    @Test
    public void testSendSingleSmsToUser() {
        // 准备参数
        Long userId = randomLongId();
        String templateCode = randomString();
        Map<String, Object> templateParams = MapUtil.<String, Object>builder().put("code", "1234")
                .put("op", "login").build();
        // mock memberService 的方法
        String mobile = "15601691300";
        when(memberService.getMemberUserMobile(eq(userId))).thenReturn(mobile);

        // mock SmsTemplateService 的方法
        SmsTemplateDO template = randomPojo(SmsTemplateDO.class, o -> {
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
            o.setContent("验证码为{code}, 操作为{op}");
            o.setParams(Lists.newArrayList("code", "op"));
        });
        when(smsTemplateService.getSmsTemplateByCodeFromCache(eq(templateCode))).thenReturn(template);
        String content = randomString();
        when(smsTemplateService.formatSmsTemplateContent(eq(template.getContent()), eq(templateParams)))
                .thenReturn(content);
        // mock SmsChannelService 的方法
        SmsChannelDO smsChannel = randomPojo(SmsChannelDO.class, o -> o.setStatus(CommonStatusEnum.ENABLE.getStatus()));
        when(smsChannelService.getSmsChannel(eq(template.getChannelId()))).thenReturn(smsChannel);
        // mock SmsLogService 的方法
        Long smsLogId = randomLongId();
        when(smsLogService.createSmsLog(eq(mobile), eq(userId), eq(UserTypeEnum.MEMBER.getValue()), eq(Boolean.TRUE), eq(template),
                eq(content), eq(templateParams))).thenReturn(smsLogId);

        // 调用
        Long resultSmsLogId = smsService.sendSingleSmsToMember(null, userId, templateCode, templateParams);
        // 断言
        assertEquals(smsLogId, resultSmsLogId);
        // 断言调用
        verify(smsProducer).sendSmsSendMessage(eq(smsLogId), eq(mobile),
                eq(template.getChannelId()), eq(template.getApiTemplateId()),
                eq(Lists.newArrayList(new KeyValue<>("code", "1234"), new KeyValue<>("op", "login"))));
    }

    /**
     * 发送成功，当短信模板开启时
     */
    @Test
    public void testSendSingleSms_successWhenSmsTemplateEnable() {
        // 准备参数
        String mobile = randomString();
        Long userId = randomLongId();
        Integer userType = randomEle(UserTypeEnum.values()).getValue();
        String templateCode = randomString();
        Map<String, Object> templateParams = MapUtil.<String, Object>builder().put("code", "1234")
                .put("op", "login").build();
        // mock SmsTemplateService 的方法
        SmsTemplateDO template = randomPojo(SmsTemplateDO.class, o -> {
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
            o.setContent("验证码为{code}, 操作为{op}");
            o.setParams(Lists.newArrayList("code", "op"));
        });
        when(smsTemplateService.getSmsTemplateByCodeFromCache(eq(templateCode))).thenReturn(template);
        String content = randomString();
        when(smsTemplateService.formatSmsTemplateContent(eq(template.getContent()), eq(templateParams)))
                .thenReturn(content);
        // mock SmsChannelService 的方法
        SmsChannelDO smsChannel = randomPojo(SmsChannelDO.class, o -> o.setStatus(CommonStatusEnum.ENABLE.getStatus()));
        when(smsChannelService.getSmsChannel(eq(template.getChannelId()))).thenReturn(smsChannel);
        // mock SmsLogService 的方法
        Long smsLogId = randomLongId();
        when(smsLogService.createSmsLog(eq(mobile), eq(userId), eq(userType), eq(Boolean.TRUE), eq(template),
                eq(content), eq(templateParams))).thenReturn(smsLogId);

        // 调用
        Long resultSmsLogId = smsService.sendSingleSms(mobile, userId, userType, templateCode, templateParams);
        // 断言
        assertEquals(smsLogId, resultSmsLogId);
        // 断言调用
        verify(smsProducer).sendSmsSendMessage(eq(smsLogId), eq(mobile),
                eq(template.getChannelId()), eq(template.getApiTemplateId()),
                eq(Lists.newArrayList(new KeyValue<>("code", "1234"), new KeyValue<>("op", "login"))));
    }

    /**
     * 发送成功，当短信模板关闭时
     */
    @Test
    public void testSendSingleSms_successWhenSmsTemplateDisable() {
        // 准备参数
        String mobile = randomString();
        Long userId = randomLongId();
        Integer userType = randomEle(UserTypeEnum.values()).getValue();
        String templateCode = randomString();
        Map<String, Object> templateParams = MapUtil.<String, Object>builder().put("code", "1234")
                .put("op", "login").build();
        // mock SmsTemplateService 的方法
        SmsTemplateDO template = randomPojo(SmsTemplateDO.class, o -> {
            o.setStatus(CommonStatusEnum.DISABLE.getStatus());
            o.setContent("验证码为{code}, 操作为{op}");
            o.setParams(Lists.newArrayList("code", "op"));
        });
        when(smsTemplateService.getSmsTemplateByCodeFromCache(eq(templateCode))).thenReturn(template);
        String content = randomString();
        when(smsTemplateService.formatSmsTemplateContent(eq(template.getContent()), eq(templateParams)))
                .thenReturn(content);
        // mock SmsChannelService 的方法
        SmsChannelDO smsChannel = randomPojo(SmsChannelDO.class, o -> o.setStatus(CommonStatusEnum.ENABLE.getStatus()));
        when(smsChannelService.getSmsChannel(eq(template.getChannelId()))).thenReturn(smsChannel);
        // mock SmsLogService 的方法
        Long smsLogId = randomLongId();
        when(smsLogService.createSmsLog(eq(mobile), eq(userId), eq(userType), eq(Boolean.FALSE), eq(template),
                eq(content), eq(templateParams))).thenReturn(smsLogId);

        // 调用
        Long resultSmsLogId = smsService.sendSingleSms(mobile, userId, userType, templateCode, templateParams);
        // 断言
        assertEquals(smsLogId, resultSmsLogId);
        // 断言调用
        verify(smsProducer, times(0)).sendSmsSendMessage(anyLong(), anyString(),
                anyLong(), any(), anyList());
    }

    @Test
    public void testCheckSmsTemplateValid_notExists() {
        // 准备参数
        String templateCode = randomString();
        // mock 方法

        // 调用，并断言异常
        assertServiceException(() -> smsService.validateSmsTemplate(templateCode),
                SMS_SEND_TEMPLATE_NOT_EXISTS);
    }

    @Test
    public void testBuildTemplateParams_paramMiss() {
        // 准备参数
        SmsTemplateDO template = randomPojo(SmsTemplateDO.class,
                o -> o.setParams(Lists.newArrayList("code")));
        Map<String, Object> templateParams = new HashMap<>();
        // mock 方法

        // 调用，并断言异常
        assertServiceException(() -> smsService.buildTemplateParams(template, templateParams),
                SMS_SEND_MOBILE_TEMPLATE_PARAM_MISS, "code");
    }

    @Test
    public void testCheckMobile_notExists() {
        // 准备参数
        // mock 方法

        // 调用，并断言异常
        assertServiceException(() -> smsService.validateMobile(null),
                SMS_SEND_MOBILE_NOT_EXISTS);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDoSendSms() {
        // 准备参数
        SmsSendMessage message = randomPojo(SmsSendMessage.class);
        // mock SmsClientFactory 的方法
        SmsClient smsClient = spy(SmsClient.class);
        when(smsClientFactory.getSmsClient(eq(message.getChannelId()))).thenReturn(smsClient);
        // mock SmsClient 的方法
        SmsCommonResult<SmsSendRespDTO> sendResult = randomPojo(SmsCommonResult.class, SmsSendRespDTO.class);
        sendResult.setData(randomPojo(SmsSendRespDTO.class));
        when(smsClient.sendSms(eq(message.getLogId()), eq(message.getMobile()), eq(message.getApiTemplateId()),
                eq(message.getTemplateParams()))).thenReturn(sendResult);

        // 调用
        smsService.doSendSms(message);
        // 断言
        verify(smsLogService).updateSmsSendResult(eq(message.getLogId()),
                eq(sendResult.getCode()), eq(sendResult.getMsg()), eq(sendResult.getApiCode()),
                eq(sendResult.getApiMsg()), eq(sendResult.getApiRequestId()), eq(sendResult.getData().getSerialNo()));
    }

    @Test
    public void testReceiveSmsStatus() throws Throwable {
        // 准备参数
        String channelCode = randomString();
        String text = randomString();
        // mock SmsClientFactory 的方法
        SmsClient smsClient = spy(SmsClient.class);
        when(smsClientFactory.getSmsClient(eq(channelCode))).thenReturn(smsClient);
        // mock SmsClient 的方法
        List<SmsReceiveRespDTO> receiveResults = randomPojoList(SmsReceiveRespDTO.class);

        // 调用
        smsService.receiveSmsStatus(channelCode, text);
        // 断言
        receiveResults.forEach(result -> smsLogService.updateSmsReceiveResult(eq(result.getLogId()), eq(result.getSuccess()),
                eq(result.getReceiveTime()), eq(result.getErrorCode()), eq(result.getErrorCode())));
    }

}
