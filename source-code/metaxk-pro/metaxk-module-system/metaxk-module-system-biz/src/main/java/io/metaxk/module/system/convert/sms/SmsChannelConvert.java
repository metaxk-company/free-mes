package io.metaxk.module.system.convert.sms;

import io.metaxk.module.system.controller.admin.sms.vo.channel.SmsChannelCreateReqVO;
import io.metaxk.module.system.controller.admin.sms.vo.channel.SmsChannelRespVO;
import io.metaxk.module.system.controller.admin.sms.vo.channel.SmsChannelSimpleRespVO;
import io.metaxk.module.system.controller.admin.sms.vo.channel.SmsChannelUpdateReqVO;
import io.metaxk.module.system.dal.dataobject.sms.SmsChannelDO;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.sms.core.property.SmsChannelProperties;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 短信渠道 Convert
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Mapper
public interface SmsChannelConvert {

    SmsChannelConvert INSTANCE = Mappers.getMapper(SmsChannelConvert.class);

    SmsChannelDO convert(SmsChannelCreateReqVO bean);

    SmsChannelDO convert(SmsChannelUpdateReqVO bean);

    SmsChannelRespVO convert(SmsChannelDO bean);

    List<SmsChannelRespVO> convertList(List<SmsChannelDO> list);

    PageResult<SmsChannelRespVO> convertPage(PageResult<SmsChannelDO> page);

    List<SmsChannelProperties> convertList02(List<SmsChannelDO> list);

    List<SmsChannelSimpleRespVO> convertList03(List<SmsChannelDO> list);

}
