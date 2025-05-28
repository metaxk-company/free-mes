package io.metaxk.module.mes.controller.admin.pro.vo;


import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;

@Data
public class FeedbackHoursVo extends PageParam {

    private  String  workstationCode;

    private  String  workshopCode;

    private  String  processCode;

    private  String  userName;

    private  String  machineryCode;

    private  String  startTime;

    private  String  endTime;

    private String feedbackTime;

    private String  finishedTime;
}
