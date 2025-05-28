package io.metaxk.module.mes.controller.admin.pro.vo;

import lombok.Data;

@Data
public class ProTaskQueryVo {


    private Long id;


    private String  workstationCode;

    private String  workstationName;

    private String  taskCode;

    private String  taskName;

    private String  machineryCode;

    private String  machineryName;

    private String  workorderCode;

    private String  processCode;

    private String  processName;

    private String  startTime;

    private String  endTime;


}
