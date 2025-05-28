package io.metaxk.module.system.convert.sms;

import io.metaxk.module.system.controller.admin.sms.vo.log.SmsLogExcelVO;
import io.metaxk.module.system.controller.admin.sms.vo.log.SmsLogRespVO;
import io.metaxk.module.system.dal.dataobject.sms.SmsLogDO;
import io.metaxk.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 短信日志 Convert
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Mapper
public interface SmsLogConvert {

    SmsLogConvert INSTANCE = Mappers.getMapper(SmsLogConvert.class);

    SmsLogRespVO convert(SmsLogDO bean);

    List<SmsLogRespVO> convertList(List<SmsLogDO> list);

    PageResult<SmsLogRespVO> convertPage(PageResult<SmsLogDO> page);

    List<SmsLogExcelVO> convertList02(List<SmsLogDO> list);

}
