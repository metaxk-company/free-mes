package io.metaxk.module.system.dal.redis.common;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.Duration;

import static io.metaxk.module.system.dal.redis.RedisKeyConstants.CAPTCHA_CODE;

/**
 * 验证码的 Redis DAO
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Repository
public class CaptchaRedisDAO {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public String get(String uuid) {
        String redisKey = formatKey(uuid);
        return stringRedisTemplate.opsForValue().get(redisKey);
    }

    public void set(String uuid, String code, Duration timeout) {
        String redisKey = formatKey(uuid);
        stringRedisTemplate.opsForValue().set(redisKey, code, timeout);
    }

    public void delete(String uuid) {
        String redisKey = formatKey(uuid);
        stringRedisTemplate.delete(redisKey);
    }

    private static String formatKey(String uuid) {
        return String.format(CAPTCHA_CODE.getKeyTemplate(), uuid);
    }

}
