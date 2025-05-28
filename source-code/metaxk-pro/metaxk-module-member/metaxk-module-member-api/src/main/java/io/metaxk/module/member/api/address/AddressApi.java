package io.metaxk.module.member.api.address;

import io.metaxk.module.member.api.address.dto.AddressRespDTO;

/**
 * 用户收件地址 API 接口
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
public interface AddressApi {

    /**
     * 获得用户收件地址
     *
     * @param id 收件地址编号
     * @param userId 用户编号
     * @return 用户收件地址
     */
    AddressRespDTO getAddress(Long id, Long userId);

}
