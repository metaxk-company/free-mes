package io.metaxk.module.mes.controller.admin.qc.vo;

import lombok.Data;

@Data
public class ReceiveRecordItemsVo {

    private Long standardItemId;//检验项目id

    private String itemName;//检测名称

    private String itemStandard;//检测标准

    private String itemDevice;//检测器具

    private String itemValue;//检测实际信息
}
