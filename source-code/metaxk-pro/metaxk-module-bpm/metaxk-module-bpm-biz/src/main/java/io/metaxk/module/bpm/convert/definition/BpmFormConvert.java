package io.metaxk.module.bpm.convert.definition;

import io.metaxk.module.bpm.controller.admin.definition.vo.form.BpmFormCreateReqVO;
import io.metaxk.module.bpm.controller.admin.definition.vo.form.BpmFormRespVO;
import io.metaxk.module.bpm.controller.admin.definition.vo.form.BpmFormSimpleRespVO;
import io.metaxk.module.bpm.controller.admin.definition.vo.form.BpmFormUpdateReqVO;
import io.metaxk.module.bpm.dal.dataobject.definition.BpmFormDO;
import io.metaxk.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 动态表单 Convert
 *
 * @author 星空斗士
 */
@Mapper
public interface BpmFormConvert {

    BpmFormConvert INSTANCE = Mappers.getMapper(BpmFormConvert.class);

    BpmFormDO convert(BpmFormCreateReqVO bean);

    BpmFormDO convert(BpmFormUpdateReqVO bean);

    BpmFormRespVO convert(BpmFormDO bean);

    List<BpmFormSimpleRespVO> convertList2(List<BpmFormDO> list);

    PageResult<BpmFormRespVO> convertPage(PageResult<BpmFormDO> page);

}
