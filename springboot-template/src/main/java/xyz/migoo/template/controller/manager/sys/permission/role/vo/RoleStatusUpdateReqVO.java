package xyz.migoo.template.controller.manager.sys.permission.role.vo;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class RoleStatusUpdateReqVO {

    @NotNull(message = "id不能为空")
    private Long id;

    @NotNull(message = "更新状态不能为空")
    @Min(value = 1, message = "更新状态错误")
    @Max(value = 2, message = "更新状态错误")
    private Integer status;
}
