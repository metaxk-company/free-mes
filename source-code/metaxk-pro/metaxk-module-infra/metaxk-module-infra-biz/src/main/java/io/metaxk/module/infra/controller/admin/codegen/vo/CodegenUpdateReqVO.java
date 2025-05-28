package io.metaxk.module.infra.controller.admin.codegen.vo;

import cn.hutool.core.util.ObjectUtil;
import io.metaxk.module.infra.controller.admin.codegen.vo.column.CodegenColumnBaseVO;
import io.metaxk.module.infra.controller.admin.codegen.vo.table.CodegenTableBaseVO;
import io.metaxk.module.infra.enums.codegen.CodegenSceneEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "管理后台 - 代码生成表和字段的修改 Request VO")
@Data
public class CodegenUpdateReqVO {

    @Valid // 校验内嵌的字段
    @NotNull(message = "表定义不能为空")
    private Table table;

    @Valid // 校验内嵌的字段
    @NotNull(message = "字段定义不能为空")
    private List<Column> columns;

    @Schema(description = "更新表定义")
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    @Valid
    public static class Table extends CodegenTableBaseVO {

        @Schema(description = "编号", required = true, example = "1")
        private Long id;

        @AssertTrue(message = "上级菜单不能为空")
        public boolean isParentMenuIdValid() {
            // 生成场景为管理后台时，必须设置上级菜单，不然生成的菜单 SQL 是无父级菜单的
            return ObjectUtil.notEqual(getScene(), CodegenSceneEnum.ADMIN.getScene())
                    || getParentMenuId() != null;
        }

    }

    @Schema(description = "更新表定义")
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class Column extends CodegenColumnBaseVO {

        @Schema(description = "编号", required = true, example = "1")
        private Long id;

    }

}
