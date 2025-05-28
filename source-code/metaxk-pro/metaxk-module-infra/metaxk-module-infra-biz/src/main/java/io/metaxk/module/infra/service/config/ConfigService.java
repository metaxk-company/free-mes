package io.metaxk.module.infra.service.config;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.infra.controller.admin.config.vo.ConfigCreateReqVO;
import io.metaxk.module.infra.controller.admin.config.vo.ConfigExportReqVO;
import io.metaxk.module.infra.controller.admin.config.vo.ConfigPageReqVO;
import io.metaxk.module.infra.controller.admin.config.vo.ConfigUpdateReqVO;
import io.metaxk.module.infra.dal.dataobject.config.ConfigDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 参数配置 Service 接口
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
public interface ConfigService {

    /**
     * 创建参数配置
     *
     * @param reqVO 创建信息
     * @return 配置编号
     */
    Long createConfig(@Valid ConfigCreateReqVO reqVO);

    /**
     * 更新参数配置
     *
     * @param reqVO 更新信息
     */
    void updateConfig(@Valid ConfigUpdateReqVO reqVO);

    /**
     * 删除参数配置
     *
     * @param id 配置编号
     */
    void deleteConfig(Long id);

    /**
     * 获得参数配置
     *
     * @param id 配置编号
     * @return 参数配置
     */
    ConfigDO getConfig(Long id);

    /**
     * 根据参数键，获得参数配置
     *
     * @param key 配置键
     * @return 参数配置
     */
    ConfigDO getConfigByKey(String key);

    /**
     * 获得参数配置分页列表
     *
     * @param reqVO 分页条件
     * @return 分页列表
     */
    PageResult<ConfigDO> getConfigPage(@Valid ConfigPageReqVO reqVO);

    /**
     * 获得参数配置列表
     *
     * @param reqVO 列表
     * @return 列表
     */
    List<ConfigDO> getConfigList(@Valid ConfigExportReqVO reqVO);


}
