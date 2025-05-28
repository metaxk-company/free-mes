package io.metaxk.module.mes.dal.dataobject.qc;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

/**
 * 检测项目器具表
 * @author 万界星空
 * @time 2023/7/11 21:23
 */
@Data
@TableName("qc_project_device")
public class ProjectDevice extends EntityCommon {

    private Long id;

    private String projectCode;

    private String deviceCode;

    private String deviceName;

    private String agreement;
}
