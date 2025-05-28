package io.metaxk.module.member.api.user;

import io.metaxk.module.member.api.user.dto.MemberUserRespDTO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static io.metaxk.framework.common.util.collection.CollectionUtils.convertMap;

/**
 * 会员用户的 API 接口
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
public interface MemberUserApi {

    /**
     * 获得会员用户信息
     *
     * @param id 用户编号
     * @return 用户信息
     */
    MemberUserRespDTO getUser(Long id);

    /**
     * 获得会员用户信息们
     *
     * @param ids 用户编号的数组
     * @return 用户信息们
     */
    List<MemberUserRespDTO> getUsers(Collection<Long> ids);

    /**
     * 获得会员用户 Map
     *
     * @param ids 用户编号的数组
     * @return 会员用户 Map
     */
    default Map<Long, MemberUserRespDTO> getUserMap(Collection<Long> ids) {
        return convertMap(getUsers(ids), MemberUserRespDTO::getId);
    }

    /**
     * 基于用户昵称，模糊匹配用户列表
     *
     * @param nickname 用户昵称，模糊匹配
     * @return 用户信息的列表
     */
    List<MemberUserRespDTO> getUserListByNickname(String nickname);

    /**
     * 基于手机号，精准匹配用户
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    MemberUserRespDTO getUserByMobile(String mobile);

}
