package xyz.migoo.template.controller.manager.sys.user.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

@Data
public class PermissionAssignUserRoleReqVO {

    @NotNull(message = "用户编号不能为空")
    private Long userId;

    private Set<Long> roleIds = Collections.emptySet();

}