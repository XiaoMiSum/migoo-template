package xyz.migoo.template.controller.manager.developer.redis.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import xyz.migoo.framework.redis.core.RedisKeyDefine;

import java.time.Duration;

@Data
@Builder
@AllArgsConstructor
public class RedisKeyRespVO {

    private String keyTemplate;

    private RedisKeyDefine.KeyTypeEnum keyType;

    private Class valueType;

    private RedisKeyDefine.TimeoutTypeEnum timeoutType;

    private Duration timeout;

    private String memo;

}
