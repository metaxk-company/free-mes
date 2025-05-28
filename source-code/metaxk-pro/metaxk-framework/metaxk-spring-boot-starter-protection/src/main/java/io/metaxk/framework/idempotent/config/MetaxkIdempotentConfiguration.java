package io.metaxk.framework.idempotent.config;

import io.metaxk.framework.idempotent.core.aop.IdempotentAspect;
import io.metaxk.framework.idempotent.core.keyresolver.impl.DefaultIdempotentKeyResolver;
import io.metaxk.framework.idempotent.core.keyresolver.impl.ExpressionIdempotentKeyResolver;
import io.metaxk.framework.idempotent.core.keyresolver.IdempotentKeyResolver;
import io.metaxk.framework.idempotent.core.redis.IdempotentRedisDAO;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import io.metaxk.framework.redis.config.MetaxkRedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@AutoConfiguration(after = MetaxkRedisAutoConfiguration.class)
public class MetaxkIdempotentConfiguration {

    @Bean
    public IdempotentAspect idempotentAspect(List<IdempotentKeyResolver> keyResolvers, IdempotentRedisDAO idempotentRedisDAO) {
        return new IdempotentAspect(keyResolvers, idempotentRedisDAO);
    }

    @Bean
    public IdempotentRedisDAO idempotentRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new IdempotentRedisDAO(stringRedisTemplate);
    }

    // ========== 各种 IdempotentKeyResolver Bean ==========

    @Bean
    public DefaultIdempotentKeyResolver defaultIdempotentKeyResolver() {
        return new DefaultIdempotentKeyResolver();
    }

    @Bean
    public ExpressionIdempotentKeyResolver expressionIdempotentKeyResolver() {
        return new ExpressionIdempotentKeyResolver();
    }

}
