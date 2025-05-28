package io.metaxk.module.mes.dal.dataobject.qc;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import io.metaxk.module.mes.controller.admin.qc.vo.OtherStandardVo;
import io.metaxk.module.mes.controller.admin.qc.vo.ReceiveStandardVo;
import lombok.Data;

import java.util.List;

/**
 * 其他检验标准实体类
 * @author 万界星空
 * @time 2023/8/19 10:28
 */
@Data
@TableName("qc_other_standard")
public class OtherStandard extends EntityCommon {

    private Long id;

    /**
     * 检验编号
     */
    private String number;

    /**
     * 检验名称
     */
    private String name;

    /**
     * 检验方式
     */
    private String method;

    /**
     * 产品分类
     */
    private String productType;

    /**
     * 型号
     */
    private String model;
    /**
     * 规格
     */
    private String spec;
    /**
     * 线别
     */
    private String lineType;
    /**
     * 盘号
     */
    private String reelNumber;
    /**
     * 颜色
     */
    private String color;

    private String version;

    private String enableFlag;

    /**
     * 检验数量
     */
    private String quantity;

    private String inspectCondition;

    private String inspectScenario;

    private String routeStandard;

    @TableField(exist = false)
    private List<String> modelList;

    @TableField(exist = false)
    private List<String> specList;

    @TableField(exist = false)
    private List<String> lineTypeList;

    @TableField(exist = false)
    private List<String> reelNumberList;

    @TableField(exist = false)
    private List<String> colorList;

    @TableField(exist = false)
    private List<String>  productTypeList;

    @TableField(exist = false)
    List<OtherStandardVo>  otherStandardVoList;
}
