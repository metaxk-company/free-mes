package io.metaxk.module.infra.controller.admin.logger.vo.apierrorlog;

import io.metaxk.framework.excel.core.annotations.DictFormat;
import io.metaxk.framework.excel.core.convert.DictConvert;
import io.metaxk.module.infra.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * API 错误日志 Excel VO
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Data
public class ApiErrorLogExcelVO {

    @ExcelProperty("编号")
    private Integer id;

    @ExcelProperty("链路追踪编号")
    private String traceId;

    @ExcelProperty("用户编号")
    private Integer userId;

    @ExcelProperty(value = "用户类型", converter = DictConvert.class)
    @DictFormat(io.metaxk.module.system.enums.DictTypeConstants.USER_TYPE)
    private Integer userType;

    @ExcelProperty("应用名")
    private String applicationName;

    @ExcelProperty("请求方法名")
    private String requestMethod;

    @ExcelProperty("请求地址")
    private String requestUrl;

    @ExcelProperty("请求参数")
    private String requestParams;

    @ExcelProperty("用户 IP")
    private String userIp;

    @ExcelProperty("浏览器 UA")
    private String userAgent;

    @ExcelProperty("异常发生时间")
    private LocalDateTime exceptionTime;

    @ExcelProperty("异常名")
    private String exceptionName;

    @ExcelProperty("异常导致的消息")
    private String exceptionMessage;

    @ExcelProperty("异常导致的根消息")
    private String exceptionRootCauseMessage;

    @ExcelProperty("异常的栈轨迹")
    private String exceptionStackTrace;

    @ExcelProperty("异常发生的类全名")
    private String exceptionClassName;

    @ExcelProperty("异常发生的类文件")
    private String exceptionFileName;

    @ExcelProperty("异常发生的方法名")
    private String exceptionMethodName;

    @ExcelProperty("异常发生的方法所在行")
    private Integer exceptionLineNumber;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty(value = "处理状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.API_ERROR_LOG_PROCESS_STATUS)
    private Integer processStatus;

    @ExcelProperty("处理时间")
    private LocalDateTime processTime;

    @ExcelProperty("处理用户编号")
    private Integer processUserId;

}
