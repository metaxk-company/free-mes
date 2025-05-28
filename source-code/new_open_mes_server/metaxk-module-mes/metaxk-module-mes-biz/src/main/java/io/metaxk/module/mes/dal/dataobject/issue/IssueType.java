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
@TableName("issue_type")
public class IssueType extends BaseDO {

    private Long id;

    /**
     * 类型名称
     */
    @NotBlank(message = "类型名称不能为空")
    private String name;

    /**
     * 子标题
     */
    private String subTopic;

}
