package io.metaxk.module.mes.dal.dataobject.wm;


import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.*;


/**
 * 库区实体类
 * @author 万界星空MES
 */
@TableName("wm_warehouse_area")
@Data
public class WareHouseArea extends EntityCommon {

    /**
     * 库区ID
     */
    private Long id;
    /**
     * 库区编码
     */
    private String areaNumber;
    /**
     * 库区名称
     */
    private String areaName;
    /**
     * 所属仓库
     */
    private String warehouseName;
    /**
     * 所属库区
     */
    private String warehouseLocation;

    /**
     * 位置
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
