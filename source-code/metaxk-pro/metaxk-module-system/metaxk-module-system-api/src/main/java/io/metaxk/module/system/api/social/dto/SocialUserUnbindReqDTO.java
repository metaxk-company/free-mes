package io.metaxk.module.system.api.social.dto;

import io.metaxk.framework.common.enums.UserTypeEnum;
import io.metaxk.framework.common.validation.InEnum;
import io.metaxk.module.system.enums.social.SocialTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 社交绑定 Request DTO，使用 code 授权码
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Data
public class SocialUserUnbindReqDTO {

    /**
     * 用户编号
     */
    @NotNull(message = "用户编号不能为空")
    private Long userId;
    /**
     * 用户类型
     */
    @InEnum(UserTypeEnum.class)
    @NotNull(message = "用户类型不能为空")
    private Integer userType;

    /**
     * 社交平台的类型
     */
    @InEnum(SocialTypeEnum.class)
    @NotNull(message = "社交平台的类型不能为空")
    private Integer type;

    /**
     * 社交平台的 unionId
     */
    @NotEmpty(message = "社交平台的 unionId 不能为空")
    private String unionId;

}
