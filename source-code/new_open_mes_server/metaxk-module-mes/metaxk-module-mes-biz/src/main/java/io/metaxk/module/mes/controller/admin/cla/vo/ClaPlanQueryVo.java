package io.metaxk.module.mes.controller.admin.cla.vo;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;


/**
 * 排班计划搜索类
 * @author 万界星空
 * @time 2023/6/26 13:42
 */
@Data
public class ClaPlanQueryVo extends PageParam {

    private  String teamType;

    private  String planCode;

    private  String planName;

    private  String startDate;

    private  String endDate;


}
