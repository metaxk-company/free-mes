package io.metaxk.module.bpm.framework.web.config;

import io.metaxk.framework.swagger.config.MetaxkSwaggerAutoConfiguration;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * bpm 模块的 web 组件的 Configuration
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Configuration(proxyBeanMethods = false)
public class BpmWebConfiguration {

    /**
     * bpm 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi bpmGroupedOpenApi() {
        return MetaxkSwaggerAutoConfiguration.buildGroupedOpenApi("bpm");
    }

}
