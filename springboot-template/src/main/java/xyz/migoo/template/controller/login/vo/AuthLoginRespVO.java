package xyz.migoo.template.controller.login.vo;

import xyz.migoo.framework.common.util.json.JsonUtils;

public class AuthLoginRespVO {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return JsonUtils.toJsonString(this);
    }

}