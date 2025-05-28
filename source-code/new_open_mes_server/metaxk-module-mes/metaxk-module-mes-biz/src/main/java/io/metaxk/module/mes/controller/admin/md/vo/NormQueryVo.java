package io.metaxk.module.mes.controller.admin.md.vo;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;

/**
 * 规格查询模型
 *
 * @author 万界星空MES
 */
@Data
public class NormQueryVo extends PageParam {
    /**
     * 型号
     */
    private String model;
    /**
     * 模糊型号
     */
    private String fuzzyModel;
    /**
     * 编号
     */
    private String serial;
    /**
     * 名称
     */
    private String name;
    /**
     * 是否采购物料
     */
    private String isProcure;
}
