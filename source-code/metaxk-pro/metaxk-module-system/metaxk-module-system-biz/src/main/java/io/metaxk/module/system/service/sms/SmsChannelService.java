package io.metaxk.module.system.service.sms;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.system.controller.admin.sms.vo.channel.SmsChannelCreateReqVO;
import io.metaxk.module.system.controller.admin.sms.vo.channel.SmsChannelPageReqVO;
import io.metaxk.module.system.controller.admin.sms.vo.channel.SmsChannelUpdateReqVO;
import io.metaxk.module.system.dal.dataobject.sms.SmsChannelDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 短信渠道 Service 接口
 *
 * @author zzf
 * @date 2021/1/25 9:24
 */
public interface SmsChannelService {

    /**
     * 初始化短信客户端
     */
    void initLocalCache();

    /**
     * 创建短信渠道
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSmsChannel(@Valid SmsChannelCreateReqVO createReqVO);

    /**
     * 更新短信渠道
     *
     * @param updateReqVO 更新信息
     */
    void updateSmsChannel(@Valid SmsChannelUpdateReqVO updateReqVO);

    /**
     * 删除短信渠道
     *
     * @param id 编号
     */
    void deleteSmsChannel(Long id);

    /**
     * 获得短信渠道
     *
     * @param id 编号
     * @return 短信渠道
     */
    SmsChannelDO getSmsChannel(Long id);

    /**
     * 获得所有短信渠道列表
     *
     * @return 短信渠道列表
     */
    List<SmsChannelDO> getSmsChannelList();

    /**
     * 获得短信渠道分页
     *
     * @param pageReqVO 分页查询
     * @return 短信渠道分页
     */
    PageResult<SmsChannelDO> getSmsChannelPage(SmsChannelPageReqVO pageReqVO);

}
