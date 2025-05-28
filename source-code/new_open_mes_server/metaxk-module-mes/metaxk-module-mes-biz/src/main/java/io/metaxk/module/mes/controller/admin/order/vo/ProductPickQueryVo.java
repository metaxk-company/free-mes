package io.metaxk.module.mes.controller.admin.order.vo;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;

import java.util.List;

@Data
public class ProductPickQueryVo extends PageParam {

    private String number;

    private String startTime;

    private String endTime;

    private List pickDate;

}
