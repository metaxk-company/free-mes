package io.metaxk.framework.pay.core.client.dto.notify;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 支付通知 Response DTO
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayOrderNotifyRespDTO {

    /**
     * 支付订单号（支付模块的）
     */
    private String orderExtensionNo;
    /**
     * 支付渠道编号
     */
    private String channelOrderNo;
    /**
     * 支付渠道用户编号
     */
    private String channelUserId;
    /**
     * 支付成功时间
     */
    private LocalDateTime successTime;

    /**
     * TODO @jason 结合其他的渠道定义成枚举,
     *
     * alipay
     * TRADE_CLOSED,未付款交易超时关闭，或支付完成后全额退款。
     * TRADE_SUCCESS, 交易支付成功
     * TRADE_FINISHED 	交易结束，不可退款。
     */
    private String tradeStatus;

}
