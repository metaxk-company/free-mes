package io.metaxk.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 项目的启动类
 * @author 万界星空科技
 */
@SuppressWarnings("SpringComponentScan") // 忽略 IDEA 无法识别 ${metaxk.info.base-package}
@SpringBootApplication(scanBasePackages = {"${metaxk.info.base-package}.module"})
//开启定时任务
@EnableScheduling
public class MetaxkServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetaxkServerApplication.class, args);
    }




}
