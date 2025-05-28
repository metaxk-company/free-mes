package io.metaxk.module.mes.controller.admin.pro.vo;

import lombok.Data;

@Data
public class ProWorkorderVo  {

    /*
    * 工单编号
    * */
    private  String workorderCode;


    private  String workorderName;
    /*
     * 产品编号
     * */
    private  String productCode;
    /*
     * 产品名称
     * */
    private  String productName;
    /*
     * 开始时间
     * */
    private String startTime;
    /*
     * 需求时间
     * */
    private  String requestDate;


}
