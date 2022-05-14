package xyz.migoo.template.service.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.security.core.LoginUser;
import xyz.migoo.framework.security.core.service.LoginUserCacheService;
import xyz.migoo.template.dal.redis.LoginUserRedis;

import javax.annotation.Resource;

@Slf4j
@Service
public class LoginUserCacheServiceImpl implements LoginUserCacheService {

    @Resource
    private LoginUserRedis usersCache;

    @Override
    public LoginUser get(String sessionId) {
        return usersCache.get(sessionId);
    }

    @Override
    public void set(String sessionId, LoginUser loginUser) {
        usersCache.set(sessionId, loginUser);
    }

    @Override
    public void delete(String sessionId) {
        usersCache.remove(sessionId);
    }
}
