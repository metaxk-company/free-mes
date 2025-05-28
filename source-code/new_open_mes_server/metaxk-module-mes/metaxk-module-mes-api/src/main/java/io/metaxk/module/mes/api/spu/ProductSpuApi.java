package io.metaxk.module.mes.api.spu;

import io.metaxk.module.mes.api.spu.dto.ProductSpuRespDTO;
import java.util.Collection;
import java.util.List;

/**
 * 商品 SPU API 接口
 *
 * @author LeeYan9
 * @since 2022-08-26
 */
public interface ProductSpuApi {

    /**
     * 批量查询 SPU 数组
     *
     * @param ids SPU 编号列表
     * @return SPU 数组
     */
    List<ProductSpuRespDTO> getSpuList(Collection<Long> ids);

}
