package io.metaxk.module.system.convert.sensitiveword;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.system.controller.admin.sensitiveword.vo.SensitiveWordCreateReqVO;
import io.metaxk.module.system.controller.admin.sensitiveword.vo.SensitiveWordExcelVO;
import io.metaxk.module.system.controller.admin.sensitiveword.vo.SensitiveWordRespVO;
import io.metaxk.module.system.controller.admin.sensitiveword.vo.SensitiveWordUpdateReqVO;
import io.metaxk.module.system.dal.dataobject.sensitiveword.SensitiveWordDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 敏感词 Convert
 *
 * @author 永不言败
 */
@Mapper
public interface SensitiveWordConvert {

    SensitiveWordConvert INSTANCE = Mappers.getMapper(SensitiveWordConvert.class);

    SensitiveWordDO convert(SensitiveWordCreateReqVO bean);

    SensitiveWordDO convert(SensitiveWordUpdateReqVO bean);

    SensitiveWordRespVO convert(SensitiveWordDO bean);

    List<SensitiveWordRespVO> convertList(List<SensitiveWordDO> list);

    PageResult<SensitiveWordRespVO> convertPage(PageResult<SensitiveWordDO> page);

    List<SensitiveWordExcelVO> convertList02(List<SensitiveWordDO> list);

}
