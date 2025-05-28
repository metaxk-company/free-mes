package io.metaxk.framework.pay.config;

import io.metaxk.framework.pay.core.client.PayClientFactory;
import io.metaxk.framework.pay.core.client.impl.PayClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 支付配置类
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@AutoConfiguration
@EnableConfigurationProperties(PayProperties.class)
public class MetaxkPayAutoConfiguration {

    @Bean
    public PayClientFactory payClientFactory() {
        return new PayClientFactoryImpl();
    }

}
