package io.metaxk.module.system.service.mail;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.system.controller.admin.mail.vo.template.MailTemplateCreateReqVO;
import io.metaxk.module.system.controller.admin.mail.vo.template.MailTemplatePageReqVO;
import io.metaxk.module.system.controller.admin.mail.vo.template.MailTemplateUpdateReqVO;
import io.metaxk.module.system.dal.dataobject.mail.MailTemplateDO;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 邮件模版 Service 接口
 *
 * @author wangjingyi
 * @since 2022-03-21
 */
public interface MailTemplateService {

    /**
     * 初始化邮件模版的本地缓存
     */
    void initLocalCache();

    /**
     * 邮件模版创建
     *
     * @param createReqVO 邮件信息
     * @return 编号
     */
    Long createMailTemplate(@Valid MailTemplateCreateReqVO createReqVO);

    /**
     * 邮件模版修改
     *
     * @param updateReqVO 邮件信息
     */
    void updateMailTemplate(@Valid MailTemplateUpdateReqVO updateReqVO);

    /**
     * 邮件模版删除
     *
     * @param id 编号
     */
    void deleteMailTemplate(Long id);

    /**
     * 获取邮件模版
     *
     * @param id 编号
     * @return 邮件模版
     */
    MailTemplateDO getMailTemplate(Long id);

    /**
     * 获取邮件模版分页
     *
     * @param pageReqVO 模版信息
     * @return 邮件模版分页信息
     */
    PageResult<MailTemplateDO> getMailTemplatePage(MailTemplatePageReqVO pageReqVO);

    /**
     * 获取邮件模板数组
     *
     * @return 模版数组
     */
    List<MailTemplateDO> getMailTemplateList();

    /**
     * 从缓存中获取邮件模版
     *
     * @param code 模板编码
     * @return 邮件模板
     */
    MailTemplateDO getMailTemplateByCodeFromCache(String code);

    /**
     * 邮件模版内容合成
     *
     * @param content 邮件模版
     * @param params 合成参数
     * @return 格式化后的内容
     */
    String formatMailTemplateContent(String content, Map<String, Object> params);

    /**
     * 获得指定邮件账号下的邮件模板数量
     *
     * @param accountId 账号编号
     * @return 数量
     */
    long countByAccountId(Long accountId);

}
