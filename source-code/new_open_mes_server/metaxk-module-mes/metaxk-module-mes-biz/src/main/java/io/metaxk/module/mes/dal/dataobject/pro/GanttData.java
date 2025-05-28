package io.metaxk.module.mes.dal.dataobject.pro;


import lombok.Data;
import java.math.BigDecimal;


/**
 * 甘特图实体
 * @author 万界星空
 */
@Data
public class GanttData {


    /**
     * id
     */
    private String id;

    /**
     * 编号
     */
    private String code;

    /**
     * TASK 类型：project；task
     */
    private String type;

    /**
     * 产品编号
     */
    private String itemCode;

    /**
     * 产品名称
     */
    private String itemName;

    /**
     * 工作站名称
     */
    private String workstation;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 工序
     */
    private String process;


    /**
     * 父TASK ID
     */
    private String parent;


    /**
     * 开始生产时间
     */
    private String start_date;


    /**
     * 完成生产时间
     */
    private String end_date;


    /**
     * 进度百分比
     */
    private BigDecimal  progress;

    /**
     * 接收订单日期
     */
    private String orderDate;

    /**
     * 已排数量
     */
    private BigDecimal arrangedQuantity;


    /**
     * 未排数量
     */
    private BigDecimal unArrangedQuantity;



}
