package xyz.migoo.template.service.impl.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.exception.util.ServiceExceptionUtil;
import xyz.migoo.framework.security.core.LoginUser;
import xyz.migoo.framework.security.core.service.SecuritySessionAuthService;
import xyz.migoo.template.controller.login.vo.AuthLoginReqVO;
import xyz.migoo.template.dal.dataobject.User;
import xyz.migoo.template.enums.ErrorCodeConstants;
import xyz.migoo.template.service.TokenService;
import xyz.migoo.template.service.UserService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

import static xyz.migoo.framework.common.enums.NumberConstants.N_2;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    @Lazy
    private AuthenticationManager authenticationManager;

    @Resource
    private SecuritySessionAuthService securitySessionAuthService;

    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.verifyUser(username);
        return new LoginUser().setId(user.getId())
                .setUpdateTime(new Date())
                .setEnabled(true)
                .setUsername(username)
                .setPassword(user.getPassword())
                .setName(user.getRealName())
                .setAvatar(user.getAvatar());
    }

    @Override
    public LoginUser verifyTokenAndRefresh(String token) {
        // 获得 LoginUser
        LoginUser loginUser = securitySessionAuthService.getLoginUser(token);
        if (Objects.nonNull(loginUser)) {
            // 刷新 LoginUser 缓存
            this.refreshLoginUserCache(token, loginUser);
        }
        return loginUser;
    }

    @Override
    public void signOut(String token) {
        securitySessionAuthService.deleteUserSession(token);
    }

    @Override
    public String signIn(AuthLoginReqVO req) {
        LoginUser loginUser = login0(req.getUsername(), req.getPassword());
        // 缓存登陆用户到 Redis 中，返回 sessionId 编号
        return securitySessionAuthService.createUserSession(loginUser);
    }

    private User verifyUser(String username) {
        // 根据请求传递的用户类型 查询对应的表数据
        User user = userService.get(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    private LoginUser login0(String reqJson, String password) {
        try {
            // 调用 Spring Security 的 AuthenticationManager#authenticate(...) 方法，使用账号密码进行认证
            // 在其内部，会调用到 loadUserByUsername 方法，获取 User 信息
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(reqJson, password));
            return (LoginUser) authentication.getPrincipal();
        } catch (BadCredentialsException badCredentialsException) {
            throw ServiceExceptionUtil.get(ErrorCodeConstants.AUTH_LOGIN_BAD_CREDENTIALS);
        } catch (DisabledException disabledException) {
            throw ServiceExceptionUtil.get(ErrorCodeConstants.AUTH_LOGIN_USER_DISABLED);
        } catch (AuthenticationException authenticationException) {
            log.error("[login0][user({}) 发生未知异常]", reqJson, authenticationException);
            throw ServiceExceptionUtil.get(ErrorCodeConstants.AUTH_LOGIN_FAIL_UNKNOWN);
        }
    }

    private void refreshLoginUserCache(String token, LoginUser loginUser) {
        // 每 1/2 的 Session 超时时间，刷新 LoginUser 缓存
        if (System.currentTimeMillis() - loginUser.getUpdateTime().getTime() > securitySessionAuthService.getSessionTimeoutMillis() / N_2) {
            // 重新加载 SysUserDO 信息
            try {
                // 验证user时抛出异常 认为 token 过期，方便前端跳转到登录界面
                this.verifyUser(loginUser.getUsername());
            } catch (Exception e) {
                throw ServiceExceptionUtil.get(ErrorCodeConstants.AUTH_TOKEN_EXPIRED);
            }
            // 刷新 LoginUser 缓存
            securitySessionAuthService.refreshUserSession(token, loginUser);
        }
    }
}
