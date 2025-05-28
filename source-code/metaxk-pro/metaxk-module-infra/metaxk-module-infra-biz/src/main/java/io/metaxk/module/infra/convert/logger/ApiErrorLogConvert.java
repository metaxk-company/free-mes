package io.metaxk.module.infra.convert.logger;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.infra.api.logger.dto.ApiErrorLogCreateReqDTO;
import io.metaxk.module.infra.controller.admin.logger.vo.apierrorlog.ApiErrorLogExcelVO;
import io.metaxk.module.infra.controller.admin.logger.vo.apierrorlog.ApiErrorLogRespVO;
import io.metaxk.module.infra.dal.dataobject.logger.ApiErrorLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * API 错误日志 Convert
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Mapper
public interface ApiErrorLogConvert {

    ApiErrorLogConvert INSTANCE = Mappers.getMapper(ApiErrorLogConvert.class);

    ApiErrorLogRespVO convert(ApiErrorLogDO bean);

    PageResult<ApiErrorLogRespVO> convertPage(PageResult<ApiErrorLogDO> page);

    List<ApiErrorLogExcelVO> convertList02(List<ApiErrorLogDO> list);

    ApiErrorLogDO convert(ApiErrorLogCreateReqDTO bean);

}
