package io.metaxk.framework.pay.core.client.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 统一下单 Response DTO
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Data
public class PayOrderUnifiedRespDTO {

    /**
     * 展示模式
     */
    private String displayMode;
    /**
     * 展示内容
     */
    private String displayContent;

}
