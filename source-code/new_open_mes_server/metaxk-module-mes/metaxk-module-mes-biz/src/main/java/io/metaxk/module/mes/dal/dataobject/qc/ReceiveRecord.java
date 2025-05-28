package io.metaxk.module.mes.dal.dataobject.qc;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 来料检验单实体类
 * @author 万界星空
 * @time 2023/7/26 14:28
 */
@Data
@TableName("qc_receive_record")
public class ReceiveRecord extends EntityCommon {

    private Long id;

    private String recordCode;

    private String recNumber;

    private String itemCode;

    //private String itemName;

    //private String model;

    //private String spec;

    //private String kind;

    //private String unitOfMeasure;

    //private BigDecimal purchasePrice;

    private BigDecimal quantity;

    //private BigDecimal includTax;

    //private BigDecimal noIncludTax;

    //private String vendor;

    private BigDecimal checkNumber;

    private String inspectWay;

    private String version;

    private String status;//状态，0：未质检 1：已质检

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

}
