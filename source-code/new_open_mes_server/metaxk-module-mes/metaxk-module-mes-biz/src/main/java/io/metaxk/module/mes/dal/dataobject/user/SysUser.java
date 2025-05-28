package io.metaxk.module.mes.dal.dataobject.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

/**
 * @author 万界星空
 * @time 2023/6/27 17:51
 */
@Data
@TableName("system_users")
public class SysUser extends EntityCommon {


    private Long id;

    @TableField("username")
    private String userName;

    private String joinTeam;

    private int deleted;




}
