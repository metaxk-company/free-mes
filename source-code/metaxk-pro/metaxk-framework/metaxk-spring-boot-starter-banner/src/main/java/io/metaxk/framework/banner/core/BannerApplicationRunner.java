package io.metaxk.framework.banner.core;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.util.ClassUtils;

import java.util.concurrent.TimeUnit;

/**
 * 项目启动成功后，提供文档相关的地址
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Slf4j
public class BannerApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ThreadUtil.execute(() -> {
            ThreadUtil.sleep(1, TimeUnit.SECONDS); // 延迟 1 秒，保证输出到结尾
            log.info("\n----------------------------------------------------------\n\t" +
                                            "万界星空平台启动成功！\n\t" +
                                            "官方网站: \t{} \n" +
                            "----------------------------------------------------------",
                       "https://www.metaxk.io");
/*
            // 数据报表
            if (isNotPresent("io.metaxk.module.report.framework.security.config.SecurityConfiguration")) {
                System.out.println("[报表模块 metaxk-module-report - 已禁用][参考 https://doc.metaxk.io/report/ 开启]");
            }
            // 工作流
            if (isNotPresent("io.metaxk.framework.flowable.config.MetaxkFlowableConfiguration")) {
                System.out.println("[工作流模块 metaxk-module-bpm - 已禁用][参考 https://doc.metaxk.io/bpm/ 开启]");
            }
            // 微信公众号
            if (isNotPresent("io.metaxk.module.mp.framework.mp.config.MpConfiguration")) {
                System.out.println("[微信公众号 metaxk-module-mp - 已禁用][参考 https://doc.metaxk.io/mp/build/ 开启]");
            }
            // 商城
            if (isNotPresent("io.metaxk.module.trade.framework.web.config.TradeWebConfiguration")) {
                System.out.println("[商城系统 metaxk-module-mall - 已禁用][参考 https://doc.metaxk.io/mall/build/ 开启]");
            }

 */
        });
    }

    private static boolean isNotPresent(String className) {
        return !ClassUtils.isPresent(className, ClassUtils.getDefaultClassLoader());
    }

}
