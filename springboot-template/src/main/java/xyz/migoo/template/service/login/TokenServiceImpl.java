package xyz.migoo.template.service.login;

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
import xyz.migoo.framework.common.enums.CommonStatusEnum;
import xyz.migoo.framework.common.exception.util.ServiceExceptionUtil;
import xyz.migoo.framework.common.util.json.JsonUtils;
import xyz.migoo.framework.security.core.LoginUser;
import xyz.migoo.framework.security.core.service.SecuritySessionAuthService;
import xyz.migoo.template.controller.login.vo.AuthLoginReqVO;
import xyz.migoo.template.controller.manager.member.vo.MemberQueryReqVO;
import xyz.migoo.template.controller.manager.sys.user.vo.UserQueryReqVO;
import xyz.migoo.template.convert.AuthConvert;
import xyz.migoo.template.dal.dataobject.BaseUser;
import xyz.migoo.template.dal.dataobject.Member;
import xyz.migoo.template.dal.dataobject.sys.User;
import xyz.migoo.template.enums.ErrorCodeConstants;
import xyz.migoo.template.service.UserService;
import xyz.migoo.template.service.manager.sys.permission.PermissionService;

import javax.annotation.Resource;
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

    @Resource(name = "userServiceImpl")
    private UserService<User, UserQueryReqVO> userService;

    @Resource(name = "memberServiceImpl")
    private UserService<Member, MemberQueryReqVO> memberService;

    @Resource
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String jsonUser) throws UsernameNotFoundException {
        AuthLoginReqVO req = JsonUtils.parseObject(jsonUser, AuthLoginReqVO.class);
        BaseUser user = this.getUser(req.getUsername(), req.getClient());
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(req.getUsername());
        }
        return AuthConvert.INSTANCE.convert(user, req);
    }

    @Override
    public LoginUser verifyTokenAndRefresh(String token) {
        // ?????? LoginUser
        LoginUser loginUser = securitySessionAuthService.getLoginUser(token);
        if (Objects.nonNull(loginUser)) {
            // ?????? LoginUser ??????
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
        LoginUser loginUser = login0(JsonUtils.toJsonString(req), req.getPassword());
        // ????????????????????? Redis ???????????? sessionId ??????
        return securitySessionAuthService.createUserSession(loginUser);
    }

    private BaseUser getUser(String username, LoginUser.Client client) {
        // ????????????????????????????????? ????????????????????????
        return LoginUser.Client.isAdminClient(client) ? userService.get(username) : memberService.get(username);
    }

    private LoginUser login0(String reqJson, String password) {
        try {
            // ?????? Spring Security ??? AuthenticationManager#authenticate(...) ???????????????????????????????????????
            // ??????????????????????????? loadUserByUsername ??????????????? User ??????
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(reqJson, password));
            return (LoginUser) authentication.getPrincipal();
        } catch (BadCredentialsException badCredentialsException) {
            throw ServiceExceptionUtil.get(ErrorCodeConstants.AUTH_LOGIN_BAD_CREDENTIALS);
        } catch (DisabledException disabledException) {
            throw ServiceExceptionUtil.get(ErrorCodeConstants.AUTH_LOGIN_USER_DISABLED);
        } catch (AuthenticationException authenticationException) {
            log.error("[login0][user({}) ??????????????????]", reqJson, authenticationException);
            throw ServiceExceptionUtil.get(ErrorCodeConstants.AUTH_LOGIN_FAIL_UNKNOWN);
        }
    }

    private void refreshLoginUserCache(String token, LoginUser loginUser) {
        // ??? 1/2 ??? Session ????????????????????? LoginUser ??????
        if (System.currentTimeMillis() - loginUser.getUpdateTime().getTime() > securitySessionAuthService.getSessionTimeoutMillis() / N_2) {
            // ???????????????null ?????? ????????? ?????? token ??????????????????????????????????????????
            BaseUser user = this.getUser(loginUser.getUsername(), loginUser.getClient());
            if (Objects.isNull(user) || Objects.equals(user.getStatus(), CommonStatusEnum.DISABLE.getStatus())) {
                throw ServiceExceptionUtil.get(ErrorCodeConstants.AUTH_TOKEN_EXPIRED);
            }
            // ?????? LoginUser ??????
            securitySessionAuthService.refreshUserSession(token, loginUser);
        }
    }
}
