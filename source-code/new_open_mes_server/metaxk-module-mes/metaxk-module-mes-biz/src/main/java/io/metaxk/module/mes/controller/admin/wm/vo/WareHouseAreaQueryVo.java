package io.metaxk.module.mes.controller.admin.wm.vo;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;


@Data
public class WareHouseAreaQueryVo extends PageParam {

    /**
     * 库区编码
     */
    private String areaNumber;
    /**
     * 库区名称
     */
    private String areaName;

}
