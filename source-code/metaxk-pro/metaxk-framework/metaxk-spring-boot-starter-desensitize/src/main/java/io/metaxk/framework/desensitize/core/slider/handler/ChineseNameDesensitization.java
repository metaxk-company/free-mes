package io.metaxk.framework.desensitize.core.slider.handler;

import io.metaxk.framework.desensitize.core.slider.annotation.ChineseNameDesensitize;

/**
 * {@link ChineseNameDesensitize} 的脱敏处理器
 *
 * @author gaibu
 */
public class ChineseNameDesensitization extends AbstractSliderDesensitizationHandler<ChineseNameDesensitize> {

    @Override
    Integer getPrefixKeep(ChineseNameDesensitize annotation) {
        return annotation.prefixKeep();
    }

    @Override
    Integer getSuffixKeep(ChineseNameDesensitize annotation) {
        return annotation.suffixKeep();
    }

    @Override
    String getReplacer(ChineseNameDesensitize annotation) {
        return annotation.replacer();
    }

}
