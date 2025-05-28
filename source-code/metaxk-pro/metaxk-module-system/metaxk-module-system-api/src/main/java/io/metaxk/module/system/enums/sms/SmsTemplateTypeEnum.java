package io.metaxk.module.system.enums.sms;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 短信的模板类型枚举
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Getter
@AllArgsConstructor
public enum SmsTemplateTypeEnum {

    VERIFICATION_CODE(1), // 验证码
    NOTICE(2), // 通知
    PROMOTION(3), // 营销
    ;

    /**
     * 类型
     */
    private final int type;

}
