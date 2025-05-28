package io.metaxk.module.mes.dal.dataobject.issue;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.framework.mybatis.core.dataobject.BaseDO;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * io.metaxk.module.mes.dal.dataobject.issue
 *
 * @author xx
 * @time 2023/8/23 15:15
 */

@Data
@TableName("issue_problem_sub")
public class IssueProblemSubmit extends BaseDO {
    /**
     * id
     */
    private long id;
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;
    /**
     * 所属车间
     */
    @NotBlank(message = "所属车间不能为空")
    private String workshopAffiliation;
    /**
     * 来源
     */
    @NotBlank(message = "来源不能为空")
    private String source;
    /**
     * 零部件
     */
    @NotBlank(message = "零部件不能为空")
    private String component;
    /**
     * 类型
     */
    @NotBlank(message = "类型不能为空")
    private String type;
    /**
     * 模式
     */
    @NotBlank(message = "模式不能为空")
    private String mode;
    /**
     * 描述
     */
    private String description;
    /**
     * 状态
     */
    private String status;
}
