package io.metaxk.module.mes.utils;

/**
 * io.metaxk.module.mes.utils
 *
 * @author 万界星空
 * @time 2023/8/4 15:08
 */

@FunctionalInterface
public interface BeanCopyUtilCallBack <S, T> {

    void callBack(S t, T s);

}
