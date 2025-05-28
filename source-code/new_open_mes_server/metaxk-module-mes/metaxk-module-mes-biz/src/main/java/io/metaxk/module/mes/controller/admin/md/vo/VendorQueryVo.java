package io.metaxk.module.mes.controller.admin.md.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.metaxk.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static io.metaxk.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 供应商分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VendorQueryVo extends PageParam {

    @Schema(description = "供应商编码")
    private String vendorCode;

    @Schema(description = "供应商名称", example = "王五")
    private String vendorName;

    @Schema(description = "供应商简称")
    private String vendorNick;

    @Schema(description = "供应商英文名称")
    private String vendorEn;

    @Schema(description = "供应商简介")
    private String vendorDes;

    @Schema(description = "供应商LOGO地址")
    private String vendorLogo;

    @Schema(description = "供应商等级")
    private String vendorLevel;

    @Schema(description = "供应商评分")
    private Integer vendorScore;

    @Schema(description = "供应商地址")
    private String address;

    @Schema(description = "供应商官网地址")
    private String website;

    @Schema(description = "供应商邮箱地址")
    private String email;

    @Schema(description = "供应商电话")
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

    @Schema(description = "备注", example = "你说的对")
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
