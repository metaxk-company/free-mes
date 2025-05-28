package io.metaxk.framework.lock4j.core;

import io.metaxk.framework.redis.core.RedisKeyDefine;
import org.redisson.api.RLock;

import static io.metaxk.framework.redis.core.RedisKeyDefine.KeyTypeEnum.HASH;

/**
 * Lock4j Redis Key 枚举类
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
public interface Lock4jRedisKeyConstants {

    RedisKeyDefine LOCK4J = new RedisKeyDefine("分布式锁",
            "lock4j:%s", // 参数来自 DefaultLockKeyBuilder 类
            HASH, RLock.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC); // Redisson 的 Lock 锁，使用 Hash 数据结构

}
