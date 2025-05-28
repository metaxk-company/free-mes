package io.metaxk.module.member.controller.app.auth.vo;

import io.metaxk.framework.common.validation.InEnum;
import io.metaxk.framework.common.validation.Mobile;
import io.metaxk.module.system.enums.sms.SmsSceneEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Schema(description = "用户 APP - 发送手机验证码 Request VO")
@Data
@Accessors(chain = true)
public class AppAuthSmsSendReqVO {

    @Schema(description = "手机号", example = "15601691234")
    @Mobile
    private String mobile;

    @Schema(description = "发送场景,对应 SmsSceneEnum 枚举", example = "1")
    @NotNull(message = "发送场景不能为空")
    @InEnum(SmsSceneEnum.class)
    private Integer scene;

}
