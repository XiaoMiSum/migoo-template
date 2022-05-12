package xyz.migoo.template.controller.login.vo;

import lombok.Data;
import xyz.migoo.framework.common.util.json.JsonUtils;
import xyz.migoo.framework.security.core.LoginUser;

import javax.validation.constraints.NotBlank;

@Data
public class AuthLoginReqVO {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "登录密码不能为空")
    private String password;

    private LoginUser.Client client;

    @Override
    public String toString() {
        return JsonUtils.toJsonString(this);
    }
}
