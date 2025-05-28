package io.metaxk.module.mes.dal.dataobject.issue;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.framework.mybatis.core.dataobject.BaseDO;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * io.metaxk.module.mes.dal.dataobject.issue
 *
 * @author 万界星空
 * @time 2023/7/26 15:07
 */

@Data
@TableName("issue_method")
public class IssueMethod extends BaseDO {

    private Long id;

    /**
     * 方法名称
     */
    @NotBlank(message = "方法名称不能为空")
    private String name;

    /**
     * 描述
     */
    private String description;

}
