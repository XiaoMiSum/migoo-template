package xyz.migoo.template.controller.manager.sys.user.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserUpdateReqVO extends UserBaseVO {

    @NotNull(message = "用户编号不能为空")
    private Long id;
    
    private Integer status;
}
