package io.metaxk.module.mes.dal.dataobject.dept;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

/**
 * @author 万界星空
 * @time 2023/7/7 14:58
 */
@Data
@TableName("system_dept")
public class SysDept extends EntityCommon {

    private Long id;

    private String name;

    private Long parentId;

    private int sort;

    private String leaderUserId;

    private String phone;

    private String email;

    private String status;


}
