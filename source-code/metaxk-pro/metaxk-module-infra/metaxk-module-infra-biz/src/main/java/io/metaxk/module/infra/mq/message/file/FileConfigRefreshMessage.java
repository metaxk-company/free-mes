package io.metaxk.module.infra.mq.message.file;

import io.metaxk.framework.mq.core.pubsub.AbstractChannelMessage;
import lombok.Data;

/**
 * 文件配置数据刷新 Message
 */
@Data
public class FileConfigRefreshMessage extends AbstractChannelMessage {

    @Override
    public String getChannel() {
        return "infra.file-config.refresh";
    }

}
