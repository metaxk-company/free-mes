package io.metaxk.module.mes.dal.dataobject.wm;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.*;



/**
 * 仓库实体类
 * @author 万界星空MES
 */
@TableName("wm_warehouse")
@Data
public class WareHouse extends EntityCommon {

    /**
     * 仓库ID
     */
    private Long id;

    /**
     * 外部系统id
     */
    private String externalId;
    /**
     * 仓库编码
     */
    private String warehouseNumber;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     *  位置
     */
    private String location;
    /**
     * 面积
     */
    private String area;
    /**
     * 负责人
     */
    private String charge;
    /**
     * 备注
     */
    private String remark;




}
