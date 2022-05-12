package xyz.migoo.template.controller.manager.developer.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CodegenColumnRespVO extends CodegenColumnBaseVO {

    private Long id;

    private Date createTime;

}