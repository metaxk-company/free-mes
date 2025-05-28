package io.metaxk.module.system.convert.errorcode;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.system.api.errorcode.dto.ErrorCodeAutoGenerateReqDTO;
import io.metaxk.module.system.api.errorcode.dto.ErrorCodeRespDTO;
import io.metaxk.module.system.controller.admin.errorcode.vo.ErrorCodeCreateReqVO;
import io.metaxk.module.system.controller.admin.errorcode.vo.ErrorCodeExcelVO;
import io.metaxk.module.system.controller.admin.errorcode.vo.ErrorCodeRespVO;
import io.metaxk.module.system.controller.admin.errorcode.vo.ErrorCodeUpdateReqVO;
import io.metaxk.module.system.dal.dataobject.errorcode.ErrorCodeDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 错误码 Convert
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Mapper
public interface ErrorCodeConvert {

    ErrorCodeConvert INSTANCE = Mappers.getMapper(ErrorCodeConvert.class);

    ErrorCodeDO convert(ErrorCodeCreateReqVO bean);

    ErrorCodeDO convert(ErrorCodeUpdateReqVO bean);

    ErrorCodeRespVO convert(ErrorCodeDO bean);

    List<ErrorCodeRespVO> convertList(List<ErrorCodeDO> list);

    PageResult<ErrorCodeRespVO> convertPage(PageResult<ErrorCodeDO> page);

    List<ErrorCodeExcelVO> convertList02(List<ErrorCodeDO> list);

    ErrorCodeDO convert(ErrorCodeAutoGenerateReqDTO bean);

    List<ErrorCodeRespDTO> convertList03(List<ErrorCodeDO> list);

}
