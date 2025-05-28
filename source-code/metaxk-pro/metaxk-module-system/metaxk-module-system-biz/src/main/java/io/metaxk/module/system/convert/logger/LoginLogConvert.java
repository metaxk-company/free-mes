package io.metaxk.module.system.convert.logger;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.system.api.logger.dto.LoginLogCreateReqDTO;
import io.metaxk.module.system.controller.admin.logger.vo.loginlog.LoginLogExcelVO;
import io.metaxk.module.system.controller.admin.logger.vo.loginlog.LoginLogRespVO;
import io.metaxk.module.system.dal.dataobject.logger.LoginLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LoginLogConvert {

    LoginLogConvert INSTANCE = Mappers.getMapper(LoginLogConvert.class);

    PageResult<LoginLogRespVO> convertPage(PageResult<LoginLogDO> page);

    List<LoginLogExcelVO> convertList(List<LoginLogDO> list);

    LoginLogDO convert(LoginLogCreateReqDTO bean);

}
