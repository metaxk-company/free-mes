package io.metaxk.framework.sms.core.client;

import io.metaxk.framework.common.exception.ErrorCode;
import io.metaxk.framework.sms.core.enums.SmsFrameworkErrorCodeConstants;

import java.util.function.Function;

/**
 * 将 API 的错误码，转换为通用的错误码
 *
 * @see SmsCommonResult
 * @see SmsFrameworkErrorCodeConstants
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
public interface SmsCodeMapping extends Function<String, ErrorCode> {
}
