package io.metaxk.module.mes.controller.admin.qc.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/11 18:11
 */
@Data
public class StandardSaveVo {

    private Long id;

    private String  inspectCode;

    private String  inspectName;

    private String  inspectMethod;

    private String  itemType;

    private String  routeStand;

    private String  processCode;

    private String  processName;

    private String  condition;

    private String  inspectScenario;

    private String  version;

    private String  enableFlag;

    @TableField(exist = false)
    List<StandardVo> inspectDeviceList;


    @TableField(exist = false)
    private List<String>  itemTypeList;


    @TableField(exist = false)
    private List<String>  processCodeList;
}
