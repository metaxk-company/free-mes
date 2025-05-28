package io.metaxk.module.infra.service.logger;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.infra.api.logger.dto.ApiAccessLogCreateReqDTO;
import io.metaxk.module.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogExportReqVO;
import io.metaxk.module.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogPageReqVO;
import io.metaxk.module.infra.convert.logger.ApiAccessLogConvert;
import io.metaxk.module.infra.dal.dataobject.logger.ApiAccessLogDO;
import io.metaxk.module.infra.dal.mysql.logger.ApiAccessLogMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * API 访问日志 Service 实现类
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Service
@Validated
public class ApiAccessLogServiceImpl implements ApiAccessLogService {

    @Resource
    private ApiAccessLogMapper apiAccessLogMapper;

    @Override
    public void createApiAccessLog(ApiAccessLogCreateReqDTO createDTO) {
        ApiAccessLogDO apiAccessLog = ApiAccessLogConvert.INSTANCE.convert(createDTO);
        apiAccessLogMapper.insert(apiAccessLog);
    }

    @Override
    public PageResult<ApiAccessLogDO> getApiAccessLogPage(ApiAccessLogPageReqVO pageReqVO) {
        return apiAccessLogMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ApiAccessLogDO> getApiAccessLogList(ApiAccessLogExportReqVO exportReqVO) {
        return apiAccessLogMapper.selectList(exportReqVO);
    }

}
