package io.metaxk.module.mes.dal.dataobject.md;

import io.metaxk.module.mes.common.EntityCommon;
import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;


/**
 * 物料产品分类
 * @author 万界星空
 */
@TableName("md_item_type")
@Data
public class ItemType extends EntityCommon {

    /**
     * 产品物料类型ID
     */
    private Long id;
    /**
     * 产品物料类型编码
     */
    private String itemTypeCode;
    /**
     * 产品物料类型名称
     */
    private String itemTypeName;
    /**
     * 父类型ID
     */
    private Long parentTypeId;
    /**
     * 所有层级父节点
     */
    private String ancestors;
    /**
     * 产品物料标识
     */
    private String itemOrProduct;
    /**
     * 排列顺序
     */
    private Integer orderNum;
    /**
     * 是否启用
     */
    private String enableFlag;


    /**
     * 子数据
     */
    @TableField(exist = false)
    private List<ItemType> children = new ArrayList<>();

}
