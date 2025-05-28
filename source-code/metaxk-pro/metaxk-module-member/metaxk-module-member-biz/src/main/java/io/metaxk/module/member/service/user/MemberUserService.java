package io.metaxk.module.member.service.user;

import io.metaxk.framework.common.validation.Mobile;
import io.metaxk.module.member.controller.app.user.vo.AppUserUpdateMobileReqVO;
import io.metaxk.module.member.dal.dataobject.user.MemberUserDO;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

/**
 * 会员用户 Service 接口
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
public interface MemberUserService {

    /**
     * 通过手机查询用户
     *
     * @param mobile 手机
     * @return 用户对象
     */
    MemberUserDO getUserByMobile(String mobile);

    /**
     * 基于用户昵称，模糊匹配用户列表
     *
     * @param nickname 用户昵称，模糊匹配
     * @return 用户信息的列表
     */
    List<MemberUserDO> getUserListByNickname(String nickname);

    /**
     * 基于手机号创建用户。
     * 如果用户已经存在，则直接进行返回
     *
     * @param mobile 手机号
     * @param registerIp 注册 IP
     * @return 用户对象
     */
    MemberUserDO createUserIfAbsent(@Mobile String mobile, String registerIp);

    /**
     * 更新用户的最后登陆信息
     *
     * @param id 用户编号
     * @param loginIp 登陆 IP
     */
    void updateUserLogin(Long id, String loginIp);

    /**
     * 通过用户 ID 查询用户
     *
     * @param id 用户ID
     * @return 用户对象信息
     */
    MemberUserDO getUser(Long id);

    /**
     * 通过用户 ID 查询用户们
     *
     * @param ids 用户 ID
     * @return 用户对象信息数组
     */
    List<MemberUserDO> getUserList(Collection<Long> ids);

    /**
     * 修改用户昵称
     * @param userId 用户id
     * @param nickname 用户新昵称
     */
    void updateUserNickname(Long userId, String nickname);

    /**
     * 修改用户头像
     * @param userId 用户id
     * @param inputStream 头像文件
     * @return 头像url
     */
    String updateUserAvatar(Long userId, InputStream inputStream) throws Exception;

    /**
     * 修改手机
     * @param userId 用户id
     * @param reqVO 请求实体
     */
    void updateUserMobile(Long userId, AppUserUpdateMobileReqVO reqVO);

    /**
     * 判断密码是否匹配
     *
     * @param rawPassword 未加密的密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    boolean isPasswordMatch(String rawPassword, String encodedPassword);

}
