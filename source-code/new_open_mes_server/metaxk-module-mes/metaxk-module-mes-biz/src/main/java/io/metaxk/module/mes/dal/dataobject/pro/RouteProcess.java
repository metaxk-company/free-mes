package io.metaxk.module.mes.dal.dataobject.pro;


import io.metaxk.module.mes.common.EntityCommon;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
/**
 * 工艺组成 DO
 * @author 万界星空
 */
@TableName("pro_route_process")
@Data
public class RouteProcess extends EntityCommon {

    /**
     * 记录ID
     */

    private Long id;
    /**
     * 工艺路线ID
     */
    private Long routeId;
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
     * 序号
     */
    private Integer orderNum;
    /**
     * 工序ID
     */
    private Long nextProcessId;
    /**
     * 工序编码
     */
    private String nextProcessCode;
    /**
     * 工序名称
     */
    private String nextProcessName;
    /**
     * 与下一道工序关系
     */
    private String linkType;
    /**
     * 准备时间
     */
    private Integer defaultPreTime;
    /**
     * 等待时间
     */
    private Integer defaultSufTime;
    /**
     * 甘特图显示颜色
     */
    private String colorCode;
    /**
     * 关键工序
     */
    private String keyFlag;
    /**
     * 备注
     */
    private String remark;

    private String attr1;

    private String attr2;

    private String attr3;

    private String attr4;


}
