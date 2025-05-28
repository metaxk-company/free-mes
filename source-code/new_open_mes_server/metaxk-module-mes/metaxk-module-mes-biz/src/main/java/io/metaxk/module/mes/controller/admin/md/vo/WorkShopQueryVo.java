package io.metaxk.module.mes.controller.admin.md.vo;

import lombok.*;
import io.metaxk.framework.common.pojo.PageParam;


@Data
public class WorkShopQueryVo extends PageParam {

    private String workshopCode;

    private String workshopName;

    private String charge;



}
