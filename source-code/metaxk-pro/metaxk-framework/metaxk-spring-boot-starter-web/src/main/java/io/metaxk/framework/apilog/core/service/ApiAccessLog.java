package io.metaxk.framework.apilog.core.service;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * API 访问日志
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Data
public class ApiAccessLog {

    /**
     * 链路追踪编号
     */
    private String traceId;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户类型
     */
    private Integer userType;
    /**
     * 应用名
     */
    @NotNull(message = "应用名不能为空")
    private String applicationName;

    /**
     * 请求方法名
     */
    @NotNull(message = "http 请求方法不能为空")
    private String requestMethod;
    /**
     * 访问地址
     */
    @NotNull(message = "访问地址不能为空")
    private String requestUrl;
    /**
     * 请求参数
     */
    @NotNull(message = "请求参数不能为空")
    private String requestParams;
    /**
     * 用户 IP
     */
    @NotNull(message = "ip 不能为空")
    private String userIp;
    /**
     * 浏览器 UA
     */
    @NotNull(message = "User-Agent 不能为空")
    private String userAgent;

    /**
     * 开始请求时间
     */
    @NotNull(message = "开始请求时间不能为空")
    private LocalDateTime beginTime;
    /**
     * 结束请求时间
     */
    @NotNull(message = "结束请求时间不能为空")
    private LocalDateTime endTime;
    /**
     * 执行时长，单位：毫秒
     */
    @NotNull(message = "执行时长不能为空")
    private Integer duration;
    /**
     * 结果码
     */
    @NotNull(message = "错误码不能为空")
    private Integer resultCode;
    /**
     * 结果提示
     */
    private String resultMsg;

}
