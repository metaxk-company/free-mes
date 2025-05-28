package io.metaxk.module.mes.enums.comment;

import io.metaxk.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 商品评论的审批状态枚举
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Getter
@AllArgsConstructor
public enum ProductCommentAuditStatusEnum implements IntArrayValuable {

    NONE(1, "待审核"),
    APPROVE(2, "审批通过"),
    REJECT(2, "审批不通过"),;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(ProductCommentAuditStatusEnum::getStatus).toArray();

    /**
     * 审批状态
     */
    private final Integer status;
    /**
     * 状态名
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
