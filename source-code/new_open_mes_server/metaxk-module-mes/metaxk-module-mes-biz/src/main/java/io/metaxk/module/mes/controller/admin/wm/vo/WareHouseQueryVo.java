package io.metaxk.module.mes.controller.admin.wm.vo;

import io.metaxk.framework.common.pojo.PageParam;
import lombok.Data;

@Data
public class WareHouseQueryVo extends PageParam {


    /**
     * 仓库编码
     */
    private String warehouseNumber;
    /**
     * 仓库名称
     */
    private String wareHouseName;
}
