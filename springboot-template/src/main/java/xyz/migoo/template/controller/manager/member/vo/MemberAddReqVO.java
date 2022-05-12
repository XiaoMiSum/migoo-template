package xyz.migoo.template.controller.manager.member.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
public class MemberAddReqVO extends MemberBaseVO {

    @NotBlank(message = "用户密码不能为空")
    private String password;

}
