package xyz.migoo.template.controller.manager.sys.post.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public abstract class PostBaseVO {

    @NotBlank(message = "岗位名称不能为空")
    @Size(max = 50, message = "岗位名称长度不能超过50个字符")
    private String name;

    @NotBlank(message = "岗位编码不能为空")
    @Size(max = 64, message = "岗位编码长度不能超过64个字符")
    private String code;

    @NotNull(message = "显示顺序不能为空")
    private Integer sort;

    private String remark;
}
