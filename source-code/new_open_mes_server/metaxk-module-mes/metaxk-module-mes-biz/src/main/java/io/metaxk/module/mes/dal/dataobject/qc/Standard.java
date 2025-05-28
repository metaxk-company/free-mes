package io.metaxk.module.mes.dal.dataobject.qc;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import io.metaxk.module.mes.controller.admin.qc.vo.StandardVo;
import lombok.Data;
import java.util.List;


/**
 * 工序检验标准实体类
 * @author 万界星空
 * @time 2023/7/6 13:40
 */
@Data
@TableName("qc_process_standard")
public class Standard extends EntityCommon {

    private Long id;

    @TableField("number")
    private String  inspectCode;

    @TableField("name")
    private String  inspectName;

    @TableField("method")
    private String  inspectMethod;

    @TableField("product_type")
    private String  itemType;

    private String  processCode;

    private String  processName;

    private String  version;

    private String  enableFlag;

    private String  quantity;

    private String  inspectCondition;

    private String  inspectScenario;

    private String  routeStandard;

    @TableField(exist = false)
    List<StandardVo>  inspectDeviceList;


    @TableField(exist = false)
    private List<String>  itemTypeList;


    @TableField(exist = false)
    private List<String>  processCodeList;
}
