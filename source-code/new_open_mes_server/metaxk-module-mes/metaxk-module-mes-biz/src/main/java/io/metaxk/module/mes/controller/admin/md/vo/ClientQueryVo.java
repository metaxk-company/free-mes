package io.metaxk.module.mes.controller.admin.md.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.metaxk.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static io.metaxk.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * @author 万界星空
 */
@Schema(description = "管理后台 - 客户分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ClientQueryVo extends PageParam {

    @Schema(description = "客户编码")
    private String clientCode;

    @Schema(description = "客户名称", example = "李四")
    private String clientName;

    @Schema(description = "客户简称")
    private String clientNick;

    @Schema(description = "客户英文名称")
    private String clientEn;

    @Schema(description = "客户简介")
    private String clientDes;

    @Schema(description = "客户LOGO地址")
    private String clientLogo;

    @Schema(description = "客户类型", example = "2")
    private String clientType;

    @Schema(description = "客户地址")
    private String address;

    @Schema(description = "客户官网地址")
    private String website;

    @Schema(description = "客户邮箱地址")
    private String email;

    @Schema(description = "客户电话")
    private String tel;

    @Schema(description = "联系人1")
    private String contact1;

    @Schema(description = "联系人1-电话")
    private String contact1Tel;

    @Schema(description = "联系人1-邮箱")
    private String contact1Email;

    @Schema(description = "联系人2")
    private String contact2;

    @Schema(description = "联系人2-电话")
    private String contact2Tel;

    @Schema(description = "联系人2-邮箱")
    private String contact2Email;

    @Schema(description = "统一社会信用代码")
    private String creditCode;

    @Schema(description = "是否启用")
    private String enableFlag;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "预留字段1")
    private String attr1;

    @Schema(description = "预留字段2")
    private String attr2;

    @Schema(description = "预留字段3")
    private Integer attr3;

    @Schema(description = "预留字段4")
    private Integer attr4;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
