package xyz.migoo.template.controller.manager.sys.user.vo;

import lombok.Getter;
import lombok.Setter;
import xyz.migoo.framework.common.validation.Mobile;

import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public abstract class UserBaseVO {

    @Size(max = 30, message = "用户昵称长度不能超过30个字符")
    private String name;

    private Long deptId;

    private Set<Long> postIds;

    private String email;

    @Mobile
    private String phone;

    private Integer gender;

    private String avatar;
}
