package io.metaxk.module.mes.dal.dataobject.qc;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 其他检验单实体类
 * @author 万界星空
 * @time 2023/8/19 14:28
 */
@Data
@TableName("qc_other_record")
public class OtherRecord extends EntityCommon {

    private Long id;

    private String number;

    /**
     * 生产订单编号
     */
    private String workOrderCode;

    private String itemCode;

    private String itemName;
    /**
     * 客户编号
     */
    private String clientCode;
    /**
     * 客户名称
     */
    private String clientName;

    /**
     * 检验标准编号
     */
    private String otherStandardNumber;

    private String model;

    private String spec;

    private String lineType;

    private String reelNumber;

    private String color;

    private String kind;

    private String unitOfMeasure;

    private BigDecimal purchasePrice;

    private BigDecimal quantity;

    private BigDecimal includTax;

    private BigDecimal noIncludTax;

    private String vendor;

    private String checkNumber;

    private String inspectWay;

    private String version;

    private String status;//状态，0：不合格 1：合格

    private String inspectFlag;//是否有检验项目，0：否 1：是

    /**
     *质检组
     */
    private String inspectGroup;

    /**
     *质检员
     */
    private String inspectUser;

    /**
     *检验开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date inspectStartTime;

    /**
     *检验结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date inspectEndTime;

    @TableField(exist = false)
    List<OtherRecordItem> otherRecordItemList;
}
