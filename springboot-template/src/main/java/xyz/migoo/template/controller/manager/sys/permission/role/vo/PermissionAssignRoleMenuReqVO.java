package xyz.migoo.template.controller.manager.sys.permission.role.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

@Data
public class PermissionAssignRoleMenuReqVO {

    @NotNull(message = "角色编号不能为空")
    private Long roleId;

    private Set<Long> menuIds = Collections.emptySet();

}