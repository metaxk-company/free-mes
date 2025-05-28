package io.metaxk.module.member.service.auth;

import io.metaxk.module.member.controller.app.auth.vo.*;

import javax.validation.Valid;

/**
 * 会员的认证 Service 接口
 *
 * 提供用户的账号密码登录、token 的校验等认证相关的功能
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
public interface MemberAuthService {

    /**
     * 手机 + 密码登录
     *
     * @param reqVO 登录信息
     * @return 登录结果
     */
    AppAuthLoginRespVO login(@Valid AppAuthLoginReqVO reqVO);

    /**
     * 基于 token 退出登录
     *
     * @param token token
     */
    void logout(String token);

    /**
     * 手机 + 验证码登陆
     *
     * @param reqVO 登陆信息
     * @return 登录结果
     */
    AppAuthLoginRespVO smsLogin(@Valid AppAuthSmsLoginReqVO reqVO);

    /**
     * 社交登录，使用 code 授权码
     *
     * @param reqVO 登录信息
     * @return 登录结果
     */
    AppAuthLoginRespVO socialLogin(@Valid AppAuthSocialLoginReqVO reqVO);

    /**
     * 微信小程序的一键登录
     *
     * @param reqVO 登录信息
     * @return 登录结果
     */
    AppAuthLoginRespVO weixinMiniAppLogin(AppAuthWeixinMiniAppLoginReqVO reqVO);

    /**
     * 获得社交认证 URL
     *
     * @param type 社交平台类型
     * @param redirectUri 跳转地址
     * @return 认证 URL
     */
    String getSocialAuthorizeUrl(Integer type, String redirectUri);

    /**
     * 修改用户密码
     * @param userId 用户id
     * @param userReqVO 用户请求实体类
     */
    void updatePassword(Long userId, AppAuthUpdatePasswordReqVO userReqVO);

    /**
     * 忘记密码
     * @param userReqVO 用户请求实体类
     */
    void resetPassword(AppAuthResetPasswordReqVO userReqVO);

    /**
     * 给用户发送短信验证码
     *
     * @param userId 用户编号
     * @param reqVO 发送信息
     */
    void sendSmsCode(Long userId, AppAuthSmsSendReqVO reqVO);

    /**
     * 刷新访问令牌
     *
     * @param refreshToken 刷新令牌
     * @return 登录结果
     */
    AppAuthLoginRespVO refreshToken(String refreshToken);

}
