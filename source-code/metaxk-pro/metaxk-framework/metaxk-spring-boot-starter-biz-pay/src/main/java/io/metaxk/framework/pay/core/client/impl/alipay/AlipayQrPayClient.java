package io.metaxk.framework.pay.core.client.impl.alipay;

import io.metaxk.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import io.metaxk.framework.pay.core.client.dto.order.PayOrderUnifiedRespDTO;
import io.metaxk.framework.pay.core.enums.PayChannelEnum;
import io.metaxk.framework.pay.core.enums.PayDisplayModeEnum;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 支付宝【扫码支付】的 PayClient 实现类
 *
 * 文档：<a href="https://opendocs.alipay.com/apis/02890k">扫码支付</a>
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Slf4j
public class AlipayQrPayClient extends AbstractAlipayClient {

    public AlipayQrPayClient(Long channelId, AlipayPayClientConfig config) {
        super(channelId, PayChannelEnum.ALIPAY_QR.getCode(), config);
    }

    @Override
    public PayOrderUnifiedRespDTO doUnifiedOrder(PayOrderUnifiedReqDTO reqDTO) throws AlipayApiException {
        // 1.1 构建 AlipayTradePrecreateModel 请求
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        // ① 通用的参数
        model.setOutTradeNo(reqDTO.getMerchantOrderId());
        model.setSubject(reqDTO.getSubject());
        model.setBody(reqDTO.getBody());
        model.setTotalAmount(formatAmount(reqDTO.getAmount()));
        model.setProductCode("FACE_TO_FACE_PAYMENT"); // 销售产品码. 目前扫码支付场景下仅支持 FACE_TO_FACE_PAYMENT
        // ② 个性化的参数【无】
        // ③ 支付宝扫码支付只有一种展示，考虑到前端可能希望二维码扫描后，手机打开
        String displayMode = PayDisplayModeEnum.QR_CODE.getMode();

        // 1.2 构建 AlipayTradePrecreateRequest 请求
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setBizModel(model);
        request.setNotifyUrl(reqDTO.getNotifyUrl());
        request.setReturnUrl(reqDTO.getReturnUrl());

        // 2.1 执行请求
        AlipayTradePrecreateResponse response = client.execute(request);
        // 2.2 处理结果
        validateSuccess(response);
        return new PayOrderUnifiedRespDTO()
                .setDisplayMode(displayMode).setDisplayContent(response.getQrCode());
    }
}
