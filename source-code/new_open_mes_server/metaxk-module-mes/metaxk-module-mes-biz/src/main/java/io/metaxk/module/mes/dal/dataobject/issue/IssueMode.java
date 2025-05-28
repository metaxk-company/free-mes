package io.metaxk.module.mes.dal.dataobject.issue;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.framework.mybatis.core.dataobject.BaseDO;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * io.metaxk.module.mes.dal.dataobject.issue
 *
 * @author 万界星空
 * @time 2023/7/26 15:08
 */

@Data
@TableName("issue_mode")
public class IssueMode extends BaseDO {

    private Long id;

    /**
     * 模式名称
     */
    @NotBlank(message = "模式名称不能为空")
    private String name;

    /**
     * 类型
     */
    @NotBlank(message = "类型名称不能为空")
    private String type;

    /**
     * 描述
     */
    private String description;

}
