package io.metaxk.module.mes.dal.dataobject.cla;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.metaxk.module.mes.common.EntityCommon;
import io.metaxk.module.mes.controller.admin.cla.vo.ClaPlanMemberVo;
import lombok.Data;
import java.util.Date;
import java.util.List;


/**
 * @author 万界星空
 * @time 2023/6/26 13:23
 */
@Data
@TableName("cla_plan_team")
public class ClassPlan extends EntityCommon {

    /**
     * id
     */
    private  Long id;

    /**
     * 排班计划编号
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




    @TableField(exist = false)
    private List<ClaPlanMemberVo>   selectedPersonnel;

    @TableField(exist = false)
    private List<ClaPlanMemberVo>   personsNotSelected;
}
