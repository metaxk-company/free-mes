package io.metaxk.module.mes.dal.dataobject.qc;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import io.metaxk.module.mes.controller.admin.qc.vo.ReceiveStandardVo;
import lombok.Data;

import java.util.List;

/**
 * 来料检验标准实体类
 * @author 万界星空
 * @time 2023/7/26 10:28
 */
@Data
@TableName("qc_receive_standard")
public class ReceiveStandard extends EntityCommon {

    private Long id;

    private String number;

    private String name;

    private String method;

    /**
     * 产品分类
     */
    private String productType;

    /**
     * 到货通知单编号
     */
    //private String recNumber;

    /**
     * 产品编号
     */
    private String itemCode;

    private String version;

    private String enableFlag;

    private String quantity;

    private String inspectCondition;

    private String inspectScenario;

    private String routeStandard;

    @TableField(exist = false)
    //private List<String> recNumberList;
    private List<String> itemCodeList;

    @TableField(exist = false)
    private List<String>  productTypeList;

    @TableField(exist = false)
    List<ReceiveStandardVo>  inspectDeviceList;

}
