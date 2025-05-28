package io.metaxk.module.mes.dal.dataobject.issue;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.framework.mybatis.core.dataobject.BaseDO;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * io.metaxk.module.mes.dal.dataobject.issue
 *
 * @author 万界星空
 * @time 2023/7/26 16:16
 */

@Data
@TableName("issue_component")
public class IssueComponent extends BaseDO {

    private Long id;

    /**
     * 零部件名称
     */
    @NotBlank(message = "零部件名称不能为空")
    private String name;

    /**
     * 描述
     */
    private String description;

}
