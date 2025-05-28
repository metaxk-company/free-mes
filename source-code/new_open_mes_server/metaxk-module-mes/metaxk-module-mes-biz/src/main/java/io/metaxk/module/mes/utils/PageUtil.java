package io.metaxk.module.mes.utils;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * io.metaxk.module.mes.utils
 *
 * @author 万界星空
 * @time 2023/8/8 14:28
 */
public class PageUtil {

    public static List<?> page(List<?> list, Integer pageSize, Integer pageNum) {
        // 将 List 按照 PageSzie 拆分成多个List
        List<? extends List<?>> partition = Lists.partition(list, pageSize);
        // 总页数
        int pages = partition.size();
        pageNum = pageNum <= 1 ? 1 : (pageNum <= (pages) ? pageNum : (pages));
        return partition.get(pageNum);
    }

}
