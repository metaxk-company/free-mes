package io.metaxk.module.mes.controller.admin.pro.vo;


import io.metaxk.module.mes.dal.dataobject.pro.TemporaryWorkHours;
import lombok.Data;
import java.util.List;



@Data
public class TemporaryWorkHoursListResult {
    private Double totalWorkhours;
    private List<TemporaryWorkHours> list;
}
