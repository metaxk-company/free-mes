package io.metaxk.module.mes.controller.admin.qc.vo;

import lombok.Data;

/**
 * @author 万界星空
 * @time 2023/7/26 11:50
 */
@Data
public class ReceiveStandardVo {

    //检测名称
    private String  itemName;

    //检测标准
    private String  itemStandard;

    //检测器具
    private String  itemDevice;

    //描述
    private String  remark;
}
