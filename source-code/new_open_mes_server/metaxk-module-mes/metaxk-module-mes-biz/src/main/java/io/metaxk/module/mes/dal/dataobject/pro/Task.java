package io.metaxk.module.mes.dal.dataobject.pro;


import io.metaxk.module.mes.common.EntityCommon;
import lombok.*;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;

/**
 * 生产任务
 * @author 万界星空
 */
@TableName("pro_task")
@Data
public class Task extends EntityCommon {

    /**
     * 任务ID
     */
    private Long id;
    /**
     * 任务编号
     */
    private String taskCode;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 生产工单ID
     */
    private Long workorderId;
    /**
     * 生产工单编号
     */
    private String workorderCode;
    /**
     * 工单名称
     */
    private String workorderName;
    /**
     * 工作站ID
     */
    private Long workstationId;
    /**
     * 工作站编号
     */
    private String workstationCode;
    /**
     * 工作站名称
     */
    private String workstationName;
    /**
     * 工序ID
     */
    private Long processId;
    /**
     * 工序编码
     */
    private String processCode;
    /**
     * 工序名称
     */
    private String processName;
    /**
     * 产品物料ID
     */
    private Long itemId;
    /**
     * 产品物料编码
     */
    private String itemCode;
    /**
     * 产品物料名称
     */
    private String itemName;
    /**
     * 规格型号
     */
    private String specification;
    /**
     * 单位
     */
    private String unitOfMeasure;
    /**
     * 排产数量
     */
    private BigDecimal quantity;
    /**
     * 已报工数量
     */
    private BigDecimal quantityFeedback;

    /**
     * 客户编码
     */
    private String clientCode;
    /**
     * 客户名称
     */
    private String clientName;

    /**
     * 开始生产时间
     */
    private String startTime;

    /**
     * 生产时长
     */
    private Long duration;
    /**
     * 完成生产时间
     */
    private String endTime;

    /**
     * 需求日期
     */
    private String requestDate;
    /**
     * 生产状态
     */
    private String status;

    /**
     * 任务二维码
     */
    private String taskUrl;
    /**
     * 二维码格式
     */
    private String barcodeFormat;
    /**
     * 工序二维码
     */
    private String processUrl;

    private String workstationType;

    private String machineryCode;

    private String machineryName;


    private BigDecimal schedule;


    /**
     * 排产任务类型（工序排产，工单排产）
     */
    private String  scheduleType;


    /**
     * 执行班组编号
     */
    private String teamCode;

    /**
     * 执行班组名称
     */
    private String  teamName;

    /**
     * 执行班组类型
     */
    private String  teamType;



    @TableField(exist = false)
    private Integer  countQuantity;


    @TableField(exist = false)
    private BigDecimal remainQuantity;


    /**
     * 剩余未排产订单数
     */
    private BigDecimal residueQuantity;

}
