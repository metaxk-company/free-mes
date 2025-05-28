package io.metaxk.module.mes.controller.admin.order.vo;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/27 16:33
 */
@Data
public class LabelQueryVo extends PageParam {

    /**
     * 型号
     */
    private String  model;


    /**
     * 规格
     */
    private String  spec;


    /**
     * 条码
     */
    private String  barCode;


    /**
     * 状态
     */
    private String  status;

    /**
     * 客户代码
     */
    private String  clientCode;

    /**
     * 数组id
     */
    private List<String> ids;


    /**
     * 线别
     */
    private String  lineType;


    /**
     * 盘号
     */
    private String  reelNumber;

    /**
     * 颜色
     */
    private String  color;

    /**
     * 托盘编号
     */
    private String  palletNumber;
    /**
     * 箱号
     */
    private String  boxNumber;
    private String  startDate;
    private String  endDate;

}
