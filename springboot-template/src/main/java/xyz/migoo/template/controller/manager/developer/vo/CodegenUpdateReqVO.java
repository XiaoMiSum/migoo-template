package xyz.migoo.template.controller.manager.developer.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CodegenUpdateReqVO {

    @Valid
    @NotNull(message = "表定义不能为空")
    private Table table;

    @Valid
    @NotNull(message = "字段定义不能为空")
    private List<Column> columns;

    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class Table extends CodegenTableBaseVO {

        private Long id;

    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class Column extends CodegenColumnBaseVO {

        private Long id;

    }

}