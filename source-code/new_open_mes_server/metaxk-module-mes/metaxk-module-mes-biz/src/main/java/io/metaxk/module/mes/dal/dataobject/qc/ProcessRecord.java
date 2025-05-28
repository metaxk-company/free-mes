package io.metaxk.module.mes.dal.dataobject.qc;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;
import java.util.Date;



/**
 * 工序检验单实体类
 * @author 万界星空
 * @time 2023/7/10 13:28
 */
@Data
@TableName("qc_process_record")
public class ProcessRecord extends EntityCommon {

    private Long id;

    private String  recordCode;

    private String  taskCode;

    private String  orderCode;

    private String  processCode;

    private String  processName;

    /**
     *物料编码
     */
    private String  productCode;

    /**
     *物料名称
     */
    private String  productName;


    private String quantity;

    private String  reportUser;

    private String  inspectWay;


    private String  checkNumber;

    private String version;

    /**
     *收订单日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderDate;

    /**
     * 质检时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date inspectTime;

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

    private String  status;//状态，0：未质检 1：已质检

    private String inspectFlag;//是否有检验项目，0：否 1：是

    /**
     *质检组
     */
    private String  inspectGroup;

    /**
     *质检员
     */
    private String  inspectUser;






}
