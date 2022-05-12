package xyz.migoo.template.controller.manager.sys.dept.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeptUpdateReqVO extends DeptBaseVO {

    @NotNull(message = "部门编号不能为空")
    private Long id;

    @NotNull(message = "状态不能为空")
    private Integer status;
}
