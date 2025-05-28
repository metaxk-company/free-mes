package io.metaxk.module.member.convert.address;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.member.api.address.dto.AddressRespDTO;
import io.metaxk.module.member.controller.app.address.vo.AppAddressCreateReqVO;
import io.metaxk.module.member.controller.app.address.vo.AppAddressRespVO;
import io.metaxk.module.member.controller.app.address.vo.AppAddressUpdateReqVO;
import io.metaxk.module.member.dal.dataobject.address.AddressDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 用户收件地址 Convert
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Mapper
public interface AddressConvert {

    AddressConvert INSTANCE = Mappers.getMapper(AddressConvert.class);

    AddressDO convert(AppAddressCreateReqVO bean);

    AddressDO convert(AppAddressUpdateReqVO bean);

    AppAddressRespVO convert(AddressDO bean);

    List<AppAddressRespVO> convertList(List<AddressDO> list);

    PageResult<AppAddressRespVO> convertPage(PageResult<AddressDO> page);

    AddressRespDTO convert02(AddressDO bean);

}
