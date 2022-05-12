package xyz.migoo.template.controller.manager.sys.permission.role.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleUpdateReqVO extends RoleBaseVO {

    @NotNull(message = "角色编号不能为空")
    private Long id;
}
