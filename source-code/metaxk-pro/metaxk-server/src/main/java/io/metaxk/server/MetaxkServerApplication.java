package io.metaxk.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目的启动类
 *
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@SuppressWarnings("SpringComponentScan") // 忽略 IDEA 无法识别 ${metaxk.info.base-package}
@SpringBootApplication(scanBasePackages = {"${metaxk.info.base-package}.server", "${metaxk.info.base-package}.module"})
public class MetaxkServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(MetaxkServerApplication.class, args);
//        new SpringApplicationBuilder(MetaxkServerApplication.class)
//                .applicationStartup(new BufferingApplicationStartup(20480))
//                .run(args);

    }

}
