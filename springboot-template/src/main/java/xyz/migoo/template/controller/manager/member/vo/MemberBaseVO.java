package xyz.migoo.template.controller.manager.member.vo;

import lombok.Data;
import xyz.migoo.framework.common.validation.Mobile;

import javax.validation.constraints.Size;

@Data
public abstract class MemberBaseVO {

    @Size(max = 30, message = "用户昵称长度不能超过30个字符")
    private String name;

    @Mobile
    private String phone;

    private Integer gender;

    private String avatar;
}
