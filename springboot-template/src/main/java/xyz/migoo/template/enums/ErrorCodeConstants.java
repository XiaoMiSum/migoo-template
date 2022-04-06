package xyz.migoo.template.enums;

import xyz.migoo.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {

    // ========== AUTH 模块 1002000000 ==========

    ErrorCode AUTH_LOGIN_BAD_CREDENTIALS = new ErrorCode(1002000000, "登录失败，用户名或密码不正确");
    ErrorCode AUTH_LOGIN_USER_DISABLED = new ErrorCode(1002000001, "登录失败，用户已停用");
    ErrorCode AUTH_LOGIN_FAIL_UNKNOWN = new ErrorCode(1002000002, "登录失败");
    ErrorCode AUTH_LOGIN_CAPTCHA_CODE_ERROR = new ErrorCode(1002000003, "验证码不正确");
    ErrorCode AUTH_TOKEN_EXPIRED = new ErrorCode(1002000004, "Token 已经过期");

    // ========== USER 模块 1003000000 ==========

    ErrorCode USER_IS_EXISTS = new ErrorCode(1003000000, "注册失败，用户已存在");
    ErrorCode USER_NOT_EXISTS = new ErrorCode(1003000001, "操作失败，操作用户不存在");
    ErrorCode USER_PASSWORD_CONFIRM_UNCONFORMITY = new ErrorCode(1003000002, "操作失败，新密码与确认密码不一致");
    ErrorCode USER_ORIGINAL_PASSWORD_UNCONFORMITY = new ErrorCode(1003000003, "操作失败，原密码错误");
    ErrorCode USER_IS_DISABLED = new ErrorCode(1003000004, "登录失败，用户已停用");

    ErrorCode OPTION_ERROR = new ErrorCode(9999, "操作失败，请检查");

}
