package io.metaxk.framework.file.config;

import io.metaxk.framework.file.core.client.FileClientFactory;
import io.metaxk.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 文件配置类
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@AutoConfiguration
public class MetaxkFileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
