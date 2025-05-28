package io.metaxk.framework.pay.core.client.impl.alipay;

import io.metaxk.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import io.metaxk.framework.pay.core.client.dto.order.PayOrderUnifiedRespDTO;
import io.metaxk.framework.pay.core.enums.PayChannelEnum;
import io.metaxk.framework.pay.core.enums.PayDisplayModeEnum;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 支付宝【App 支付】的 PayClient 实现类
 *
 * 文档：<a href="https://opendocs.alipay.com/open/02e7gq">App 支付</a>
 *
 * // TODO 星空斗士：未详细测试，因为手头没 App
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Slf4j
public class AlipayAppPayClient extends AbstractAlipayClient {

    public AlipayAppPayClient(Long channelId, AlipayPayClientConfig config) {
        super(channelId, PayChannelEnum.ALIPAY_APP.getCode(), config);
    }

    @Override
    public PayOrderUnifiedRespDTO doUnifiedOrder(PayOrderUnifiedReqDTO reqDTO) throws AlipayApiException {
        // 1.1 构建 AlipayTradeAppPayModel 请求
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        // ① 通用的参数
        model.setOutTradeNo(reqDTO.getMerchantOrderId());
        model.setSubject(reqDTO.getSubject());
        model.setBody(reqDTO.getBody());
        model.setTotalAmount(formatAmount(reqDTO.getAmount()));
        model.setProductCode(" QUICK_MSECURITY_PAY"); // 销售产品码：无线快捷支付产品
        // ② 个性化的参数【无】
        // ③ 支付宝扫码支付只有一种展示
        String displayMode = PayDisplayModeEnum.APP.getMode();

        // 1.2 构建 AlipayTradePrecreateRequest 请求
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setBizModel(model);
        request.setNotifyUrl(reqDTO.getNotifyUrl());
        request.setReturnUrl(reqDTO.getReturnUrl());

        // 2.1 执行请求
        AlipayTradeAppPayResponse response = client.execute(request);
        // 2.2 处理结果
        validateSuccess(response);
        return new PayOrderUnifiedRespDTO()
                .setDisplayMode(displayMode).setDisplayContent("");
    }

}
