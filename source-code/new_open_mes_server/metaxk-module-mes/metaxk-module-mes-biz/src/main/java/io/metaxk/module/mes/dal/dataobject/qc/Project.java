package io.metaxk.module.mes.dal.dataobject.qc;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import io.metaxk.module.mes.controller.admin.qc.vo.InspectDeviceVo;
import lombok.Data;
import java.util.List;

/**
 * 检验项目实体类
 * @author 万界星空
 * @time 2023/7/6 13:19
 */
@Data
@TableName("qc_project")
public class Project extends EntityCommon {

    private Long id;

    private String projectCode;

    private String projectName;

    private String classify;

    private String standValue;

    private String  standUnit;

    private String   remark;

    @TableField(exist = false)
    private String inspectDevice;

    @TableField(exist = false)
    private List<InspectDeviceVo>  inspectDeviceList;

}
