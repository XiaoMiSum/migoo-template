package xyz.migoo.template.controller.manager.developer.redis;

import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.framework.redis.core.RedisKeyDefine;
import xyz.migoo.framework.redis.core.RedisKeyRegistry;
import xyz.migoo.template.controller.manager.developer.redis.vo.RedisKeyRespVO;
import xyz.migoo.template.controller.manager.developer.redis.vo.RedisMonitorRespVO;
import xyz.migoo.template.convert.manager.developer.RedisConvert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Properties;


@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping
    @PreAuthorize("@ss.hasPermission('infra:redis:monitor')")
    public Result<RedisMonitorRespVO> getRedisMonitorInfo() {
        // 获得 Redis 统计信息
        Properties info = stringRedisTemplate.execute((RedisCallback<Properties>) RedisServerCommands::info);
        Long dbSize = stringRedisTemplate.execute(RedisServerCommands::dbSize);
        Properties commandStats = stringRedisTemplate.execute((
                RedisCallback<Properties>) connection -> connection.info("commandstats"));
        assert commandStats != null; // 断言，避免警告
        // 拼接结果返回
        return Result.getSuccessful(RedisConvert.INSTANCE.build(info, dbSize, commandStats));
    }

    @GetMapping("/keys")
    @PreAuthorize("@ss.hasPermission('developer:redis:keys')")
    public Result<List<RedisKeyRespVO>> getKeyList() {
        List<RedisKeyDefine> keyDefines = RedisKeyRegistry.list();
        return Result.getSuccessful(RedisConvert.INSTANCE.convertList(keyDefines));
    }

}
