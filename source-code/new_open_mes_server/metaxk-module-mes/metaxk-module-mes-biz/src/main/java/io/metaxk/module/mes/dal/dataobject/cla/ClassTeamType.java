package io.metaxk.module.mes.dal.dataobject.cla;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;


/**
 * 班组类型实体
 * @author 万界星空
 * @time 2023/6/25 9:41
 */
@Data
@TableName("cla_team_type")
public class ClassTeamType {

    private Long id;

    private String typeCode;

    private String typeName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


    private String creator;

    private String remark;



}
