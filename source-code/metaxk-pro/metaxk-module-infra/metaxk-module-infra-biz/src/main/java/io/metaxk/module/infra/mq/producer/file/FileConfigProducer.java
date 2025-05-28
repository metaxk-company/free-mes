package io.metaxk.module.infra.mq.producer.file;

import io.metaxk.framework.mq.core.RedisMQTemplate;
import io.metaxk.module.infra.mq.message.file.FileConfigRefreshMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 文件配置相关消息的 Producer
 */
@Component
public class FileConfigProducer {

    @Resource
    private RedisMQTemplate redisMQTemplate;

    /**
     * 发送 {@link FileConfigRefreshMessage} 消息
     */
    public void sendFileConfigRefreshMessage() {
        FileConfigRefreshMessage message = new FileConfigRefreshMessage();
        redisMQTemplate.send(message);
    }

}
