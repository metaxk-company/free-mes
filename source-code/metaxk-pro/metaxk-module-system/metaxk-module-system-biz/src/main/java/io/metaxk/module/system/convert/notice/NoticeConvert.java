package io.metaxk.module.system.convert.notice;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.system.controller.admin.notice.vo.NoticeCreateReqVO;
import io.metaxk.module.system.controller.admin.notice.vo.NoticeRespVO;
import io.metaxk.module.system.controller.admin.notice.vo.NoticeUpdateReqVO;
import io.metaxk.module.system.dal.dataobject.notice.NoticeDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NoticeConvert {

    NoticeConvert INSTANCE = Mappers.getMapper(NoticeConvert.class);

    PageResult<NoticeRespVO> convertPage(PageResult<NoticeDO> page);

    NoticeRespVO convert(NoticeDO bean);

    NoticeDO convert(NoticeUpdateReqVO bean);

    NoticeDO convert(NoticeCreateReqVO bean);

}
