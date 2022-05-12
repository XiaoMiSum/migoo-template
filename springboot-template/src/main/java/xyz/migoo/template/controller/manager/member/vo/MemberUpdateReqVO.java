package xyz.migoo.template.controller.manager.member.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class MemberUpdateReqVO extends MemberBaseVO {

    @NotNull(message = "用户编号不能为空")
    private Long id;
}
