package io.metaxk.module.mes.enums.spu;

import io.metaxk.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 商品 SPU 规格类型
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Getter
@AllArgsConstructor
public enum ProductSpuSpecTypeEnum implements IntArrayValuable {

    RECYCLE(1, "统一规格"),
    DISABLE(2, "多规格");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(ProductSpuSpecTypeEnum::getType).toArray();

    /**
     * 规格类型
     */
    private final Integer type;
    /**
     * 规格名称
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
