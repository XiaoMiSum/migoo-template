package xyz.migoo.template.dal.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import xyz.migoo.framework.common.util.json.JsonUtils;
import xyz.migoo.framework.security.config.SecurityProperties;
import xyz.migoo.framework.security.core.LoginUser;

import javax.annotation.Resource;
import java.time.Duration;

import static xyz.migoo.template.dal.redis.SysRedisKeyConstants.LOGIN_USER;

/**
 * @author xiaomi
 * Created on 2022/5/14 16:53
 */
@Repository
public class LoginUserRedis {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private SecurityProperties securityProperties;

    private static String formatKey(String sessionId) {
        return String.format(LOGIN_USER.getKeyTemplate(), sessionId);
    }

    public LoginUser get(String sessionId) {
        String redisKey = formatKey(sessionId);
        return JsonUtils.parseObject(stringRedisTemplate.opsForValue().get(redisKey), LoginUser.class);
    }

    public void set(String sessionId, LoginUser loginUser) {
        String redisKey = formatKey(sessionId);
        stringRedisTemplate.opsForValue().set(redisKey, JsonUtils.toJsonString(loginUser),
                Duration.ofMillis(securityProperties.getSessionTimeout().toMillis()));
    }

    public void remove(String sessionId) {
        String redisKey = formatKey(sessionId);
        stringRedisTemplate.delete(redisKey);
    }
}
