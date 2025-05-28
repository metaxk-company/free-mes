package io.metaxk.framework.mq.core.message;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Redis 消息抽象基类
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Data
public abstract class AbstractRedisMessage {

    /**
     * 头
     */
    private Map<String, String> headers = new HashMap<>();

    public String getHeader(String key) {
        return headers.get(key);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

}
