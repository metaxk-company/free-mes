package io.metaxk.framework.operatelog.config;

import io.metaxk.framework.operatelog.core.aop.OperateLogAspect;
import io.metaxk.framework.operatelog.core.service.OperateLogFrameworkService;
import io.metaxk.framework.operatelog.core.service.OperateLogFrameworkServiceImpl;
import io.metaxk.module.system.api.logger.OperateLogApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class MetaxkOperateLogAutoConfiguration {

    @Bean
    public OperateLogAspect operateLogAspect() {
        return new OperateLogAspect();
    }

    @Bean
    public OperateLogFrameworkService operateLogFrameworkService(OperateLogApi operateLogApi) {
        return new OperateLogFrameworkServiceImpl(operateLogApi);
    }

}
