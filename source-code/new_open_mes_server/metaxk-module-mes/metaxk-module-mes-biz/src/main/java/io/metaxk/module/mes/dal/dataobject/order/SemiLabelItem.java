package io.metaxk.module.mes.dal.dataobject.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;
import java.util.Date;



/**
 * 半成品入库子表实体类
 * @author 万界星空
 * @time 2023/8/9 17:06
 */
@Data
@TableName("wh_semi_label_item")
public class SemiLabelItem extends EntityCommon {
    /**
     * id
     */
    private Long  id;


    /**
     * 半成品入库编号
     */
    private String  semiNumber;



    /**
     * 型号
     */
    private String  model;

    /**
     * 轴数
     */
    private String  axlesNum;

    /**
     * 规格
     */
    private String  spec;

    /**
     * 批号
     */
    private String  batchNumber;

    /**
     * 箱号
     */
    private String  boxNumber;

    /**
     * 1轴净重
     */
    private String  oneAxleHeight;

    /**
     * 2轴净重
     */
    private String  twoAxleHeight;

    /**
     * 3轴净重
     */
    private String  threeAxleHeight;

    /**
     * 4轴净重
     */
    private String  fourAxleHeight;

    /**
     * 5轴净重
     */
    private String  fiveAxleHeight;

    /**
     * 6轴净重
     */
    private String  sixAxleHeight;

    /**
     * 7轴净重
     */
    private String  sevenAxleHeight;

    /**
     * 8轴净重
     */
    private String  eightAxleHeight;

    /**
     * 总重量
     */
    private String  totalHeight;

    /**
     * 标准
     */
    private String  stand;

    /**
     * 客户代码
     */
    private String  clientCode;

    /**
     * 线别
     */
    private String  lineType;

    /**
     * 盘号
     */
    private String  reelNumber;

    /**
     * 颜色
     */
    private String  color;

    /**
     * 是否重包
     */
    private String  hePackage;

    /**
     * 公司名称
     */
    private String  corporateName;

    /**
     * 条码
     */
    private String  barCode;

    /**
     * 设备编码
     */
    private String  equNumber;

    /**
     * 托盘编号
     */
    private String  palletNumber;

    /**
     * 托盘数量
     */
    private String  palletQuantity;

    /**
     * 产品编号
     */
    private String  itemCode;

    /**
     * 产品类型
     */
    private String  itemType;

    /**
     * 产品名称
     */
    private String  itemName;

    /**
     * 单位
     */
    private String  unitOfMeasure;

    /**
     * 是否退货
     */
    private String  returnGood;

    /**
     * 数量
     */
    private String quantity;

    /**
     * 退货日期
     */
    private String  returnDate;

    /**
     * 日期
     */
    private Date date;

    /**
     * 皮重
     */
    private String tare;

    /**
     * 总皮重
     */
    private String totalTare;

    /**
     * 备注
     */
    private String remark;


    @TableField(exist = false)
    private String  itemTypeName;
}
