package io.metaxk.module.mes.controller.admin.pro.vo;

import io.metaxk.module.mes.dal.dataobject.pro.FeedbackHours;
import lombok.Data;
import java.util.List;



@Data
public class FeedbackHoursListResult {
    private String totalWorkhours;
    private String equipmentWorkhours;
    private List<FeedbackHours> list;
}
