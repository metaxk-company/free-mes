package io.metaxk.module.mes.controller.admin.cla.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.metaxk.module.mes.dal.dataobject.cla.PeopleInfo;
import lombok.Data;
import java.util.Date;
import java.util.List;


/**
 * @author 万界星空
 * @time 2023/6/28 16:43
 */
@Data
public class ClaPlanSaveVo {

    /**
     * id
     */
    private  Long id;

    /**
     * 排班编号
     */
    private String planCode;

    /**
     * 排班名称
     */
    private String planName;

    /**
     * 排班类型
     */
    private String teamType;

    /**
     * 班组编号
     */
    private String teamCode;


    /**
     * 班组名称
     */
    private String teamName;



    private List<PeopleInfo> selectedPersonnel;


    /**
     * 轮班时长
     */
    private String shiftDuration;

    /**
     * 轮班方式
     */
    private String shiftWay;

    /**
     * 倒班方式
     */
    private String changeShiftWay;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startDate;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endDate;



}
