package io.metaxk.module.system.convert.mail;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.system.controller.admin.mail.vo.log.MailLogRespVO;
import io.metaxk.module.system.dal.dataobject.mail.MailLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MailLogConvert {

    MailLogConvert INSTANCE = Mappers.getMapper(MailLogConvert.class);

    PageResult<MailLogRespVO> convertPage(PageResult<MailLogDO> pageResult);

    MailLogRespVO convert(MailLogDO bean);

}
