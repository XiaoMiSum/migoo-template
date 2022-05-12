package xyz.migoo.template.controller.manager.sys.post.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PostListReqVO extends PostBaseVO {

    private String name;

    private Integer status;

}
