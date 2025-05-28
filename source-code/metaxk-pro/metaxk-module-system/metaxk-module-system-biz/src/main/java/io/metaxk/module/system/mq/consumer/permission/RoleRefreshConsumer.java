package io.metaxk.module.system.mq.consumer.permission;

import io.metaxk.framework.mq.core.pubsub.AbstractChannelMessageListener;
import io.metaxk.module.system.mq.message.permission.RoleRefreshMessage;
import io.metaxk.module.system.service.permission.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 针对 {@link RoleRefreshMessage} 的消费者
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Component
@Slf4j
public class RoleRefreshConsumer extends AbstractChannelMessageListener<RoleRefreshMessage> {

    @Resource
    private RoleService roleService;

    @Override
    public void onMessage(RoleRefreshMessage message) {
        log.info("[onMessage][收到 Role 刷新消息]");
        roleService.initLocalCache();
    }

}
