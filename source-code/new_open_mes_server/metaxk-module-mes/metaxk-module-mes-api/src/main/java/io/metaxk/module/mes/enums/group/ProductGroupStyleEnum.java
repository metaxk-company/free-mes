package io.metaxk.module.mes.enums.group;

import io.metaxk.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 商品分组的样式枚举
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Getter
@AllArgsConstructor
public enum ProductGroupStyleEnum implements IntArrayValuable {

    ONE(1, "每列一个"),
    TWO(2, "每列两个"),
    THREE(2, "每列三个"),;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(ProductGroupStyleEnum::getStyle).toArray();

    /**
     * 列表样式
     */
    private final Integer style;
    /**
     * 状态名
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
