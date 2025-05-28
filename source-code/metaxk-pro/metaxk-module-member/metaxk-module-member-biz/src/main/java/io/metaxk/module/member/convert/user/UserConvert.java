package io.metaxk.module.member.convert.user;

import io.metaxk.module.member.api.user.dto.MemberUserRespDTO;
import io.metaxk.module.member.controller.app.user.vo.AppUserInfoRespVO;
import io.metaxk.module.member.dal.dataobject.user.MemberUserDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    AppUserInfoRespVO convert(MemberUserDO bean);

    MemberUserRespDTO convert2(MemberUserDO bean);

    List<MemberUserRespDTO> convertList2(List<MemberUserDO> list);

}
