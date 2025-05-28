package io.metaxk.module.mes.dal.dataobject.md;


import io.metaxk.module.mes.common.EntityCommon;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;

/**
 * 供应商
 * @author 万界星空
 */
@TableName("md_vendor")
@Data
public class Vendor extends EntityCommon {

    /**
     * 供应商ID
     */
    private Long id;

    /**
     * 外部系统id
     */
    private String externalId;

    /**
     * 供应商编码
     */
    private String vendorCode;
    /**
     * 供应商名称
     */
    private String vendorName;
    /**
     * 供应商简称
     */
    private String vendorNick;
    /**
     * 供应商英文名称
     */
    private String vendorEn;
    /**
     * 供应商简介
     */
    private String vendorDes;
    /**
     * 供应商LOGO地址
     */
    private String vendorLogo;
    /**
     * 供应商等级
     */
    private String vendorLevel;
    /**
     * 供应商评分
     */
    private Integer vendorScore;
    /**
     * 供应商地址
     */
    private String address;
    /**
     * 供应商官网地址
     */
    private String website;
    /**
     * 供应商邮箱地址
     */
    private String email;
    /**
     * 供应商电话
     */
    private String tel;
    /**
     * 联系人1
     */
    private String contact1;
    /**
     * 联系人1-电话
     */
    private String contact1Tel;
    /**
     * 联系人1-邮箱
     */
    private String contact1Email;
    /**
     * 联系人2
     */
    private String contact2;
    /**
     * 联系人2-电话
     */
    private String contact2Tel;
    /**
     * 联系人2-邮箱
     */
    private String contact2Email;
    /**
     * 统一社会信用代码
     */
    private String creditCode;
    /**
     * 是否启用
     */
    private String enableFlag;
    /**
     * 备注
     */
    private String remark;

    /**
     * 逻辑删除字段
     */
    @TableLogic
    private Boolean deleted;
}
