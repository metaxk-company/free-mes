package io.metaxk.module.mes.controller.admin.cla.vo;

import lombok.Data;
import java.math.BigDecimal;

/**
 * @author 万界星空
 * @time 2023/7/4 9:25
 */
@Data
public class ClaPlanMemberUpdateVo {

    private Long id;

    private String planPeopleName;

    private BigDecimal peopleQuantity;


}
