package io.metaxk.framework.sms.core.client.impl.debug;

import io.metaxk.framework.common.exception.ErrorCode;
import io.metaxk.framework.common.exception.enums.GlobalErrorCodeConstants;
import io.metaxk.framework.sms.core.client.SmsCodeMapping;
import io.metaxk.framework.sms.core.enums.SmsFrameworkErrorCodeConstants;

import java.util.Objects;

/**
 * 钉钉的 SmsCodeMapping 实现类
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
public class DebugDingTalkCodeMapping implements SmsCodeMapping {

    @Override
    public ErrorCode apply(String apiCode) {
        return Objects.equals(apiCode, "0") ? GlobalErrorCodeConstants.SUCCESS : SmsFrameworkErrorCodeConstants.SMS_UNKNOWN;
    }

}
