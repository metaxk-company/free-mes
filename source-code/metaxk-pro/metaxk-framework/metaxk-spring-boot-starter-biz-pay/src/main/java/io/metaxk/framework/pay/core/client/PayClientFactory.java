package io.metaxk.framework.pay.core.client;

/**
 * 支付客户端的工厂接口
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
public interface PayClientFactory {

    /**
     * 获得支付客户端
     *
     * @param channelId 渠道编号
     * @return 支付客户端
     */
     PayClient getPayClient(Long channelId);

    /**
     * 创建支付客户端
     *
     * @param channelId 渠道编号
     * @param channelCode 渠道编码
     * @param config 支付配置
     */
    <Config extends PayClientConfig> void createOrUpdatePayClient(Long channelId, String channelCode,
                                                                  Config config);

}
