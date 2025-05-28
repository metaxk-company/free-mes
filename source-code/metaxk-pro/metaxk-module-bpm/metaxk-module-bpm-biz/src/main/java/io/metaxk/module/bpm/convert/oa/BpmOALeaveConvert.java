package io.metaxk.module.bpm.convert.oa;

import io.metaxk.module.bpm.controller.admin.oa.vo.BpmOALeaveCreateReqVO;
import io.metaxk.module.bpm.controller.admin.oa.vo.BpmOALeaveRespVO;
import io.metaxk.module.bpm.dal.dataobject.oa.BpmOALeaveDO;
import io.metaxk.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 请假申请 Convert
 *
 * @author 星空斗士
 */
@Mapper
public interface BpmOALeaveConvert {

    BpmOALeaveConvert INSTANCE = Mappers.getMapper(BpmOALeaveConvert.class);

    BpmOALeaveDO convert(BpmOALeaveCreateReqVO bean);

    BpmOALeaveRespVO convert(BpmOALeaveDO bean);

    List<BpmOALeaveRespVO> convertList(List<BpmOALeaveDO> list);

    PageResult<BpmOALeaveRespVO> convertPage(PageResult<BpmOALeaveDO> page);

}
