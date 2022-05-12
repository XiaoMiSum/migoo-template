package xyz.migoo.template.service.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.security.config.SecurityProperties;
import xyz.migoo.framework.security.core.LoginUser;
import xyz.migoo.framework.security.core.service.LoginUserCacheService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class LoginUserCacheServiceImpl implements LoginUserCacheService {

    private Map<String, LoginUser> usersCache;
    @Resource
    private SecurityProperties securityProperties;


    @PostConstruct
    public void init() {
        this.usersCache = new HashMap<>(16);
    }

    @Override
    public LoginUser get(String sessionId) {
        return usersCache.get(sessionId);
    }

    @Override
    public void set(String sessionId, LoginUser loginUser) {
        usersCache.put(sessionId, loginUser);
    }

    @Override
    public void delete(String sessionId) {
        usersCache.remove(sessionId);
    }

    @Scheduled(fixedDelay = 300000)
    public void task() {
        if (!usersCache.isEmpty()) {
            for (String key : usersCache.keySet()) {
                long x = System.currentTimeMillis() - usersCache.get(key).getUpdateTime().getTime();
                if (x >= securityProperties.getSessionTimeout().toMillis()) {
                    usersCache.remove(key);
                    log.info("删除过期token {}", key);
                }
            }
        }

    }
}
