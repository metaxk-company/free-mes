package io.metaxk.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文档地址
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Getter
@AllArgsConstructor
public enum DocumentEnum {

    REDIS_INSTALL("https://www.metaxk.io", "Redis 安装文档"),
    TENANT("https://doc.metaxk.io", "SaaS 多租户文档");

    private final String url;
    private final String memo;

}
