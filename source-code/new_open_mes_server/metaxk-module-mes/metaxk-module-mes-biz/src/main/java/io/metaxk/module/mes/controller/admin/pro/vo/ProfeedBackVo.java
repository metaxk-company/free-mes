package io.metaxk.module.mes.controller.admin.pro.vo;


import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class ProfeedBackVo {

    private String taskCode;

    private BigDecimal quantity;

    private List<Map<String, String>> equipmentCode;

    private String workHour;

    private String ipAddress;

    private String userName;

    private String status;


    private String pauseReason;
}
