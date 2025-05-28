package io.metaxk.module.report.convert.goview;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.report.controller.admin.goview.vo.project.GoViewProjectCreateReqVO;
import io.metaxk.module.report.controller.admin.goview.vo.project.GoViewProjectRespVO;
import io.metaxk.module.report.controller.admin.goview.vo.project.GoViewProjectUpdateReqVO;
import io.metaxk.module.report.dal.dataobject.goview.GoViewProjectDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GoViewProjectConvert {

    GoViewProjectConvert INSTANCE = Mappers.getMapper(GoViewProjectConvert.class);

    GoViewProjectDO convert(GoViewProjectCreateReqVO bean);

    GoViewProjectDO convert(GoViewProjectUpdateReqVO bean);

    GoViewProjectRespVO convert(GoViewProjectDO bean);

    PageResult<GoViewProjectRespVO> convertPage(PageResult<GoViewProjectDO> page);

}
