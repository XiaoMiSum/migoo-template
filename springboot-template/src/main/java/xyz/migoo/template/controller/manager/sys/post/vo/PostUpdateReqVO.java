package xyz.migoo.template.controller.manager.sys.post.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class PostUpdateReqVO extends PostBaseVO {

    @NotNull(message = "岗位编号不能为空")
    private Long id;

    private Integer status;

}
