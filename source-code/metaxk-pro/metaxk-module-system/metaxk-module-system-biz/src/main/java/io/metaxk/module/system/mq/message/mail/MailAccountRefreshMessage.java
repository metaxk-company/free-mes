package io.metaxk.module.system.mq.message.mail;

import io.metaxk.framework.mq.core.pubsub.AbstractChannelMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 邮箱账号的数据刷新 Message
 *
 * @author wangjingyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MailAccountRefreshMessage extends AbstractChannelMessage {

    @Override
    public String getChannel() {
        return "system.mail-account.refresh";
    }

}
