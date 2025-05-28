package io.metaxk.module.infra.dal.dataobject.codegen;

import com.baomidou.mybatisplus.annotation.TableId;
import io.metaxk.framework.mybatis.core.dataobject.BaseDO;
import io.metaxk.module.infra.dal.dataobject.db.DataSourceConfigDO;
import io.metaxk.module.infra.enums.codegen.CodegenSceneEnum;
import io.metaxk.module.infra.enums.codegen.CodegenTemplateTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 代码生成 table 表定义
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@TableName(value = "infra_codegen_table", autoResultMap = true)
@KeySequence("infra_codegen_table_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CodegenTableDO extends BaseDO {

    /**
     * ID 编号
     */
    @TableId
    private Long id;

    /**
     * 数据源编号
     *
     * 关联 {@link DataSourceConfigDO#getId()}
     */
    private Long dataSourceConfigId;
    /**
     * 生成场景
     *
     * 枚举 {@link CodegenSceneEnum}
     */
    private Integer scene;

    // ========== 表相关字段 ==========

    /**
     * 表名称
     *
     * 关联 {@link TableInfo#getName()}
     */
    private String tableName;
    /**
     * 表描述
     *
     * 关联 {@link TableInfo#getComment()}
     */
    private String tableComment;
    /**
     * 备注
     */
    private String remark;

    // ========== 类相关字段 ==========

    /**
     * 模块名，即一级目录
     *
     * 例如说，system、infra、tool 等等
     */
    private String moduleName;
    /**
     * 业务名，即二级目录
     *
     * 例如说，user、permission、dict 等等
     */
    private String businessName;
    /**
     * 类名称（首字母大写）
     *
     * 例如说，SysUser、SysMenu、SysDictData 等等
     */
    private String className;
    /**
     * 类描述
     */
    private String classComment;
    /**
     * 作者
     */
    private String author;

    // ========== 生成相关字段 ==========

    /**
     * 模板类型
     *
     * 枚举 {@link CodegenTemplateTypeEnum}
     */
    private Integer templateType;

    // ========== 菜单相关字段 ==========

    /**
     * 父菜单编号
     *
     * 关联 MenuDO 的 id 属性
     */
    private Long parentMenuId;

}
