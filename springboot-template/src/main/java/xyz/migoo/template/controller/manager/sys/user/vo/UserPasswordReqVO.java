package xyz.migoo.template.controller.manager.sys.user.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserPasswordReqVO {

    @NotNull(message = "用户编号不能为空")
    private Long id;

    @NotBlank(message = "登录密码不能为空")
    private String password;
}
