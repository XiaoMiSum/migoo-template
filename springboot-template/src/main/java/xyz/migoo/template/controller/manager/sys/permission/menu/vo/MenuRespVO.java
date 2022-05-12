package xyz.migoo.template.controller.manager.sys.permission.menu.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class MenuRespVO extends MenuBaseVO {

    private Long id;

    private Integer status;

    private Date createTime;
}
