package io.metaxk.module.mes.controller.admin.qc.vo;

import lombok.Data;

/**
 * @author 万界星空
 * @time 2023/8/19 10:28
 */
@Data
public class OtherStandardVo {

    //检测名称
    private String  itemName;

    //检测标准
    private String  itemStandard;

    //检测器具
    private String  itemDevice;

    //描述
    private String  remark;
}
