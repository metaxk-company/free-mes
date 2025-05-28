package io.metaxk.module.system.mq.consumer.sms;

import io.metaxk.framework.mq.core.pubsub.AbstractChannelMessageListener;
import io.metaxk.module.system.mq.message.sms.SmsChannelRefreshMessage;
import io.metaxk.module.system.service.sms.SmsChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 针对 {@link SmsChannelRefreshMessage} 的消费者
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Component
@Slf4j
public class SmsChannelRefreshConsumer extends AbstractChannelMessageListener<SmsChannelRefreshMessage> {

    @Resource
    private SmsChannelService smsChannelService;

    @Override
    public void onMessage(SmsChannelRefreshMessage message) {
        log.info("[onMessage][收到 SmsChannel 刷新消息]");
        smsChannelService.initLocalCache();
    }

}
