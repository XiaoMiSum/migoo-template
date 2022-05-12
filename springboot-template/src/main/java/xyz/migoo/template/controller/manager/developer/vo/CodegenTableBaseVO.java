package xyz.migoo.template.controller.manager.developer.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CodegenTableBaseVO {

    @NotNull(message = "表名称不能为空")
    private String tableName;

    @NotNull(message = "表描述不能为空")
    private String tableComment;

    private String remark;

    @NotNull(message = "模块名不能为空")
    private String moduleName;

    @NotNull(message = "业务名不能为空")
    private String businessName;

    @NotNull(message = "类名称不能为空")
    private String className;

    @NotNull(message = "类描述不能为空")
    private String classComment;

    @NotNull(message = "作者不能为空")
    private String author;

    @NotNull(message = "模板类型不能为空")
    private Integer templateType;

    private Long parentMenuId;

}