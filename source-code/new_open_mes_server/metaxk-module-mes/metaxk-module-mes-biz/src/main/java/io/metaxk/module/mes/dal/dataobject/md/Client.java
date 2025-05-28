package io.metaxk.module.mes.dal.dataobject.md;

import io.metaxk.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;

/**
 * 客户
 * @author 万界星空
 */
@TableName("md_client")
@Data
public class Client extends BaseDO {

    /**
     * 客户ID
     */
    private Long id;

    /**
     * 外部系统id
     */
    private String externalId;

    /**
     * 客户编码
     */
    private String clientCode;
    /**
     * 客户名称
     */
    private String clientName;
    /**
     * 客户简称
     */
    private String clientNick;
    /**
     * 客户英文名称
     */
    private String clientEn;
    /**
     * 客户简介
     */
    private String clientDes;
    /**
     * 客户LOGO地址
     */
    private String clientLogo;
    /**
     * 客户类型
     */
    private String clientType;
    /**
     * 客户地址
     */
    private String address;
    /**
     * 客户官网地址
     */
    private String website;
    /**
     * 客户邮箱地址
     */
    private String email;
    /**
     * 客户电话
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
     * 出库上限
     */
    private Integer outMax;
    /**
     * 出库下限
     */
    private Integer outMin;
    /**
     * 客户订单号
     */
    private String orderNumber;
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
