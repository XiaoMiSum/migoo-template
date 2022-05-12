package xyz.migoo.template.controller.manager.developer.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CodegenTableRespVO extends CodegenTableBaseVO {

    private Long id;

    private Date createTime;

    private Date updateTime;

}