package xyz.migoo.template.controller.manager.sys.user.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserAddReqVO extends UserBaseVO {

    @NotBlank(message = "登录密码不能为空")
    private String password;
}
