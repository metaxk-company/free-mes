package io.metaxk.module.mes.dal.dataobject.qc;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

/**
 * 检验项目分类实体类
 * @author 万界星空
 * @time 2023/7/6 11:37
 */
@Data
@TableName("qc_project_classify")
public class ProjectClassify extends EntityCommon {

    private Long  id;

    private String  projectCode;

    private String  projectName;

    private String  classify;

    private Long  parentClassify;



}
