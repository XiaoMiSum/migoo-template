package xyz.migoo.template.controller.manager.sys.permission.menu.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class MenuUpdateReqVO extends MenuBaseVO {

    @NotNull(message = "菜单编号不能为空")
    private Integer id;

    @NotNull(message = "状态不能为空")
    private Integer status;
}
