package io.metaxk.module.mes.dal.dataobject.issue;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * io.metaxk.module.mes.dal.dataobject.issue
 *
 * @author 万界星空
 * @time 2023/7/26 15:02
 */

@Data
@TableName("issue_source")
public class IssueSource extends EntityCommon {

    private Long id;

    /**
     * 来源名称
     */
    @NotBlank(message = "来源名称不能为空")
    private String name;

    /**
     * 描述
     */
    private String description;

}
