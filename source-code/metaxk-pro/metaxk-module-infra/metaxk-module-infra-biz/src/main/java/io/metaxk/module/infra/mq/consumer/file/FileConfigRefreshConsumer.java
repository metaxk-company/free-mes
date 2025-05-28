package io.metaxk.module.infra.mq.consumer.file;

import io.metaxk.framework.mq.core.pubsub.AbstractChannelMessageListener;
import io.metaxk.module.infra.mq.message.file.FileConfigRefreshMessage;
import io.metaxk.module.infra.service.file.FileConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 针对 {@link FileConfigRefreshMessage} 的消费者
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Component
@Slf4j
public class FileConfigRefreshConsumer extends AbstractChannelMessageListener<FileConfigRefreshMessage> {

    @Resource
    private FileConfigService fileConfigService;

    @Override
    public void onMessage(FileConfigRefreshMessage message) {
        log.info("[onMessage][收到 FileConfig 刷新消息]");
        fileConfigService.initLocalCache();
    }

}
