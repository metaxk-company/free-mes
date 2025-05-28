package io.metaxk.module.mes.controller.admin.md.vo;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;

/**
 * 型号查询模型
 *
 * @author 万界星空MES
 */
@Data
public class ModelQueryVo extends PageParam {
    /**
     * 型号编号
     */
    private String number;
    /**
     * 型号名称
     */
    private String name;
    /**
     * 是否采购物料
     */
    private String isProcure;
}
