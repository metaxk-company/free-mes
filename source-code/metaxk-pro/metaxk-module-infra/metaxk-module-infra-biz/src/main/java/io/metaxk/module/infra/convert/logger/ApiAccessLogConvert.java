package io.metaxk.module.infra.convert.logger;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.infra.api.logger.dto.ApiAccessLogCreateReqDTO;
import io.metaxk.module.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogExcelVO;
import io.metaxk.module.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogRespVO;
import io.metaxk.module.infra.dal.dataobject.logger.ApiAccessLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * API 访问日志 Convert
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Mapper
public interface ApiAccessLogConvert {

    ApiAccessLogConvert INSTANCE = Mappers.getMapper(ApiAccessLogConvert.class);

    ApiAccessLogRespVO convert(ApiAccessLogDO bean);

    List<ApiAccessLogRespVO> convertList(List<ApiAccessLogDO> list);

    PageResult<ApiAccessLogRespVO> convertPage(PageResult<ApiAccessLogDO> page);

    List<ApiAccessLogExcelVO> convertList02(List<ApiAccessLogDO> list);

    ApiAccessLogDO convert(ApiAccessLogCreateReqDTO bean);

}
