package io.metaxk.module.system.mq.message.permission;

import io.metaxk.framework.mq.core.pubsub.AbstractChannelMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户与角色的数据刷新 Message
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRoleRefreshMessage extends AbstractChannelMessage {

    @Override
    public String getChannel() {
        return "system.user-role.refresh";
    }

}
