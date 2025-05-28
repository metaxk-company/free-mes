package io.metaxk.module.member.convert.auth;

import io.metaxk.module.member.controller.app.auth.vo.*;
import io.metaxk.module.member.controller.app.social.vo.AppSocialUserUnbindReqVO;
import io.metaxk.module.system.api.oauth2.dto.OAuth2AccessTokenRespDTO;
import io.metaxk.module.system.api.sms.dto.code.SmsCodeSendReqDTO;
import io.metaxk.module.system.api.sms.dto.code.SmsCodeUseReqDTO;
import io.metaxk.module.system.api.social.dto.SocialUserBindReqDTO;
import io.metaxk.module.system.api.social.dto.SocialUserUnbindReqDTO;
import io.metaxk.module.system.enums.sms.SmsSceneEnum;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthConvert {

    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    SocialUserBindReqDTO convert(Long userId, Integer userType, AppAuthSocialLoginReqVO reqVO);
    SocialUserUnbindReqDTO convert(Long userId, Integer userType, AppSocialUserUnbindReqVO reqVO);

    SmsCodeSendReqDTO convert(AppAuthSmsSendReqVO reqVO);
    SmsCodeUseReqDTO convert(AppAuthResetPasswordReqVO reqVO, SmsSceneEnum scene, String usedIp);
    SmsCodeUseReqDTO convert(AppAuthSmsLoginReqVO reqVO, Integer scene, String usedIp);

    AppAuthLoginRespVO convert(OAuth2AccessTokenRespDTO bean);

}
