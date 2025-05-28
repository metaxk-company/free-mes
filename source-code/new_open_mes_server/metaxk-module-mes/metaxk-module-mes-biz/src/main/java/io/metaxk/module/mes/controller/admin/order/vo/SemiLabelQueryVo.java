package io.metaxk.module.mes.controller.admin.order.vo;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/8/9 17:06
 */
@Data
public class SemiLabelQueryVo extends PageParam {

    /**
     * 编号
     */
    private String number;


    /**
     * 状态
     */
    private String status;

    /**
     * 数组id
     */
    private List<String> ids;
}
