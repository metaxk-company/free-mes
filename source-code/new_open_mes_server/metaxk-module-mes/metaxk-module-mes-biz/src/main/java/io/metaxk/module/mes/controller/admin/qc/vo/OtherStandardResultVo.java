package io.metaxk.module.mes.controller.admin.qc.vo;

import lombok.Data;

@Data
public class OtherStandardResultVo {

    private String model;

    private String spec;

    private String lineType;

    private String status;

    private String num;

    private String qualifiedNum;

    private String unqualifiedNum;
}
