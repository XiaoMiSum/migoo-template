package xyz.migoo.template.service;

import xyz.migoo.framework.security.core.service.SecurityAuthFrameworkService;
import xyz.migoo.template.controller.login.vo.AuthLoginReqVO;

import javax.validation.Valid;


public interface TokenService extends SecurityAuthFrameworkService {

    /**
     * 账号登录
     *
     * @param reqVO 登录信息
     * @return 身份令牌，使用 JWT 方式
     */
    String signIn(@Valid AuthLoginReqVO reqVO);
}
