package io.metaxk.framework.common.util.object;

import io.metaxk.framework.common.pojo.PageParam;

/**
 * {@link io.metaxk.framework.common.pojo.PageParam} 工具类
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
public class PageUtils {

    public static int getStart(PageParam pageParam) {
        return (pageParam.getPageNo() - 1) * pageParam.getPageSize();
    }

}
