package io.metaxk.module.mes.enums.delivery;

import io.metaxk.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 配送方式枚举
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Getter
@AllArgsConstructor
public enum DeliveryTypeEnum implements IntArrayValuable {

    // TODO 星空斗士：英文单词，需要再想下；
    EXPRESS(1, "快递发货"),
    USER(2, "用户自提"),;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(DeliveryTypeEnum::getMode).toArray();

    /**
     * 配送方式
     */
    private final Integer mode;
    /**
     * 状态名
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
